package com.softmax.oauth2.security.common;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.management.ManagementFactory;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.LockSupport;


/**
 * 分布式ID生成
 *
 * @author Jarvis
 */
public class DcsIdGenerator {

    private static final Logger LOGGER = LoggerFactory.getLogger(DcsIdGenerator.class);

    /**
     * EPOCH是服务器第一次上线时间点, 设置后不允许修改
     */
    private static long EPOCH;

    /**
     * 每台workerId服务器有3个备份workerId, 备份workerId数量越多, 可靠性越高, 但是可部署的sequence ID服务越少
     */
    private static final long BACKUP_COUNT = 3;

    /**
     * worker id 的bit数，最多支持8192个节点
     */
    private static final long WORKER_ID_BITS = 5L;

    /**
     * 数据中心标识位数
     */
    private static final long DATA_CENTER_ID_BITS = 5L;

    /**
     * 原来代码 -1 的补码（二进制全1）右移13位, 然后取反
     */
    private static final long MAX_WORKER_ID = ~(-1L << WORKER_ID_BITS);

    /**
     * 序列号，支持单节点最高每毫秒的最大ID数4096
     * 毫秒内自增位
     */
    private static final long SEQUENCE_BITS = 12L;

    /**
     * 机器ID偏左移12位
     */
    private static final long WORKER_ID_SHIFT = SEQUENCE_BITS;

    /**
     * 数据中心ID左移17位(12+5)
     */
    private static final long DATA_CENTER_ID_SHIFT = SEQUENCE_BITS + WORKER_ID_BITS;

    /**
     * 时间毫秒左移22位(5+5+12)
     */
    private static final long TIMESTAMP_LEFT_SHIFT = SEQUENCE_BITS + WORKER_ID_BITS + DATA_CENTER_ID_BITS;

    /**
     * sequence掩码，确保sequnce不会超出上限
     * 最大的序列号，4096
     * -1 的补码（二进制全1）右移12位, 然后取反
     * 生成序列的掩码，这里为4095 (0b111111111111=0xfff=4095)
     */
    private static final long SEQUENCE_MASK = ~(-1L << SEQUENCE_BITS);

    /**
     * 支持的最大数据标识id，结果是31
     */
    private static final long MAX_DATA_CENTER_ID = ~(-1L << DATA_CENTER_ID_BITS);


    /**
     * 工作机器ID(0~31)
     * snowflake算法给workerId预留了10位，即workId的取值范围为[0, 1023]，
     * 事实上实际生产环境不大可能需要部署1024个分布式ID服务，
     * 所以：将workerId取值范围缩小为[0, 511]，[512, 1023]
     * 这个范围的workerId当做备用workerId。workId为0的备用workerId是512，
     * workId为1的备用workerId是513，以此类推
     */
    private static long workerId;

    /**
     * 数据中心ID(0~31)
     */
    private final long dataCenterId;

    /**
     * 当前毫秒生成的序列
     */
    private long sequence = 0L;

    /**
     * 上次生成ID的时间戳
     */
    private long lastTimestamp = -1L;


    /**
     * 保留workerId和lastTimestamp, 以及备用workerId和其对应的lastTimestamp
     */
    private static final Map<Long, Long> WORKER_ID_LAST_TIME_MAP = new ConcurrentHashMap<>();

    /**
     * 最大容忍时间, 单位毫秒, 即如果时钟只是回拨了该变量指定的时间, 那么等待相应的时间即可;
     * 考虑到sequence服务的高性能, 这个值不易过大
     */
    private static final long MAX_BACKWARD_MS = 3;

    private static final DcsIdGenerator DCS_ID_WORK;

    static {
        DCS_ID_WORK = new DcsIdGenerator();
    }

    static {
        LocalDateTime localTime = LocalDateTime.of(2020, 6, 1, 0, 0, 0, 0);
        // EPOCH是服务器第一次上线时间点, 设置后不允许修改,毫秒数
        EPOCH = localTime.toInstant(ZoneOffset.ofHours(8)).toEpochMilli();
        // 初始化workerId和其所有备份workerId与lastTimestamp
        // 假设workerId为0且BACKUP_AMOUNT为4, 那么map的值为: {0:0L, 256:0L, 512:0L, 768:0L}
        // 假设workerId为2且BACKUP_AMOUNT为4, 那么map的值为: {2:0L, 258:0L, 514:0L, 770:0L}
        for (int i = 0; i <= BACKUP_COUNT; i++) {
            WORKER_ID_LAST_TIME_MAP.put(workerId + (i * MAX_WORKER_ID), 0L);
        }
    }

    /**
     * 成员类
     */
    private static class DcsIdGenHolder {
        private static final DcsIdGenerator INSTANCE = new DcsIdGenerator();
    }

    /**
     * 外部调用获取DcsIdGenerator的实例对象，确保不可变
     *
     * @return
     */
    public static DcsIdGenerator getInstance() {
        return DcsIdGenHolder.INSTANCE;
    }

    /**
     * 静态工具类
     *
     * @return
     */
    public static Long generateId() {
        return DCS_ID_WORK.nextId();
    }

    /**
     * 初始化构造，无参构造有参函数，默认节点都是0
     */
    public DcsIdGenerator() {
        this.dataCenterId = getDataCenterId(MAX_DATA_CENTER_ID);
        //获取机器编码
        workerId = getWorkerId(dataCenterId, MAX_WORKER_ID);
    }

    /**
     * 构造函数
     *
     * @param workerId     工作ID (0~31)
     * @param dataCenterId 数据中心ID (0~31)
     */
    public DcsIdGenerator(long workerId, long dataCenterId) {
        if (workerId > MAX_WORKER_ID || workerId < 0) {
            throw new IllegalArgumentException(String.format("worker Id can't be greater than %d or less than 0", MAX_WORKER_ID));
        }
        if (dataCenterId > MAX_DATA_CENTER_ID || dataCenterId < 0) {
            throw new IllegalArgumentException(String.format("datacenter Id can't be greater than %d or less than 0", MAX_DATA_CENTER_ID));
        }
        DcsIdGenerator.workerId = workerId;
        this.dataCenterId = dataCenterId;
    }

    /**
     * 获取带自定义前缀的全局唯一编码
     */
    public String getStrCodingByPrefix(String prefix) {
        Long ele = this.nextId();
        return prefix + ele.toString();
    }

    /**
     * 获得下一个ID (该方法是线程安全的)
     * 在单节点上获得下一个ID，使用Synchronized控制并发，而非CAS的方式，
     * 是因为CAS不适合并发量非常高的场景。
     * <p>
     * 考虑时钟回拨
     * 缺陷: 如果连续两次时钟回拨, 可能还是会有问题, 但是这种概率极低极低
     *
     * @return
     */
    public synchronized long nextId() {
        long currentTimestamp = timeGen();
        // 当发生时钟回拨时
        if (currentTimestamp < lastTimestamp) {
            // 如果时钟回拨在可接受范围内, 等待即可
            long offset = lastTimestamp - currentTimestamp;
            if (offset <= MAX_BACKWARD_MS) {
                try {
                    //睡（lastTimestamp - currentTimestamp）ms让其追上
                    LockSupport.parkNanos(TimeUnit.MILLISECONDS.toNanos(offset));
                    //时间偏差大小小于5ms，则等待两倍时间
                    currentTimestamp = timeGen();
                    //如果时间还小于当前时间，采用抛异常并上报
                    if (currentTimestamp < lastTimestamp) {
                        throw new RuntimeException(String.format("Clock moved backwards.  Refusing to generate id for %d milliseconds", lastTimestamp - currentTimestamp));
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            } else {
                //尝试在workerId的备份workerId上生成
                tryGenerateKeyOnBackup(currentTimestamp);
            }
        }
        // 如果和最后一次请求处于同一毫秒, 那么sequence+1
        if (lastTimestamp == currentTimestamp) {
            // 如果当前生成id的时间还是上次的时间，那么对sequence序列号进行+1
            sequence = (sequence + 1) & SEQUENCE_MASK;
            if (sequence == 0) {
                //自旋等待到下一毫秒
                currentTimestamp = waitUntilNextTime(lastTimestamp);
            }
        } else {
            // 如果是一个更近的时间戳, 那么sequence归零
            sequence = 0L;
        }
        // 更新上次生成id的时间戳
        lastTimestamp = currentTimestamp;
        if (LOGGER.isDebugEnabled()) {
            LOGGER.debug("{}-{}-{}", new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS").format(new Date(lastTimestamp)), workerId, sequence);
        }
        // 进行移位操作生成int64的唯一ID
        //时间戳右移动23位
        long timestamp = (currentTimestamp - EPOCH) << TIMESTAMP_LEFT_SHIFT;

        //workerId 右移动10位
        long workerId = DcsIdGenerator.workerId << WORKER_ID_SHIFT;

        //dataCenterId 右移动(sequenceBits + workerIdBits = 17位)
        long dataCenterId = this.dataCenterId << DATA_CENTER_ID_SHIFT;
        return timestamp | dataCenterId | workerId | sequence;
    }

    /**
     * 尝试在workerId的备份workerId上生成
     * 核心优化代码在方法tryGenerateKeyOnBackup()中，BACKUP_COUNT即备份workerId数越多，
     * sequence服务避免时钟回拨影响的能力越强，但是可部署的sequence服务越少，
     * 设置BACKUP_COUNT为3，最多可以部署1024/(3+1)即256个sequence服务，完全够用，
     * 抗时钟回拨影响的能力也得到非常大的保障。
     *
     * @param currentMillis 当前时间
     */
    private long tryGenerateKeyOnBackup(long currentMillis) {
        // 遍历所有workerId(包括备用workerId, 查看哪些workerId可用)
        for (Map.Entry<Long, Long> entry : WORKER_ID_LAST_TIME_MAP.entrySet()) {
            workerId = entry.getKey();
            // 取得备用workerId的lastTime
            Long tempLastTime = entry.getValue();
            lastTimestamp = tempLastTime == null ? 0L : tempLastTime;

            // 如果找到了合适的workerId
            if (lastTimestamp <= currentMillis) {
                return lastTimestamp;
            }
        }
        // 如果所有workerId以及备用workerId都处于时钟回拨, 那么抛出异常
        throw new IllegalStateException("Clock is moving backwards, current time is "
                + currentMillis + " milliseconds, workerId map = " + WORKER_ID_LAST_TIME_MAP);
    }

    /**
     * 阻塞到下一个毫秒，直到获得新的时间戳
     *
     * @param lastTimestamp 上次生成ID的时间截
     * @return 当前时间戳
     */
    protected long waitUntilNextTime(long lastTimestamp) {
        long timestamp = timeGen();
        while (timestamp <= lastTimestamp) {
            timestamp = timeGen();
        }
        return timestamp;
    }

    private long timeGen() {
        return System.currentTimeMillis();
    }

    /**
     * 获取WorkerId
     *
     * @param dataCenterId
     * @param maxWorkerId
     * @return
     */
    protected static long getWorkerId(long dataCenterId, long maxWorkerId) {
        StringBuffer mpid = new StringBuffer();
        mpid.append(dataCenterId);
        String name = ManagementFactory.getRuntimeMXBean().getName();
        if (!name.isEmpty()) {
            // GET jvmPid
            mpid.append(name.split("@")[0]);
        }
        // MAC + PID 的 hashcode 获取16个低位
        return (mpid.toString().hashCode() & 0xffff) % (maxWorkerId + 1);
    }

    /**
     * 获取机器编码 用来做数据ID
     * 数据标识id部分 通常不建议采用下面的MAC地址方式，
     * 因为用户通过破解很容易拿到MAC进行破坏
     */
    protected static long getDataCenterId(long tempMaxDataCenterId) {
        if (tempMaxDataCenterId < 0L || tempMaxDataCenterId > MAX_DATA_CENTER_ID) {
            tempMaxDataCenterId = MAX_DATA_CENTER_ID;
        }
        long id = 0L;
        try {
            InetAddress ip = InetAddress.getLocalHost();
            NetworkInterface network = NetworkInterface.getByInetAddress(ip);
            if (network == null) {
                id = 1L;
            } else {
                byte[] mac = network.getHardwareAddress();
                id = ((0x000000FF & (long) mac[mac.length - 1])
                        | (0x0000FF00 & (((long) mac[mac.length - 2]) << 8))) >> 6;
                id = id % (tempMaxDataCenterId + 1);
            }
        } catch (Exception e) {
            LOGGER.error("getDatacenterId:{}", e.getMessage());
        }
        return id;
    }

    //region test case

    private static void testProductIdByMoreThread(int dataCenterId, int workerId, int n) throws InterruptedException {
        List<Thread> tlist = new ArrayList<>();
        Set<Long> setAll = new HashSet<>();
        CountDownLatch cdLatch = new CountDownLatch(10);
        long start = System.currentTimeMillis();
        int threadNo = dataCenterId;
        Map<String, DcsIdGenerator> idFactories = new HashMap<>();
        for (int i = 0; i < 10; i++) {
            //用线程名称做map key.
            idFactories.put("snowflake" + i, new DcsIdGenerator(workerId, threadNo++));
        }
        for (int i = 0; i < 10; i++) {
            Thread temp = new Thread(new Runnable() {
                @Override
                public void run() {
                    Set<Long> setId = new HashSet<>();
                    DcsIdGenerator idWorker = idFactories.get(Thread.currentThread().getName());
                    for (int j = 0; j < n; j++) {
                        setId.add(idWorker.nextId());
                    }
                    synchronized (setAll) {
                        setAll.addAll(setId);
                        LOGGER.info("{}生产了{}个id,并成功加入到setAll中.", Thread.currentThread().getName(), n);
                    }
                    cdLatch.countDown();
                }
            }, "snowflake" + i);
            tlist.add(temp);
        }
        for (int j = 0; j < 10; j++) {
            tlist.get(j).start();
        }
        cdLatch.await();

        long end1 = System.currentTimeMillis() - start;

        LOGGER.info("共耗时:{}毫秒,预期应该生产{}个id, 实际合并总计生成ID个数:{}", end1, 10 * n, setAll.size());

    }

    private static void testProductId(int dataCenterId, int workerId, int n) {
        DcsIdGenerator idWorker = new DcsIdGenerator(workerId, dataCenterId);
        DcsIdGenerator idWorker2 = new DcsIdGenerator(workerId + 1, dataCenterId);
        Set<Long> setOne = new HashSet<>();
        Set<Long> setTow = new HashSet<>();
        long start = System.currentTimeMillis();
        for (int i = 0; i < n; i++) {
            //加入set
            setOne.add(idWorker.nextId());
        }
        long end1 = System.currentTimeMillis() - start;
        LOGGER.info("第一批ID预计生成{}个,实际生成{}个<<<<*>>>>共耗时:{}", n, setOne.size(), end1);

        for (int i = 0; i < n; i++) {
            //加入set
            setTow.add(idWorker2.nextId());
        }
        long end2 = System.currentTimeMillis() - start;
        LOGGER.info("第二批ID预计生成{}个,实际生成{}个<<<<*>>>>共耗时:{}", n, setTow.size(), end2);

        setOne.addAll(setTow);
        LOGGER.info("合并总计生成ID个数:{}", setOne.size());

    }

    private static void testPerSecondProductIdNums() {
        DcsIdGenerator idWorker = new DcsIdGenerator(1, 2);
        long start = System.currentTimeMillis();
        int count = 0;
        for (int i = 0; System.currentTimeMillis() - start < 1000; i++, count = i) {
            /**  测试方法一: 此用法纯粹的生产ID,每秒生产ID个数为300w+ */
            long nextId = idWorker.nextId();
            /**  测试方法二: 在log中打印,同时获取ID,此用法生产ID的能力受限于log.error()的吞吐能力.
             * 每秒徘徊在10万左右. */
            LOGGER.info("{}", nextId);
        }
        long end = System.currentTimeMillis() - start;
        System.out.println(end);
        System.out.println(count);
    }

    //endregion

    public static void main(String[] args) {

        DcsIdGenerator dcsIdGenerator = new DcsIdGenerator();
        long nextId = dcsIdGenerator.nextId();
        LOGGER.error("{}", nextId);
        /** case1: 测试每秒生产id个数?
         *   结论: 每秒生产id个数300w+ */
//        testPerSecondProductIdNums();

        /** case2: 单线程-测试多个生产者同时生产N个id,验证id是否有重复?
         *   结论: 验证通过,没有重复. */
//        testProductId(1,2,10000);//验证通过!
//        testProductId(1,2,20000);//验证通过!

        /** case3: 多线程-测试多个生产者同时生产N个id, 全部id在全局范围内是否会重复?
         *   结论: 验证通过,没有重复. */
       /* try {
            testProductIdByMoreThread(1,2,100000);//单机测试此场景,性能损失至少折半!
        } catch (InterruptedException e) {
            e.printStackTrace();
        }*/

    }


}

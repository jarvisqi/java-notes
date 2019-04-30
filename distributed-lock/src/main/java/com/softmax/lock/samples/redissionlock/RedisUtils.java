package com.softmax.lock.samples.redissionlock;

import org.redisson.Redisson;
import org.redisson.api.*;
import org.redisson.config.Config;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


/**
 * @author Jarvis
 * @date 2018/10/9
 */
public class RedisUtils {

    private static Logger logger = LoggerFactory.getLogger(RedisUtils.class);

    private static RedisUtils redisUtils;

    private RedisUtils() {
    }

    /**
     * 单例模式
     *
     * @return
     */
    public static RedisUtils getInstance() {
        if (redisUtils == null) {
            synchronized (RedisUtils.class) {
                if (redisUtils == null) {
                    redisUtils = new RedisUtils();
                }
            }
        }
        return redisUtils;
    }

    /**
     * 使用config创建Redisson
     * Redisson是用于连接Redis Server的基础类
     *
     * @param config
     * @return
     */
    public RedissonClient getRedisson(Config config) {
        RedissonClient redissonClient = Redisson.create(config);
        logger.info("成功连接Redis Server");
        return redissonClient;
    }

    /**
     * 使用ip地址和端口创建Redisson
     *
     * @param ip
     * @param port
     * @return
     */
    public RedissonClient getRedisson(String ip, String port) {
        Config config = new Config();
        config.useSingleServer().setAddress(ip + ":" + port);
        RedissonClient redissonClient = Redisson.create(config);
        logger.info("成功连接Redis Server" + "\t" + "连接" + ip + ":" + port + "服务器");
        return redissonClient;
    }

    /**
     * 关闭Redisson客户端连接
     *
     * @param redissonClient
     */
    public void closeRedisson(RedissonClient redissonClient) {
        redissonClient.shutdown();
        logger.info("成功关闭Redis Client连接");
    }

    /**
     * 获取字符串对象
     *
     * @param redissonClient
     * @param objectName
     * @param <T>
     * @return
     */
    public <T> RBucket<T> getRBucket(RedissonClient redissonClient, String objectName) {
        RBucket<T> bucket = redissonClient.getBucket(objectName);
        return bucket;
    }

    /**
     * 获取Map对象
     *
     * @param redissonClient
     * @param objectName
     * @param <K>
     * @param <V>
     * @return
     */
    public <K, V> RMap<K, V> getMap(RedissonClient redissonClient, String objectName) {
        RMap<K, V> map = redissonClient.getMap(objectName);
        return map;
    }

    /**
     * 获取有序集合
     *
     * @param redissonClient
     * @param objectName
     * @param <V>
     * @return
     */
    public <V> RSortedSet<V> getRSortedSet(RedissonClient redissonClient, String objectName) {
        RSortedSet<V> sortedSet = redissonClient.getSortedSet(objectName);
        return sortedSet;
    }

    /**
     * 获取集合
     *
     * @param redissonClient
     * @param objectName
     * @param <V>
     * @return
     */
    public <V> RSet<V> getRSet(RedissonClient redissonClient, String objectName) {
        RSet<V> set = redissonClient.getSet(objectName);
        return set;
    }

    /**
     * 获取列表
     *
     * @param redissonClient
     * @param objectName
     * @param <V>
     * @return
     */
    public <V> RList<V> getRList(RedissonClient redissonClient, String objectName) {
        RList<V> list = redissonClient.getList(objectName);
        return list;
    }

    /**
     * 获取队列
     *
     * @param redissonClient
     * @param objectName
     * @param <V>
     * @return
     */
    public <V> RQueue<V> getRQueue(RedissonClient redissonClient, String objectName) {
        RQueue<V> queue = redissonClient.getQueue(objectName);
        return queue;
    }

    /**
     * 获取双端队列
     *
     * @param redissonClient
     * @param objectName
     * @param <V>
     * @return
     */
    public <V> RDeque<V> getRDeque(RedissonClient redissonClient, String objectName) {
        RDeque<V> rDeque = redissonClient.getDeque(objectName);
        return rDeque;
    }

    /**
     * 获取消息的Topic
     *
     * @param redissonClient
     * @param objectName
     * @param <M>
     * @return
     */
    public <M> RTopic<M> getTopic(RedissonClient redissonClient, String objectName) {
        RTopic<M> rTopic = redissonClient.getTopic(objectName);
        return rTopic;
    }


    /**
     * 获取锁
     *
     * @param redissonClient
     * @param objectName
     * @return
     */
    public RLock getRlock(RedissonClient redissonClient, String objectName) {
        RLock rLock = redissonClient.getLock(objectName);
        return rLock;
    }

    /**
     * 获取原子数
     *
     * @param redissonClient
     * @param objectName
     * @return
     */
    public RAtomicLong getRAtomicLong(RedissonClient redissonClient, String objectName) {
        RAtomicLong atomicLong = redissonClient.getAtomicLong(objectName);
        return atomicLong;
    }

    /**
     * 获取记数锁
     *
     * @param redissonClient
     * @param objectName
     * @return
     */
    public RCountDownLatch getRCountDownLatch(RedissonClient redissonClient, String objectName) {
        RCountDownLatch rCountDownLatch = redissonClient.getCountDownLatch(objectName);
        return rCountDownLatch;
    }
}

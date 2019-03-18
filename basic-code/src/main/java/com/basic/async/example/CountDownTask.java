package com.basic.async.example;

import java.util.concurrent.*;

/**
 * @author Jarvis
 * @date 2018/9/12
 */
public class CountDownTask {

    private static final int CORE_POOL_SIZE = 4;
    private static final int MAX_POOL_SIZE = 12;
    private static final long KEEP_ALIVE_TIME = 5L;
    private final static int QUEUE_SIZE = 1600;

    protected final static ExecutorService THREAD_POOL = new ThreadPoolExecutor(CORE_POOL_SIZE, MAX_POOL_SIZE,
            KEEP_ALIVE_TIME, TimeUnit.SECONDS, new LinkedBlockingQueue<>(QUEUE_SIZE));

    public static void main(String[] args) throws InterruptedException {
        // 新建一个为5的计数器
        //await()方法可以阻塞至超时或者计数器减至0，其他线程当完成自己目标的时候可以减少1，利用这个机制我们可以将其用来做并发。
        CountDownLatch countDownLatch = new CountDownLatch(5);
        OrderInfo orderInfo = new OrderInfo();
        THREAD_POOL.execute(() -> {
            System.out.println("当前任务Customer,线程名字为:" + Thread.currentThread().getName());
            orderInfo.setCustomerInfo(new CustomerInfo());
            countDownLatch.countDown();
        });

        THREAD_POOL.execute(() -> {
            System.out.println("当前任务Discount,线程名字为:" + Thread.currentThread().getName());
            orderInfo.setDiscountInfo(new DiscountInfo());
            countDownLatch.countDown();
        });

        THREAD_POOL.execute(() -> {
            System.out.println("当前任务Food,线程名字为:" + Thread.currentThread().getName());
            orderInfo.setFoodListInfo(new FoodListInfo());
            countDownLatch.countDown();
        });

        THREAD_POOL.execute(() -> {
            System.out.println("当前任务Tenant,线程名字为:" + Thread.currentThread().getName());
            orderInfo.setTenantInfo(new TenantInfo());
            countDownLatch.countDown();
        });

        THREAD_POOL.execute(() -> {
            System.out.println("当前任务OtherInfo,线程名字为:" + Thread.currentThread().getName());
            orderInfo.setOtherInfo(new OtherInfo());
            countDownLatch.countDown();
        });

        //await方法阻塞等待结果成功返回。
        countDownLatch.await(1, TimeUnit.SECONDS);
        System.out.println("主线程：" + Thread.currentThread().getName());
    }

}

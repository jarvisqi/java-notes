package com.basiccode.asynccode;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.*;

/**
 * @author Jarvis
 * @date 2018/9/12
 */
public class CompletableFutureParallel {
    private static final int CORE_POOL_SIZE = 4;
    private static final int MAX_POOL_SIZE = 12;
    private static final long KEEP_ALIVE_TIME = 5L;
    private final static int QUEUE_SIZE = 1600;
    protected final static ExecutorService THREAD_POOL = new ThreadPoolExecutor(CORE_POOL_SIZE, MAX_POOL_SIZE,
            KEEP_ALIVE_TIME, TimeUnit.SECONDS, new LinkedBlockingQueue<>(QUEUE_SIZE));

    public static void main(String[] args) throws InterruptedException, ExecutionException, TimeoutException {

        List<CompletableFuture> futures = new ArrayList<>();
        OrderInfo orderInfo = new OrderInfo();

        futures.add(CompletableFuture.runAsync(() -> {
            System.out.println("当前任务Customer,线程名字为:" + Thread.currentThread().getName());
            orderInfo.setCustomerInfo(new CustomerInfo());
        }, THREAD_POOL));

        futures.add(CompletableFuture.runAsync(() -> {
            System.out.println("当前任务Discount,线程名字为:" + Thread.currentThread().getName());
            orderInfo.setDiscountInfo(new DiscountInfo());
        }, THREAD_POOL));

        futures.add(CompletableFuture.runAsync(() -> {
            System.out.println("当前任务Food,线程名字为:" + Thread.currentThread().getName());
            orderInfo.setFoodListInfo(new FoodListInfo());
        }, THREAD_POOL));

        futures.add(CompletableFuture.runAsync(() -> {
            System.out.println("当前任务Other,线程名字为:" + Thread.currentThread().getName());
            orderInfo.setOtherInfo(new OtherInfo());
        }, THREAD_POOL));

        CompletableFuture allDoneFuture = CompletableFuture.allOf(futures.toArray(new CompletableFuture[futures.size()]));
        allDoneFuture.get(10, TimeUnit.SECONDS);
    }


}

package com.javabase.threaddemo;


import java.util.Arrays;
import java.util.List;
import java.util.concurrent.*;

/**
 * 并发编程 - 线程和执行器
 *
 * @author : Jarvis
 * @date : Created in 2018/5/26
 */
public class ThreadExecutorTest {


    public static void main(String[] args) {

        //executorDemo();
        //returnValue();
        //invokeAllDemo();
        //invokeAnyDemo();
        //scheduledExecutorDemo();
        scheduledExecutorDemo2();
    }


    /**
     * Executors类创建线程池
     * Executors必须显式的停止-否则它们将持续监听新的任务。
     * shutdwon()会等待正在执行的任务执行完
     * shutdownNow()会终止所有正在执行的任务并立即关闭execuotr
     */
    public static void executorDemo() {

        ExecutorService executorService = Executors.newFixedThreadPool(1);
        executorService.submit(() -> {
            String threadName = Thread.currentThread().getName();
            System.out.println("当前线程名称：" + threadName);
        });

        try {
            executorService.shutdown();
            executorService.awaitTermination(5, TimeUnit.SECONDS);
        } catch (InterruptedException e) {
            System.err.println("任务中断");
        } finally {
            if (!executorService.isTerminated()) {
                System.err.println("取消未完成的任务");
            }
            executorService.shutdownNow();
            System.out.println("关闭Executor完成");
        }


    }


    /**
     * executor 返回一个Future类型的结果，它可以用来在稍后某个时间取出实际的结果。
     *
     * @throws ExecutionException
     * @throws InterruptedException
     */
    private static void returnValue() throws ExecutionException, InterruptedException {
        //在休眠一分钟后返回一个整数。
        Callable<Integer> task = () -> {
            try {
                TimeUnit.SECONDS.sleep(1);
                return 123;
            } catch (InterruptedException e) {
                throw new IllegalStateException("任务中断", e);
            }
        };
        ExecutorService executor = Executors.newFixedThreadPool(1);
        Future<Integer> future = executor.submit(task);

        //返回值
        Integer result = future.get();
        //检查是否完成
        System.out.println("future done? " + future.isDone());
        System.out.print("result: " + result);
    }

    /**
     * future.get()调用都会阻塞 超时
     * Callables也是类似于runnables的函数接口，不同之处在于，Callable返回一个值。
     *
     * @throws InterruptedException
     * @throws ExecutionException
     * @throws TimeoutException
     */
    private static void timeOut() throws InterruptedException, ExecutionException, TimeoutException {
        ExecutorService executor = Executors.newFixedThreadPool(1);
        Future<Integer> future = executor.submit(() -> {
            try {
                TimeUnit.SECONDS.sleep(2);
                return 123;
            } catch (InterruptedException e) {
                throw new IllegalStateException("task interrupted", e);
            }
        });
        future.get(1, TimeUnit.SECONDS);

    }

    /**
     * Executors  通过invokeAll()一次批量提交多个callable 并返回值
     *
     * @throws InterruptedException
     */
    private static void invokeAllDemo() throws InterruptedException {
        // 新建线程池
        ExecutorService executor = Executors.newWorkStealingPool();
        List<Callable<String>> callables = Arrays.asList(
                () -> "task1",
                () -> "task2",
                () -> "task3"
        );

        executor.invokeAll(callables).stream().map(future -> {
            try {
                return future.get();
            } catch (Exception e) {
                throw new IllegalStateException(e);
            }
        }).forEach(System.out::println);
    }

    /**
     * invokeAny
     * 在等待future对象的过程中，这个方法将会阻塞直到第一个callable中止然后返回这一个callable的结果。
     * 通过invokeAny()将这些callable提交给一个executor，返回最快的callable的字符串结果 task2
     *
     * @throws ExecutionException
     * @throws InterruptedException
     */
    private static void invokeAnyDemo() throws ExecutionException, InterruptedException {
        ExecutorService executor = Executors.newWorkStealingPool();

        List<Callable<String>> callables = Arrays.asList(
                callable("task1", 2),
                callable("task2", 1),
                callable("task3", 3)
        );

        String result = executor.invokeAny(callables);
        System.out.println(result);
    }

    /**
     * 返回一个callable，callable休眠指定 的时间直到返回给定的结果。
     *
     * @param result
     * @param sleepSeconds
     * @return
     */
    static Callable<String> callable(String result, long sleepSeconds) {
        return () -> {
            TimeUnit.SECONDS.sleep(sleepSeconds);
            return result;
        };
    }

    /**
     * ScheduledExecutorService支持任务调度，持续执行或者延迟一段时间后执行。
     */
    private static void scheduledExecutorDemo() throws InterruptedException {
        ScheduledExecutorService scheduledExecutor = Executors.newScheduledThreadPool(1);
        Runnable task = () -> System.out.println("\nScheduling: " + System.nanoTime());
        //调度一个任务在延迟5秒后执行
        ScheduledFuture<?> future = scheduledExecutor.schedule(task, 5, TimeUnit.SECONDS);

        TimeUnit.MILLISECONDS.sleep(1337);
        //getDelay()方法来获得剩余的延迟。在延迟消逝后，任务将会并发执行
        long remainingDelay = future.getDelay(TimeUnit.MILLISECONDS);
        System.out.printf("Remaining Delay: %sms", remainingDelay);
    }

    /**
     * 调度了一个任务，并在一次执行的结束和下一次执行的开始之间设置了一个1秒钟的固定延迟。
     * 初始化延迟为0，任务执行时间为0。所以我们分别在0s,3s,6s,9s等间隔处结束一次执行
     */
    private static void scheduledExecutorDemo2() {
        ScheduledExecutorService executor = Executors.newScheduledThreadPool(1);
        Runnable task = () -> {
            try {
                TimeUnit.SECONDS.sleep(2);
                System.out.println("Scheduling: " + System.nanoTime());
            } catch (InterruptedException e) {
                System.err.println("任务中断");
            }
        };

        executor.scheduleWithFixedDelay(task, 0, 1, TimeUnit.SECONDS);
    }
}

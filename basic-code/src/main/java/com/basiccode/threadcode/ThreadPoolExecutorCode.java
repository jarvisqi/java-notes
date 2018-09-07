package com.basiccode.threadcode;

import com.google.common.util.concurrent.ThreadFactoryBuilder;

import java.util.concurrent.*;

/**
 * @author Jarvis
 * @date 2018/8/22
 */
public class ThreadPoolExecutorCode implements Runnable {
    private int taskNum;

    public ThreadPoolExecutorCode(int num) {
        this.taskNum = num;
    }

    /**
     * When an object implementing interface <code>Runnable</code> is used
     * to create a thread, starting the thread causes the object's
     * <code>run</code> method to be called in that separately executing
     * thread.
     * <p>
     * The general contract of the method <code>run</code> is that it may
     * take any action whatsoever.
     *
     * @see Thread#run()
     */
    @Override
    public void run() {
        System.out.println("正在执行task " + taskNum);
        try {
            Thread.sleep(4000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("task " + taskNum + "执行完毕");
    }
}


class Test {
    public static void main(String[] args) {
        //并不提倡直接使用ThreadPoolExecutor
        ThreadPoolExecutor executor = new ThreadPoolExecutor(5, 10,
                200, TimeUnit.MILLISECONDS, new ArrayBlockingQueue<Runnable>(5));

        // ThreadFactoryBuilder  : com.google.common
        ThreadFactory namedThreadFactory = new ThreadFactoryBuilder()
                .setNameFormat("demo-pool-%d").build();
        ExecutorService executorService = new ThreadPoolExecutor(5, 10,
                0L, TimeUnit.MILLISECONDS,
                new LinkedBlockingDeque<Runnable>(1024), namedThreadFactory, new ThreadPoolExecutor.AbortPolicy());

        for (int i = 0; i < 15; i++) {
            ThreadPoolExecutorCode myTask = new ThreadPoolExecutorCode(i);
            executor.execute(myTask);
            System.out.println("线程池中线程数目：" + executor.getPoolSize() + "，队列中等待执行的任务数目：" +
                    executor.getQueue().size() + "，已执行完其他的任务数目：" + executor.getCompletedTaskCount());
        }
        executor.shutdown();
    }
}

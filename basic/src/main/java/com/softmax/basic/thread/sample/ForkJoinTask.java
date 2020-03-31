package com.softmax.basic.thread.sample;

import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.RecursiveTask;

public class ForkJoinTask extends RecursiveTask<Long> {

    private static final long MAX = 1000000000L;
    private static final long THRESHOLD = 1000L;
    private long start;
    private long end;

    public ForkJoinTask(long start, long end) {
        this.start = start;
        this.end = end;
    }


    private static void test() {
        System.out.println("test");
        long start = System.currentTimeMillis();
        Long sum = 0L;
        for (long i = 0L; i <= MAX; i++) {
            sum += i;
        }
        System.out.println(sum);
        System.out.println(System.currentTimeMillis() - start + "ms");
    }

    private static void testForkJoin() {
        System.out.println("testForkJoin");
        long start = System.currentTimeMillis();
        ForkJoinPool forkJoinPool = new ForkJoinPool();
        long sum = forkJoinPool.invoke(new ForkJoinTask(1, MAX));
        System.out.println(sum);
        System.out.println(System.currentTimeMillis() - start + "ms");
    }


    @Override
    protected Long compute() {
        long sum = 0;
        //判断任务是否小于等于阈值1000，如果是就直接执行任务。否则分割成两个子任务
        if (end - start <= THRESHOLD) {
            for (long i = start; i <= end; i++) {
                sum += i;
            }
            return sum;
        } else {
            long mid = (start + end) / 2;

            ForkJoinTask task1 = new ForkJoinTask(start, mid);
            task1.fork();

            ForkJoinTask task2 = new ForkJoinTask(mid + 1, end);
            task2.fork();

            return task1.join() + task2.join();
        }

    }


    public static void main(String[] args) {
        test();
        System.out.println("\n--------------------------------------\n");
        testForkJoin();

        int t = 100 / 4;
        System.out.println(1 + "---" + t);
        System.out.println(t + 1 + "---" + t * 2);
        System.out.println((t * 2) + 1 + "---" + t * 3);
        System.out.println((t * 3) + 1 + "---" + 100);
    }
}

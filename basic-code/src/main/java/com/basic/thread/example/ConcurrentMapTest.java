package com.basic.thread.example;

import com.basic.locks.example.SynchronizationLocks;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.LongAccumulator;
import java.util.concurrent.atomic.LongAdder;
import java.util.function.LongBinaryOperator;
import java.util.stream.IntStream;

/**
 * @author : Jarvis
 * @date :  2018/5/29
 */
class ConcurrentMapTest {

    private static final int NUM_INCREMENTS = 10000;

    public void atomicIntegerDemo1() {
        //AtomicInteger代替Integer，我们就能线程安全地并发增加数值，而不需要同步访问变量。
        AtomicInteger atomicInt = new AtomicInteger(0);
        ExecutorService executor = Executors.newFixedThreadPool(2);
        IntStream.range(0, 1000).forEach(i -> executor.submit(atomicInt::incrementAndGet));

        SynchronizationLocks.stopExecutor(executor);
        // => 1000
        System.out.println(atomicInt.get());
    }

    public void atomicIntegerDemo2() {
        //AtomicInteger代替Integer，我们就能线程安全地并发增加数值，而不需要同步访问变量。
        // incrementAndGet()方法是原子操作，所以我们可以在多个线程中安全调用它。
        /**
         AtomicInteger atomicInt = new AtomicInteger(0);
         ExecutorService executor = Executors.newFixedThreadPool(2);
         IntStream.range(0, 1000).forEach(i -> {
         Runnable task = () -> atomicInt.updateAndGet(n -> n + 2);
         executor.submit(task);
         });

         SynchronizationLocks.stopExecutor(executor);
         // => 2000
         System.out.println(atomicInt.get());
         */


        //并发计算0~1000所有值的和：
        AtomicInteger atomicInt = new AtomicInteger(0);
        ExecutorService executor = Executors.newFixedThreadPool(2);
        //accumulateAndGet()方法接受另一种类型IntBinaryOperator的lambda表达式
        IntStream.range(0, 1000).forEach(i -> {
            executor.submit(() -> atomicInt.accumulateAndGet(i, (n, m) -> n + m));
        });

        SynchronizationLocks.stopExecutor(executor);
        // => 499500
        System.out.println(atomicInt.get());
    }

    /**
     * LongAdder是AtomicLong的替代，用于向某个数值连续添加值。
     * LongAdder提供了add()和increment()方法，就像原子数值类一样，同样是线程安全的
     */
    public void longAdderDemo() {
        LongAdder adder = new LongAdder();
        ExecutorService executor = Executors.newFixedThreadPool(2);
        //IntStream.range(0, NUM_INCREMENTS).forEach(i -> {
        //   executor.submit(() -> adder.add(2));
        //});

        // increment
        IntStream.range(0, NUM_INCREMENTS).forEach(i -> {
            executor.submit(adder::increment);
        });

        SynchronizationLocks.stopExecutor(executor);
        // System.out.format("Add: %d\n", adder.sumThenReset());
        System.out.format("Increment: Expected=%d; Is=%d\n", NUM_INCREMENTS, adder.sumThenReset());
    }

    /**
     * LongAccumulator以类型为LongBinaryOperatorlambda表达式构建
     * 而不是仅仅执行加法操作
     */
    public void longAccumulatorDemo() {
        ExecutorService executor = Executors.newFixedThreadPool(2);

        LongBinaryOperator op = (x, y) -> 2 * x + y;
        LongAccumulator accumulator = new LongAccumulator(op, 1L);
        IntStream.range(0, 10).forEach(i -> executor.submit(() -> accumulator.accumulate(i)));

        SynchronizationLocks.stopExecutor(executor);
        // => 2539
        System.out.println(accumulator.getThenReset());
    }

    /**
     * ConcurrentMap接口继承自Map接口，并定义了最实用的并发集合类型之一。
     * Java8通过将新的方法添加到这个接口，引入了函数式编程。
     * ConcurrentHashMap 是无序的
     */
    public void concurrentMapDemo() {
        ConcurrentHashMap<String, String> map = new ConcurrentHashMap<>();
        map.put("foo", "bar");
        map.put("han", "solo");
        map.put("r2", "d2");
        map.put("c3", "p0");

        // 方法可以并行迭代映射中的键值对
        map.forEach(1, (k, v) ->
                System.out.printf("key: %s; value: %s; thread: %s\n",
                        k, v, Thread.currentThread().getName()));

        System.out.println("\n");
        // search 只要返回了非空的结果，就不会往下搜索了
        String result = map.search(1, (k, v) -> {
                    System.out.println(Thread.currentThread().getName());
                    if ("foo".equals(k)) {
                        return v;
                    }
                    return null;
                }
        );

        System.out.println("Result: " + result);

        System.out.println("\n");
        String result1 = map.search(1, (k, v) -> {
                    System.out.println(Thread.currentThread().getName());
                    if (v.length() > 3) {
                        return v;
                    }
                    return null;
                }
        );

        System.out.println("Result: " + result1);

        System.out.println("\n");
        String result2 = map.reduce(1,
                (k, v) -> {
                    System.out.println("Transform: " + Thread.currentThread().getName());
                    return k + "=" + v;
                },
                (s1, s2) -> {
                    System.out.println("Reduce: " + Thread.currentThread().getName());
                    return s1 + ", " + s2;
                }
        );
        System.out.println("Result: " + result2);
    }
}

class CurrenytOut {

    public static void main(String[] args) {

        ConcurrentMapTest concurrent = new ConcurrentMapTest();
        //concurrent.atomicIntegerDemo1();
        //concurrent.atomicIntegerDemo2();
        //concurrent.longAdderDemo();
        //concurrent.longAccumulatorDemo();
        concurrent.concurrentMapDemo();

    }
}


package com.softmax.basic.async.sample;

import java.util.ArrayList;
import java.util.concurrent.atomic.LongAdder;

/**
 * @author Jarvis
 * @date 2018/9/12
 */
public class ParallelStream {

    public static void main(String[] args) {

        ArrayList<Integer> list = new ArrayList<>();
        for (int i = 1; i <= 100; i++) {
            list.add(i);
        }
        LongAdder sum = new LongAdder();
        list.parallelStream().forEach(integer -> {
            // System.out.println("当前线程" + Thread.currentThread().getName());
            sum.add(integer);
        });
        System.out.println(sum);
    }

}

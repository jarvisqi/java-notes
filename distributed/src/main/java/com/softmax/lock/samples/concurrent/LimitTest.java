package com.softmax.lock.samples.concurrent;

import java.util.Random;

public class LimitTest {

    public static void main(String[] args) throws InterruptedException {
        SlidingTimeWindow timeWindow = new SlidingTimeWindow();
        //开启一个线程判断当前的限流情况.
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    timeWindow.doCheck();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }).start();

        while (true) {
            //判断是否被限流了.
            if (!timeWindow.isLimit) {
                timeWindow.counter++;
                //未被限流执行相应的业务方法.
                //  executeBusinessCode();
                //模拟业务执行方法时间.
                Thread.sleep(new Random().nextInt(15));
                System.out.println("业务方法执行完了...");
            } else {
                System.out.println("被限流了，直接返回给用户");
            }
        }
    }


}

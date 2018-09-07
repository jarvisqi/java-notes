package com.basiccode.datastructure;

import java.util.concurrent.*;

/**
 * @author Jarvis
 * @date 2018/8/1
 */
public class QueueTest {

    public static void main(String[] args) {
        testBasket();
    }

    /**
     * 测试方法
     */
    public static void testBasket() {
        //定义一个苹果篮子
        final Basket basket = new Basket();

        /**
         * 生产者
         */
        class Produce implements Runnable {
            @Override
            public void run() {
                try {
                    while (true) {
                        // 生产苹果
                        System.out.println("生产者准备生产苹果：" + System.currentTimeMillis());
                        basket.produce();
                        System.out.println("生产者生产苹果完毕：" + System.currentTimeMillis());
                        System.out.println("生产完后有苹果：" + basket.getAppleNumber() + "个");
                        // 休眠300ms
                        Thread.sleep(300);
                    }
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }

        /**
         * 消费者
         */
        class Consumer implements Runnable {
            @Override
            public void run() {
                try {
                    while (true) {
                        // 消费苹果
                        System.out.println("消费者准备消费苹果：" + System.currentTimeMillis());
                        basket.consumer();
                        System.out.println("消费者消费苹果完毕：" + System.currentTimeMillis());
                        System.out.println("消费完后有苹果：" + basket.getAppleNumber() + "个");
                        // 休眠1000ms
                        Thread.sleep(1000);
                    }
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }

        ExecutorService service = Executors.newCachedThreadPool();

        service.submit(new Produce());
        service.submit(new Consumer());
        // 程序运行10s后，所有任务停止
        try {
            Thread.sleep(10000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        service.shutdownNow();
    }
}

class Basket {
    /**
     * 篮子，能够容纳3个苹果
     */
    private BlockingQueue<String> basket = new ArrayBlockingQueue<String>(3);

    /**
     * 生产苹果，放入篮子
     */
    public void produce() throws InterruptedException {
        basket.put("An Apple");
    }

    /**
     * 消费苹果，从篮子中取走
     *
     * @throws InterruptedException
     */
    public String consumer() throws InterruptedException {
        String apple = basket.take();
        return apple;
    }

    /**
     * 篮子中数量
     *
     * @return
     */
    public int getAppleNumber() {
        return basket.size();
    }
}
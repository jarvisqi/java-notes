package com.softmax.basic.thread.sample;

/**
 * @author Jarvis
 * @date 2018/8/10
 */
public class TestSync2 implements Runnable {

    int b = 100;

    synchronized void m1() throws InterruptedException {
        b = 1000;
        Thread.sleep(500);
        System.out.println("b=" + b);
    }

    synchronized void m2() throws InterruptedException {
        Thread.sleep(250);
        b = 2000;
    }

    public static void main(String[] args) throws InterruptedException {
        TestSync2 sync2 = new TestSync2();
        Thread thread = new Thread(sync2);
        thread.start();  //  start之后变成 Runnable
        sync2.m2();     //调用m2 打印 b=2000,然后调用 run 打印 b=1000
        System.out.println("main thread b=" + sync2.b);
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
        try {
            m1();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}

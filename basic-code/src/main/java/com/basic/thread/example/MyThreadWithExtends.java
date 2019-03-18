package com.basic.thread.example;

/**
 * @author Jarvis
 * @date 2018/8/22
 */
public class MyThreadWithExtends extends Thread {
    private int tickets = 10;

    @Override
    public void run() {
        for (int i = 0; i <= 100; i++) {
            if (tickets > 0) {
                System.out.println(Thread.currentThread().getName() + "--卖出票：" + tickets--);
            }
        }
    }


    public static void main(String[] args) {
        MyThreadWithExtends thread1 = new MyThreadWithExtends();
        MyThreadWithExtends thread2 = new MyThreadWithExtends();
        MyThreadWithExtends thread3 = new MyThreadWithExtends();

        thread1.start();
        thread2.start();
        thread3.start();
    }
}

class MyThreadWithImplements implements Runnable {

    private int tickets = 10;

    @Override
    public void run() {

        for (int i = 0; i <= 100; i++) {
            if (tickets > 0) {
                System.out.println(Thread.currentThread().getName() + "--卖出票：" + tickets--);
            }
        }
    }

    public static void main(String[] args) {
        MyThreadWithImplements myRunnable = new MyThreadWithImplements();
        Thread thread1 = new Thread(myRunnable, "窗口一");
        Thread thread2 = new Thread(myRunnable, "窗口二");
        Thread thread3 = new Thread(myRunnable, "窗口三");

        thread1.start();
        thread2.start();
        thread3.start();
    }

}

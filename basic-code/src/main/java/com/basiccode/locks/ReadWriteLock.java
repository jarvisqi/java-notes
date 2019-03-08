package com.basiccode.locks;

/**
 * 简单的读写锁实现
 *
 * @author Jarvis
 * @date 2019/3/8
 */
public class ReadWriteLock {

    /**
     * 读锁持有的个数
     */
    private Integer readCount = 0;

    /**
     * 写锁持有的个数
     */
    private Integer writeCount = 0;

    /**
     * 获取读锁,读锁在写锁不存在的时候才能获取
     */
    public synchronized void lockRead() throws InterruptedException {

        while (writeCount > 0) {
            wait();
        }
        readCount++;
    }

    /**
     * 释放读锁
     */
    public synchronized void unlockRead() {
        readCount--;
        notifyAll();
    }

    /**
     * 获取写锁,写锁在读锁不存在的时候才能获取
     */
    public synchronized void lockWrite() throws InterruptedException {

        //先判断是否有写请求
        while (writeCount > 0) {
            wait();
        }
        //此时已经不存在获取写锁的线程了,因此占坑,防止写锁饥饿
        writeCount++;
        // 读锁为0时获取写锁
        while (readCount > 0) {
            wait();
        }
    }

    /**
     * 释放写锁
     */
    public synchronized void unlockWrite() {
        writeCount--;
        notifyAll();
    }
}

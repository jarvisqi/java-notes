package com.softmax.design.single;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * 单例 - 饱汉模式
 *
 * @author Jarvis
 * @date 2018/7/26
 */
public class SingletonForFull {
    /**
     * step 1,讲 new SingletonForHunger() 堵死
     */
    private SingletonForFull() {
    }

    /**
     * step 2,不需要实例化，volatile是必须的
     */
    private static volatile SingletonForFull instance = null;

    /**
     * step 2,创建实例
     * 双重检查，指的是两次检查 instance 是否为 null。
     */
    public static SingletonForFull getInstance() {

        // 使用 synchronized
        if (instance == null) {
            // 加锁
            synchronized (SingletonForFull.class) {
                // 这一次判断也是必须的，不然会有并发问题
                if (instance == null) {
                    instance = new SingletonForFull();
                }
            }
        }
        // 使用 Lock 和 ReentrantLock
        Lock lock = new ReentrantLock();
        if (instance == null) {
            lock.lock();
            try {
                if (instance == null) {
                    instance = new SingletonForFull();
                }
            } finally {
                lock.unlock();
            }
        }


        return instance;
    }

}



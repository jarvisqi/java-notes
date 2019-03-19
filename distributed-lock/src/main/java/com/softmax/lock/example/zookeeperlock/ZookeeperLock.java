package com.softmax.lock.example.zookeeperlock;

import org.apache.curator.RetryPolicy;
import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.framework.recipes.locks.InterProcessMutex;
import org.apache.curator.retry.ExponentialBackoffRetry;

import java.util.concurrent.TimeUnit;

/**
 * zookeeper 分布式锁
 *
 * @author Jarvis
 * @date 2018/8/2
 */
public class ZookeeperLock {

    private static final String ADDRESS = "192.168.10.100:2181";

    public static CuratorFramework client;

    static {
        RetryPolicy retryPolicy = new ExponentialBackoffRetry(1000, 3);
        client = CuratorFrameworkFactory.newClient(ADDRESS, retryPolicy);
        client.start();
    }

    /**
     * 私有的默认构造子，保证外界无法直接实例化
     */
    private ZookeeperLock() {
    }

    /**
     * 类级的内部类，也就是静态的成员式内部类，该内部类的实例与外部类的实例
     * 没有绑定关系，而且只有被调用到才会装载，从而实现了延迟加载
     * 针对一件商品实现，多件商品同时秒杀建议实现一个map
     */
    private static class SingletonHolder {
        /**
         * 静态初始化器，由JVM来保证线程安全
         * 参考：http://ifeve.com/zookeeper-lock/
         * 这里建议 new 一个
         */
        private static InterProcessMutex mutex = new InterProcessMutex(client, "/curator/com.lock");
    }

    public static InterProcessMutex getMutex() {
        return SingletonHolder.mutex;
    }

    /**
     * 获得锁
     *
     * @param time
     * @param unit
     * @return
     */
    public static boolean lock(long time, TimeUnit unit) {
        try {
            return getMutex().acquire(time, unit);
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * 释放锁
     */
    public static void unlock() {
        try {
            getMutex().release();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}

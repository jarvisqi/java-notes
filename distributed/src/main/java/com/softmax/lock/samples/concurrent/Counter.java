package com.softmax.lock.samples.concurrent;

/**
 * 计数器算法
 */
public class Counter {
    //当前时间
    public long timeStamp = System.currentTimeMillis();
    //初始化计数器
    public int reqCount = 0;
    // 时间窗口内最大请求数
    public final int limit = 100;
    // 时间窗口ms
    public final int interval = 100 * 60;

    public boolean limit() {
        boolean result;
        long now = System.currentTimeMillis();
        if (now < timeStamp + interval) {
            // 在时间窗口内
            reqCount++;
            result = reqCount <= limit;
        } else {
            timeStamp = now;
            //超时后重置
            reqCount = 1;
            result = true;
        }
        return result;
    }
}


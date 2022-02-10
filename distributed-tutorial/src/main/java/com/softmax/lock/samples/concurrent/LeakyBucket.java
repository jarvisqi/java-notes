package com.softmax.lock.samples.concurrent;

/**
 * 漏桶算法
 *
 * @author Jarvis
 */
public class LeakyBucket {
    /**
     * 当前时间
     */
    private long timeStamp = System.currentTimeMillis();
    /**
     * 桶的容量
     */
    private long capacity;
    /**
     * 水漏出的速度
     */
    private long rate;
    /**
     * 当前水量(当前累积请求数)
     */
    private long water;

    public boolean grant() {
        long now = System.currentTimeMillis();
        // 先执行漏水，计算剩余水量
        //（2）漏水：通过时间差来计算漏水量。
        //（3）剩余水量：总水量-漏水量。
        water = Math.max(0, water - (now - timeStamp) * rate);
        timeStamp = now;
        if ((water + 1) < capacity) {
            // （1）未满加水：通过代码 water +=1进行不停加水的动作。
            water += 1;
            return true;
        } else {
            // 水满，拒绝加水
            return false;
        }
    }

}

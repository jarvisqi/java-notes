package com.softmax.lock.samples.concurrent;

import org.slf4j.ILoggerFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.LinkedList;

/**
 * 滑动时间窗口限流实现
 * 假设某个服务最多只能每秒钟处理100个请求，我们可以设置一个1秒钟的滑动时间窗口，
 * 窗口中有10个格子，每个格子100毫秒，每100毫秒移动一次，每次移动都需要记录当前服务请求的次数
 */
public class SlidingTimeWindow {

    private final Logger logger = LoggerFactory.getLogger(SlidingTimeWindow.class);

    // 时间窗口内最大请求数
    public final int limit = 100;
    // 服务访问次数
    Long counter = 0L;
    // 使用LinkedList来记录滑动窗口的10个格子。
    LinkedList<Long> slots = new LinkedList<>();
    // 时间划分多少段落
    int split = 10;
    // 是否限流了,true:限流了，false：允许正常访问。
    boolean isLimit = false;

    public void doCheck() throws InterruptedException {
        while (true) {
            slots.add(counter);
            // 超出了，就把第一个移出。
            if (slots.size() > split) {
                slots.removeFirst();
            }
            // 比较最后一个和第一个，两者相差100以上就限流
            if ((slots.peekLast() - slots.peekFirst()) > limit) {
                logger.info("限流了");
                isLimit = true;
            } else {
                isLimit = false;
            }

            Thread.sleep(1000 / split);
        }
    }
}

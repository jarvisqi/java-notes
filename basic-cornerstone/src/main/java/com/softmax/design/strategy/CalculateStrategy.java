package com.softmax.design.strategy;

/**
 * 计算策略接口
 *
 * @author Jarvis
 * @date 2019/10/22
 */
public interface CalculateStrategy {
    /**
     * 计算
     *
     * @param a
     * @param b
     * @return
     */
    int calculate(int a, int b);
}

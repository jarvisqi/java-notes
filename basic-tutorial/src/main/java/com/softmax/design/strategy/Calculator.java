package com.softmax.design.strategy;

import java.math.BigDecimal;

/**
 * 计算器类
 *
 * @author Jarvis
 * @date 2019/10/22
 */
public class Calculator {

    /**
     * 拥有某种算法策略
     */
    private CalculateStrategy calculateStrategy;

    /**
     * 接入算法策略
     *
     * @param calculateStrategy
     */
    public void setCalculateStrategy(CalculateStrategy calculateStrategy) {
        this.calculateStrategy = calculateStrategy;
    }

    public int getResult(int a, int b) {
        return calculateStrategy.calculate(a, b);
    }

    public BigDecimal getResult(float a, float b) {
        return calculateStrategy.calculate(a, b);
    }
}

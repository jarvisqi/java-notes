package com.softmax.design.strategy;

import java.math.BigDecimal;

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

    /**
     * 计算
     *
     * @param a
     * @param b
     * @return
     */
    BigDecimal calculate(float a, float b);
}

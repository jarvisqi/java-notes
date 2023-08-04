package com.softmax.design.strategy;

import java.math.BigDecimal;

/**
 * 实现加法策略
 *
 * @author Jarvis
 * @date 2019/10/22
 */
class Addition implements CalculateStrategy {

    @Override
    public int calculate(int a, int b) {
        return a + b;
    }

    @Override
    public BigDecimal calculate(float a, float b) {
        return BigDecimal.valueOf(0);
    }
}

/**
 * 实现减法策略
 */
class Substanction implements CalculateStrategy {

    @Override
    public int calculate(int a, int b) {
        return a - b;
    }

    @Override
    public BigDecimal calculate(float a, float b) {
        return BigDecimal.valueOf(0);
    }
}


/**
 * 实现乘法策略
 *
 * @author Jarvis
 * @date 2019/10/22
 */
class Multiplication implements CalculateStrategy {

    @Override
    public int calculate(int a, int b) {
        return Math.multiplyExact(a, b);
    }

    @Override
    public BigDecimal calculate(float a, float b) {
        BigDecimal decimala = new BigDecimal(a);
        BigDecimal decimalb = new BigDecimal(b);
        return decimala.multiply(decimalb);
    }
}

package com.softmax.design.strategy;

import java.math.BigDecimal;

/**
 * 实现算法接口 （加法）
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
 * 实现算法接口  （减法）
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
 * 实现算法接口 （乘法）
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

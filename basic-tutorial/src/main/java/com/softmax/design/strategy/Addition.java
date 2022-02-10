package com.softmax.design.strategy;

import java.math.BigDecimal;

/**
 * ʵ���㷨�ӿ� ���ӷ���
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
 * ʵ���㷨�ӿ�  ��������
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
 * ʵ���㷨�ӿ� ���˷���
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

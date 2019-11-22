package com.softmax.design.strategy;

/**
 * 实现算法接口
 *
 * @author Jarvis
 * @date 2019/10/22
 */
class Addition implements CalculateStrategy {

    @Override
    public int calculate(int a, int b) {
        return a + b;
    }
}

/**
 * 实现算法接口
 */
class Substanction implements CalculateStrategy {

    @Override
    public int calculate(int a, int b) {
        return a - b;
    }
}

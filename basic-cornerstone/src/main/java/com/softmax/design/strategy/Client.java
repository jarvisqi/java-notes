package com.softmax.design.strategy;

/**
 * 客户端调用
 * 策略模式
 * 有新的算法是不需要再更改任何现有代码的，只需要新写一个算法比如乘法Multiplication，
 * 并实现calculate方法，接下来要做的只是组装上去便可以使用了。
 *
 * @author Jarvis
 * @date 2019/10/22
 */

public class Client {

    public static void main(String[] args) {
        Calculator calculator = new Calculator();
        //加法计算器
        calculator.setCalculateStrategy(new Addition());
        int result = calculator.getResult(1, 2);
        System.out.println(result);

        //减法
        calculator.setCalculateStrategy(new Substanction());
        result = calculator.getResult(1, 2);
        System.out.println(result);
    }
}

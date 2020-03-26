package com.softmax.design.strategy;

import java.math.BigDecimal;
import java.math.RoundingMode;

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


        calculator.setCalculateStrategy(new Multiplication());
        result = calculator.getResult(10090, 188893);
        System.out.println(result);


        BigDecimal decimalResult = calculator.getResult((float) 107.34, (float) 359.233);
        System.out.println(decimalResult);
        System.out.println(decimalResult.setScale(6, RoundingMode.CEILING));
        System.out.println(decimalResult.setScale(6, RoundingMode.FLOOR));
        System.out.println(decimalResult.setScale(6, RoundingMode.DOWN));
        System.out.println(decimalResult.setScale(6, RoundingMode.HALF_DOWN));
        System.out.println(decimalResult.setScale(6, RoundingMode.HALF_UP));
    }
}

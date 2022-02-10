package com.softmax.design.strategy;

import java.math.BigDecimal;
import java.math.RoundingMode;

/**
 * �ͻ��˵���
 * ����ģʽ
 * ���µ��㷨�ǲ���Ҫ�ٸ����κ����д���ģ�ֻ��Ҫ��дһ���㷨����˷�Multiplication��
 * ��ʵ��calculate������������Ҫ����ֻ����װ��ȥ�����ʹ���ˡ�
 *
 * @author Jarvis
 * @date 2019/10/22
 */

public class Client {

    public static void main(String[] args) {
        Calculator calculator = new Calculator();
        //�ӷ�������
        calculator.setCalculateStrategy(new Addition());
        int result = calculator.getResult(1, 2);
        System.out.println(result);

        //����
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

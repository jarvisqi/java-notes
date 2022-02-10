package com.softmax.design.decorator;

/**
 * ��� ʵ�����ϻ���
 *
 * @author Jarvis
 * @date 2020/06/26
 */
class BlackTea extends Beverage {
    @Override
    public String getDescription() {
        return "���";
    }

    @Override
    public double cost() {
        return 10;
    }
}


/**
 * �̲� ʵ�����ϻ���
 *
 * @author Jarvis
 * @date 2020/06/26
 */
class GreenTea extends Beverage {
    @Override
    public String getDescription() {
        return "�̲�";
    }

    @Override
    public double cost() {
        return 12;
    }
}


/**
 * ���� ʵ�����ϻ���
 *
 * @author Jarvis
 * @date 2020/06/26
 */
class Coffee extends Beverage {
    @Override
    public String getDescription() {
        return "����";
    }

    @Override
    public double cost() {
        return 18;
    }
}


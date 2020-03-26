package com.softmax.design.decorator;

/**
 * 红茶 实现饮料基类
 *
 * @author Jarvis
 * @date 2020/06/26
 */
class BlackTea extends Beverage {
    @Override
    public String getDescription() {
        return "红茶";
    }

    @Override
    public double cost() {
        return 10;
    }
}


/**
 * 绿茶 实现饮料基类
 *
 * @author Jarvis
 * @date 2020/06/26
 */
class GreenTea extends Beverage {
    @Override
    public String getDescription() {
        return "绿茶";
    }

    @Override
    public double cost() {
        return 12;
    }
}


/**
 * 咖啡 实现饮料基类
 *
 * @author Jarvis
 * @date 2020/06/26
 */
class Coffee extends Beverage {
    @Override
    public String getDescription() {
        return "咖啡";
    }

    @Override
    public double cost() {
        return 18;
    }
}


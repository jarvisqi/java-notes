package com.softmax.design.decorator;

/**
 * 定义三个基础饮料实现类，红茶、绿茶和咖啡
 *
 * @author Jarvis
 * @date 2020/06/26
 */
class BlackTea extends Bevarage {
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
 * 绿茶
 *
 * @author Jarvis
 * @date 2020/06/26
 */
class GreenTea extends Bevarage {
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
 * 咖啡
 *
 * @author Jarvis
 * @date 2020/06/26
 */
class Coffee extends Bevarage {
    @Override
    public String getDescription() {
        return "咖啡";
    }

    @Override
    public double cost() {
        return 18;
    }
}


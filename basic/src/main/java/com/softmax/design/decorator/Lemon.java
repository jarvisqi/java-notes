package com.softmax.design.decorator;

/**
 * 定义具体的调味剂
 */
public class Lemon extends Condiment {

    private Beverage bevarage;

    /**
     * 需要传入具体的饮料，如需要传入没有被装饰的红茶或绿茶，
     * 也可以传入已经装饰好的芒果绿茶，这样可以做芒果柠檬绿茶
     *
     * @param bevarage
     */
    public Lemon(Beverage bevarage) {
        this.bevarage = bevarage;
    }

    /**
     * 装饰
     *
     * @return
     */
    @Override
    public String getDescription() {
        return bevarage.getDescription() + ",加【柠檬】";
    }

    /**
     * 装饰
     *
     * @return
     */
    @Override
    public double cost() {
        // 加柠檬需要 2 元
        return bevarage.cost() + 2;
    }
}


class Mango extends Condiment {

    private Beverage beverage;

    public Mango(Beverage beverage) {
        this.beverage = beverage;
    }

    @Override
    public String getDescription() {
        return beverage.getDescription() + ",加【芒果】";
    }


    @Override
    public double cost() {
        //加芒果需要加1.5元
        return beverage.cost() + 1.5;
    }
}
package com.softmax.design.decorator;

/**
 * 定义柠檬、芒果等具体的调料，它们属于装饰者
 */
public class Lemon extends Condiment {

    private final Bevarage bevarage;

    /**
     * // 这里很关键，需要传入具体的饮料，如需要传入没有被装饰的红茶或绿茶，
     * // 当然也可以传入已经装饰好的芒果绿茶，这样可以做芒果柠檬绿茶
     *
     * @param bevarage
     */
    public Lemon(Bevarage bevarage) {
        this.bevarage = bevarage;
    }

    /**
     * // 装饰
     *
     * @return
     */
    @Override
    public String getDescription() {
        return bevarage.getDescription() + ", 加柠檬";
    }

    /**
     * װ��
     *
     * @return
     */
    @Override
    public double cost() {
        // 装饰
        return bevarage.cost() + 2; // 加柠檬需要 2 元
    }
}


class Mango extends Condiment {

    private final Bevarage bevarage;

    public Mango(Bevarage bevarage) {
        this.bevarage = bevarage;
    }

    @Override
    public String getDescription() {
        return bevarage.getDescription() + ", 加芒果";
    }

    public double cost() {
        return bevarage.cost() + 3; // 加芒果需要 3 元
    }
}
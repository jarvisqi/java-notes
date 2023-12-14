package com.softmax.design.proxy;

import com.softmax.design.common.Chicken;
import com.softmax.design.common.Food;
import com.softmax.design.common.Noodle;

/**
 *
 */
class FoodServiceImpl implements FoodService {

    /**
     * 鸡肉饭
     *
     * @return
     */
    @Override
    public Food createChicken() {
        Food food = new Chicken("鸡肉饭");
        food.setSpicy("1g");
        food.setSalt("3g");
        return food;
    }

    /**
     * 面条
     *
     * @return
     */
    @Override
    public Food createNoodle() {
        Food food = new Noodle("牛肉面");
        food.setSpicy("5g");
        food.setSalt("5g");
        return food;
    }
}

package com.softmax.design.proxy;

import com.softmax.design.common.Food;

/**
 * 接口
 */
interface FoodService {
    /**
     * 鸡肉饭
     *
     * @return food
     */
    Food createChicken();

    /**
     * 面条
     *
     * @return food
     */
    Food createNoodle();
}

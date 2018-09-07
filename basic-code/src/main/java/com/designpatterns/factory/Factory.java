package com.designpatterns.factory;

import com.designpatterns.common.Food;

/**
 * 工厂模式
 *
 * @author Jarvis
 * @date 2018/7/25
 */
public interface Factory {
    Food createFood(String name);
}

class ChineseFoodFactory implements Factory {

    @Override
    public Food createFood(String name) {
        switch (name) {
            case "A":
                return new ChineseFoodA("刀削面");
            case "B":
                return new ChineseFoodA("火锅");
            default:
                return new Food("中国菜");
        }
    }
}

class AmericanFoodFactory implements Factory {
    @Override
    public Food createFood(String name) {
        switch (name) {
            case "A":
                return new AmericanFoodA("各种汉堡");
            case "B":
                return new AmericanFoodB("各种烤肉");
            default:
                return new Food("美国菜");
        }
    }
}

class ChineseFoodA extends Food {
    ChineseFoodA(String name) {
        super(name);
    }
}

class ChineseFoodB extends Food {
    ChineseFoodB(String name) {
        super(name);
    }
}

class AmericanFoodA extends Food {
    AmericanFoodA(String name) {
        super(name);
    }
}


class AmericanFoodB extends Food {
    AmericanFoodB(String name) {
        super(name);
    }
}

class AppFood {

    public static void main(String[] args) {
        //step 1：创建工厂
        Factory factory = new AmericanFoodFactory();
        //step 2：工厂创建食物
        Food food = factory.createFood("A");
        System.out.println(food.getName());

        //step 1：创建工厂
        Factory factory1 = new ChineseFoodFactory();
        //step 2：工厂创建食物
        Food food1 = factory1.createFood("A");
        System.out.println(food1.getName());
    }
}

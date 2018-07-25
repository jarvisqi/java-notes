package com.designpatterns.factory;

/**
 * 简单工厂
 *
 * @author Jarvis
 * @date 2018/7/25
 */
public class SimpleFactory {
    public static Food createFood(String name) {
        switch (name) {
            case "noodle":
                return new LanZhouNoodle("兰州拉面");
            case "chicken":
                return new HuangMenChicken("黄焖鸡拉面");
            default:
                return new Food("各种拉面");
        }
    }
}


class Food {
    public Food() {

    }

    public Food(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    private String name;
}

class LanZhouNoodle extends Food {
    LanZhouNoodle(String name) {
        super(name);
    }
}

class HuangMenChicken extends Food {
    HuangMenChicken(String name) {
        super(name);
    }
}


class App {
    public static void main(String[] args) {

        var food = SimpleFactory.createFood("noodle");

        System.out.println(food.getName());
    }
}
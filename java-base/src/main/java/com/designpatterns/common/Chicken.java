package com.designpatterns.common;

/**
 * @author Jarvis
 * @date 2018/7/27
 */
public class Chicken extends Food {

    public Chicken(String name) {
        super(name);
    }

    @Override
    public String toString() {
        return "名字：" + getName() + " 辣椒：" + getSpicy() + " 盐：" + getSalt();
    }
}

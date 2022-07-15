package com.softmax.design.decorator;

/**
 * 定义抽象饮料类
 *
 * @author Jarvis
 * @date 2020/03/26
 */
public abstract class Beverage {

    /**
     * 返回描述信息
     */
    public abstract String getDescription();

    /**
     * 返回价格
     */
    public abstract double cost();


}

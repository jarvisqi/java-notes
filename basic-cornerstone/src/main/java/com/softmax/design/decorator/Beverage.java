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
     *
     * @return
     */
    public abstract String getDescription();

    /**
     * 返回价格
     *
     * @return
     */
    public abstract double cost();


}

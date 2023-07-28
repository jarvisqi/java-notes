package com.softmax.design.decorator;

/**
 * 定义饮料抽象基类
 *
 * @author Jarvis
 * @date 2020/03/26
 */
public abstract class Bevarage {

    /**
     * 返回描述
     */
    public abstract String getDescription();

    /**
     * 返回价格
     */
    public abstract double cost();


}

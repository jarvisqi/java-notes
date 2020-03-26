package com.softmax.design.observer;

/**
 * 定义观察者接口
 *
 * @author Jarvis
 * @date 2020/03/26
 */

public abstract class Observer {

    protected Subject subject;

    public abstract void update();
}

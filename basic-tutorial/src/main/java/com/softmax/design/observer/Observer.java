package com.softmax.design.observer;

/**
 * ����۲��߽ӿ�
 *
 * @author Jarvis
 * @date 2020/03/26
 */

public abstract class Observer {

    protected Subject subject;

    public abstract void update();
}

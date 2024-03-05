package com.softmax.design.observer;

import java.util.ArrayList;
import java.util.List;

/**
 * 定义主题，每个主题需要持有观察者列表的引用，用于在数据变更的时候通知各个观察者：
 *
 * @author Jarvis
 * @date 2020/03/26
 */
public class Subject {

    private final List<Observer> observers = new ArrayList<>();

    private int state;

    public int getState() {
        return state;
    }

    public void setState(int state) {
        this.state = state;
        // 数据已变更，通知观察者们
        notifyAllObservers();
    }

    /**
     * 注册观察者
     *
     * @param observer
     */
    public void attach(Observer observer) {
        observers.add(observer);
    }

    /**
     * 通知观察者
     */
    public void notifyAllObservers() {
        for (Observer observer : observers) {
            //��������
            observer.update();
        }
    }

}

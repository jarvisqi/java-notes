package com.softmax.design.observer;

import java.util.ArrayList;
import java.util.List;

/**
 * 定义观察者关心的主题和主题有数据
 *
 * @author Jarvis
 * @date 2020/03/26
 */
public class Subject {

    private List<Observer> observers = new ArrayList<>();

    private int state;

    public int getState() {
        return state;
    }

    public void setState(int state) {
        this.state = state;
        //通知观察者，变更
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
            //更新数据
            observer.update();
        }
    }

}

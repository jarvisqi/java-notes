package com.softmax.design.observer;

import java.util.ArrayList;
import java.util.List;

/**
 * ����۲��߹��ĵ����������������
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
        //֪ͨ�۲��ߣ����
        notifyAllObservers();
    }

    /**
     * ע��۲���
     *
     * @param observer
     */
    public void attach(Observer observer) {
        observers.add(observer);
    }

    /**
     * ֪ͨ�۲���
     */
    public void notifyAllObservers() {
        for (Observer observer : observers) {
            //��������
            observer.update();
        }
    }

}

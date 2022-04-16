package com.softmax.design.common;

public class Bulb implements Switchable {
    @Override
    public void on() {
        System.out.println("通电，灯亮。");
    }

    @Override
    public void off() {
        System.out.println("断电，灯灭。");
    }
}

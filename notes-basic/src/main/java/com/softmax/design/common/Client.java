package com.softmax.design.common;

public class Client {
    public static void main(String[] args) {
        System.out.println("===客户端用【电线】直接操作灯泡===");
        Bulb bulb = new Bulb();
        bulb.on();
        bulb.off();

        System.out.println("========================");

        System.out.println("===客户端用【开关】操作电器===");
        Switcher switcher = new Switcher();
        //灯泡接入开关。
        switcher.setSwitchable(new Bulb());
        switcher.buttonOnClick();
        switcher.buttonOffClick();

        //风扇接入开关。
        switcher.setSwitchable(new Fan());
        switcher.buttonOnClick();
        switcher.buttonOffClick();

    }
}

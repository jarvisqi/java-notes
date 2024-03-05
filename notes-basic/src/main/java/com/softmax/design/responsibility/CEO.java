package com.softmax.design.responsibility;

/**
 * @author Jarvis
 */
public class CEO {

    private String name;

    public CEO(String name) {
        this.name = name;
    }

    public boolean approve(int amount) {
        if (amount <= 10000) {
            System.out.println("审批通过。【CEO：" + name + "】");
            return true;
        } else {
            System.out.println("无权审批，请找上级。【CEO：" + name + "】");
            return false;
        }
    }
}
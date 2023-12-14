package com.softmax.design.responsibility;

/**
 * @author Jarvis
 */
public class ResponsibilityTest {

    public static void main(String[] args) {
        //出差花费10000元
        int amount = 10000;
        Staff staff = new Staff("张飞");

        if (!staff.approve(amount)) {
            Manager manager = new Manager("关羽");
            if (!manager.approve(amount)) {
                CEO ceo = new CEO("刘备");
                ceo.approve(amount);
            }
        }
    }
}

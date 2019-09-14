package com.softmax.design.adapter;

/**
 *
 */
interface Human {

    void eat();

    void say();

    void run();
}


class Action {
    public void say() {
        System.out.println("说话");
    }

    public void run() {
        System.out.println("跑步");
    }
}

class UserAdapter extends Action implements Human {

    @Override
    public void eat() {
        System.out.println("吃草");
    }

    /**
     * 通过继承的方法，适配器自动获得了所需要的大部分方法
     */
    @Override
    public void say() {
        super.say();
    }

    @Override
    public void run() {
        super.run();
    }
}

/**
 * 类适配模式
 *
 * @author Jarvis
 * @date 2018/7/27
 */
public class ClassAdapter {

    public static void main(String[] args) {
        Human userAdapter = new UserAdapter();

        userAdapter.eat();
        
        userAdapter.say();
        userAdapter.run();
    }
}

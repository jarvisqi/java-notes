package com.softmax.features;

public interface UserInterface {
    private void getUsername() {
        System.err.println("你好，我是刘备！");
    }

    private void getPassword() {
        System.err.println("你好，我是徐庶！");
    }

    default void getData() {
        getUsername();
        getPassword();
    }
}


/**
 * 只能实现default方法，无法实现private方法。
 */
class UserImpl implements UserInterface {

    @Override
    public void getData() {
        UserInterface.super.getData();
    }

    public static void main(String[] args) {
        UserImpl user = new UserImpl();
        user.getData();
    }
}
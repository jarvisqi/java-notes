package com.softmax.basic.aop.sample;

/**
 * @author Jarvis
 * @date 2018/8/22
 */
public class UserBeanImpl implements UserBean {
    private String user = null;

    public UserBeanImpl() {
    }

    public UserBeanImpl(String user) {
        this.user = user;
    }

    public void setUser(String user) {
        this.user = user;
        System.out.println("this is setUser() method!");
    }

    public String getUserName() {
        return user;
    }

    @Override
    public void getUser() {
        System.out.println("this is getUser() method!");
    }

    @Override
    public void addUser() {
        System.out.println("this is addUser() method!");
    }

    @Override
    public void updateUser() {
        System.out.println("this is updateUser() method!");
    }

    @Override
    public void deleteUser() {
        System.out.println("this is deleteUser() method!");
    }


}

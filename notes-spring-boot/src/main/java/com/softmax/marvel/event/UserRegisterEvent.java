package com.softmax.marvel.event;

import org.springframework.context.ApplicationEvent;

/**
 * 使用Spring 的Event事件机制，实现观察者模式
 * 用户注册事件
 *
 * @author Jarvis
 */
public class UserRegisterEvent extends ApplicationEvent {
    /**
     * 用户名
     */
    private String username;

    public UserRegisterEvent(Object source) {
        super(source);
    }

    public UserRegisterEvent(Object source, String username) {
        super(source);
        this.username = username;
    }

    public String getUsername() {
        return username;
    }

}

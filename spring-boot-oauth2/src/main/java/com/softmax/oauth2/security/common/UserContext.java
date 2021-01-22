package com.softmax.oauth2.security.common;

import com.softmax.oauth2.security.user.entity.LoginUser;

/**
 * 当前用户上下文实体
 *
 * @author Jarvis
 */
public class UserContext {

    /**
     * 把构造函数私有化，外部不能new
     */
    private UserContext() {
    }

    private static final ThreadLocal<LoginUser> context = new ThreadLocal<>();

    /**
     * 存放用户信息
     *
     * @param user
     */
    public static void set(LoginUser user) {
        context.set(user);
    }

    /**
     * 获取用户信息
     *
     * @return
     */
    public static LoginUser get() {
        return context.get();
    }

    /**
     * 清除当前线程内引用，防止内存泄漏
     */
    public static void remove() {
        context.remove();
    }
}

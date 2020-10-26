package com.softmax.design.single;

import java.time.LocalDate;

/**
 * 单例- 饥饿模式
 *
 * @author Jarvis
 * @date 2018/7/25
 */
public class SingletonForHunger {

    /**
     * step 1,讲 new SingletonForHunger() 堵死
     */
    private SingletonForHunger() {
    }

    /**
     * step 2,创建私有静态实例，意味着这个类第一次使用的时候就会进行创建
     */
    private static final SingletonForHunger instance = new SingletonForHunger();

    public static SingletonForHunger getInstance() {
        return instance;
    }

    /**
     * 瞎写一个静态方法。这里想说的是，如果我们只是要调用 Singleton.getDate(...)，
     * 本来是不想要生成 Singleton 实例的，不过没办法，已经生成了
     *
     * @param mode
     * @return
     */
    public static LocalDate getCurrentDate(String mode) {
        return LocalDate.now();
    }
}

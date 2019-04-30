package com.basic.ioc.sample;

/**
 * SpringIOC 实现原理
 * 工厂模式 + 反射
 *
 * @author Jarvis
 * @date 2018/8/3
 */
public class SpringIOC {

    public static void main(String[] args) {
        Human woman = Factory.getInstance("Woman");
        if (woman != null) {
            woman.run();
        }
    }
}


interface Human {
    void run();
}

class Man implements Human {

    @Override
    public void run() {
        System.out.println("Man");
    }
}

class Woman implements Human {

    @Override
    public void run() {
        System.out.println("Woman");
    }
}

class Factory {

    public static Human getInstance(String className) {
        Human human = null;
        try {
            human = (Human) Class.forName(className).getDeclaredConstructor().newInstance();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return human;
    }
}

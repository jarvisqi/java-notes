package com.designpatterns.adapter;


/**
 * 鸭接口
 */
interface Duck {
    public void quack(); // 鸭的呱呱叫

    public void fly(); // 飞
}

/**
 * 鸡接口
 */
interface Cock {
    public void gobble(); // 鸡的咕咕叫

    public void fly(); // 飞
}

/**
 * 野鸡
 */
class WildCock implements Cock {

    @Override
    public void gobble() {
        System.out.println("咕咕叫");
    }

    @Override
    public void fly() {
        System.out.println("鸡也会飞哦");
    }
}

/**
 * 鸭接口有 fly() 和 quare() 两个方法，鸡 Cock 如果要冒充鸭，fly() 方法是现成的，
 * 但是鸡不会鸭的呱呱叫，没有 quack() 方法。这个时候就需要适配了：
 */
class CockAdapter implements Duck {

    Cock cock;

    /**
     * 构造方法中需要一个鸡的实例，此类就是将这只鸡适配成鸭来用
     *
     * @param cock
     */
    public CockAdapter(Cock cock) {
        this.cock = cock;
    }

    /**
     * 实现鸭的呱呱叫方法
     */
    @Override
    public void quack() {
        // 内部其实是一只鸡的咕咕叫
        cock.gobble();
    }

    @Override
    public void fly() {
        cock.fly();
    }
}

/**
 * 对象适配器模式
 *
 * @author Jarvis
 * @date 2018/7/27
 */
public class ObjectAdapter {

    public static void main(String[] args) {
        //创建一只野鸡
        Cock wildCock = new WildCock();
        // 成功将野鸡适配成鸭
        Duck duck = new CockAdapter(wildCock);
        duck.quack();
    }
}

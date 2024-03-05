package com.softmax.design.proxy;

/**
 * 客户端调用，注意，我们要用代理来实例化接口：
 */
class ProxyTest {
    public static void main(String[] args) {
        //创建代理对象
        FoodService serviceProxy = new FoodServiceProxy(new FoodServiceImpl());
        //使用代理对象 制作鸡肉饭
        var chicken = serviceProxy.createChicken();
        System.out.println(chicken.toString());
        //使用代理对象 制作牛肉面
        var noodle = serviceProxy.createNoodle();
        System.out.println(noodle.toString());
    }
}

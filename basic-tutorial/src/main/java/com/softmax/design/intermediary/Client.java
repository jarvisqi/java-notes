package com.softmax.design.intermediary;

/**
 * 中介模式：，在软件架构中也非常常见，当下流行的微服务分布式软件架构所用到的注册中心，
 * 例如最常用到的云组件Eureka Server，其作用就是为众多分布式服务提供注册发现服务，它正是充当像中介一样的角色。
 * <p>
 * 中介模式符合迪米特法则，它解决了对象间过度耦合、复杂频繁交互的问题，打破了你中有我，我中有你的相互依赖，
 * 第三方的介入有助于双方调停，打破如胶似漆、纠缠不休的关系，让他们之间变得松散、自由、独立。
 *
 * @author Jarvis
 */
public class Client {

    public static void main(String[] args) {
        People p3 = new People("张三");
        People p4 = new People("李四");

        p3.connect(p4);
        p4.connect(p3);

        p3.talk("你好。");
        p4.talk("早上好，三哥。");
    }

}
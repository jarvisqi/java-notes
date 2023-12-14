package com.softmax.design.decorator;

/**
 * 装饰模式：
 * 每个组件或者各司其职，不做和自己不相关的事，然后把部件层层叠加，并根据需求组装成型，以达最终的装饰目的。
 * new BufferedReader(new InputStreamReader(new FileInputStream(filePath)));
 */
public class DecoratorTest {

    public static void main(String[] args) {
        // step1，我们需要一个基础饮料，红茶、绿茶或咖啡
        Bevarage bevarage = new GreenTea();

        //step2，开始装饰，即加调味剂

        //加柠檬
        bevarage = new Lemon(bevarage);
        //加芒果
        bevarage = new Mango(bevarage);

        //step3，输出打印
        System.out.println("饮料：" + bevarage.getDescription() + "；\n 价格： ￥" + bevarage.cost());

        //双芒果-双份柠檬-红茶：
        bevarage = new Mango(new Mango(new Lemon(new Lemon(new BlackTea()))));
        System.out.println("饮料：" + bevarage.getDescription() + "；\n 价格： ￥" + bevarage.cost());

    }
}

package com.softmax.design.bridge;

/**
 * 桥梁模式
 *
 * @author Jarvis
 * @date 2018/8/28
 */
public interface DrawAPI {

    /**
     * 画图
     *
     * @param graphicsName 图形名称
     * @param radius       圆角
     * @param x
     * @param y
     */
    void draw(String graphicsName, int radius, int x, int y);
}

/**
 * 红色
 */
class RedPen implements DrawAPI {
    @Override
    public void draw(String graphicsName, int radius, int x, int y) {
        System.out.printf("用红色笔画%s，radius:%d, x:%d, y:%d%n", graphicsName, radius, x, y);
    }
}

/**
 * 绿色
 */
class GreenPen implements DrawAPI {
    @Override
    public void draw(String graphicsName, int radius, int x, int y) {
        System.out.printf("用绿色笔画%s，radius:%d, x:%d, y:%d%n", graphicsName, radius, x, y);
    }
}

/**
 * 蓝色
 */
class BluePen implements DrawAPI {
    @Override
    public void draw(String graphicsName, int radius, int x, int y) {
        System.out.printf("用蓝色笔画图%s，radius:%d, x:%d, y:%d%n", graphicsName, radius, x, y);
    }
}

/**
 * 定义一个抽象类，此类的实现类都需要使用 DrawAPI：
 */
abstract class Shape {
    protected DrawAPI drawAPI;

    /**
     * 定义画笔
     *
     * @param drawAPI
     */
    protected Shape(DrawAPI drawAPI) {
        this.drawAPI = drawAPI;
    }

    /**
     * 画图
     */
    public abstract void draw();
}

/**
 * 画一个圆
 */
class Circle extends Shape {
    private final int radius;

    public Circle(int radius, DrawAPI drawAPI) {
        super(drawAPI);
        this.radius = radius;
    }

    @Override
    public void draw() {
        drawAPI.draw("圆", radius, 0, 0);
    }
}

/**
 * 画一个长方形
 */
class Rectangle extends Shape {
    private final int x;
    private final int y;

    public Rectangle(int x, int y, DrawAPI drawAPI) {
        super(drawAPI);
        this.x = x;
        this.y = y;
    }

    @Override
    public void draw() {
        drawAPI.draw("长方形", 0, x, y);
    }
}

class Bridge {

    public static void main(String[] args) {
        Shape greenCircle = new Circle(10, new GreenPen());
        Shape redRectangle = new Rectangle(4, 8, new RedPen());

        greenCircle.draw();
        redRectangle.draw();
    }
}
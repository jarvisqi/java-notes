package com.basic.datastructure.sample;

import java.io.*;

/**
 * 实现对象克隆
 *
 * @author Jarvis
 * @date 2018/7/25
 */
public class CloneUtil {

    /**
     * 实现Cloneable接口并重写Object类中的clone()方法；
     * 实现Serializable接口，通过对象的序列化和反序列化实现克隆，可以实现真正的深度克隆。
     *
     * @param obj
     * @param <T>
     * @return
     * @throws IOException
     * @throws ClassNotFoundException
     */
    public static <T> T clone(T obj) throws IOException, ClassNotFoundException {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        ObjectOutputStream oos = new ObjectOutputStream(baos);
        oos.writeObject(obj);

        ByteArrayInputStream bins = new ByteArrayInputStream(baos.toByteArray());
        ObjectInputStream ois = new ObjectInputStream(bins);
        return (T) ois.readObject();
    }
}

/**
 * 人类
 *
 * @author 骆昊
 */
class Person implements Serializable {
    private static final long serialVersionUID = -9102017020286042305L;

    private String name;
    private int age;
    private Car car;

    public Person(String name, int age, Car car) {
        this.name = name;
        this.age = age;
        this.car = car;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public Car getCar() {
        return car;
    }

    public void setCar(Car car) {
        this.car = car;
    }

    @Override
    public String toString() {
        return "Person [name=" + name + ", age=" + age + ", car=" + car + "]";
    }
}

/**
 * 小汽车类
 *
 * @author 骆昊
 */
class Car implements Serializable {
    private static final long serialVersionUID = -5713945027627603702L;

    private String brand;
    private int maxSpeed;

    public Car(String brand, int maxSpeed) {
        this.brand = brand;
        this.maxSpeed = maxSpeed;
    }

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public int getMaxSpeed() {
        return maxSpeed;
    }

    public void setMaxSpeed(int maxSpeed) {
        this.maxSpeed = maxSpeed;
    }

    @Override
    public String toString() {
        return "Car [brand=" + brand + ", maxSpeed=" + maxSpeed + "]";
    }
}

class CloneTest {

    public static void main(String[] args) {
        try {
            Person p1 = new Person("Hao LUO", 33, new Car("Benz", 300));
            // 深度克隆
            Person p2 = CloneUtil.clone(p1);
            p2.getCar().setBrand("BYD");
            // 修改克隆的Person对象p2关联的汽车对象的品牌属性
            // 原来的Person对象p1关联的汽车不会受到任何影响
            // 因为在克隆Person对象时其关联的汽车对象也被克隆了
            System.out.println(p1);
            System.out.println(p2);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

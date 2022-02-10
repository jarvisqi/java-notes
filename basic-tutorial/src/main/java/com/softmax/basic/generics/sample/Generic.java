package com.softmax.basic.generics.sample;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;

/**
 * 泛型类型
 *
 * @author : Jarvis
 * @date : Created in 2018/5/25
 */

class BoxClass {
    //    使用泛型
    Box<String> strBox = new Box();
    Box<Integer> intBox = new Box();
    Box<Double> douBox = new Box();

    Parm<Integer, String> p1 = new Parm<>(1, "apple");
    Parm<Integer, String> p2 = new Parm<>(2, "pear");
    boolean same = Util.compare(p1, p2);


    //<? extends T>的用法
    List<? super Apple> list1 = new ArrayList<Apple>();
    List<? super Apple> list2 = new ArrayList<Fruit>();
    List<? super Apple> list3 = new ArrayList<Object>();


    private static <E> void append(List<E> list, Class<E> cls) throws NoSuchMethodException,
            IllegalAccessException, InvocationTargetException, InstantiationException {
        E lem = cls.getDeclaredConstructor().newInstance();
        list.add(lem);

    }

}

class Box<T> {
    private T t;

    public void set(T t) {
        this.t = t;
    }

    public T get() {
        return t;
    }
}

class Util {
    /**
     * 泛型方法
     *
     * @param p1
     * @param p2
     * @param <K>
     * @param <V>
     * @return
     */
    public static <K, V> boolean compare(Parm<K, V> p1, Parm<K, V> p2) {
        return p1.getKey().equals(p2.getKey()) &&
                p1.getValue().equals(p2.getValue());
    }
}

class Parm<K, V> {
    private K key;
    private V value;

    public Parm(K key, V value) {
        this.key = key;
        this.value = value;
    }

    public void setKey(K key) {
        this.key = key;
    }

    public void setValue(V value) {
        this.value = value;
    }

    public K getKey() {
        return key;
    }

    public V getValue() {
        return value;
    }
}

/**
 * <? extends T>的用法
 */

class GenericWriting {
    static List<Apple> apples = new ArrayList<Apple>();
    static List<Fruit> fruit = new ArrayList<Fruit>();

    static <T> void writeExact(List<T> list, T item) {
        list.add(item);
    }

    static void f1() {
        writeExact(apples, new Apple());
        writeExact(fruit, new Fruit());
    }

    static <T> void writeWithWildcard(List<? super T> list, T item) {
        list.add(item);
    }

    static void f2() {
        writeWithWildcard(apples, new Apple());
        writeWithWildcard(fruit, new Fruit());
    }

    public static void main(String[] args) {
        f1();
        f2();
    }

}

class Apple extends Fruit {
}

class Fruit {
}
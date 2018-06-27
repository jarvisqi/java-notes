package com.javabase.Instrumendemo;

import java.util.*;

/**
 * 继承
 *
 * @author ： Jarvis
 * @date : 2016/08/12
 */
public class InstrumentHashSet<E> extends HashSet<E> {
    private int count;

    @Override
    public boolean add(E e) {
        count++;
        return super.add(e);
    }

    @Override
    public boolean addAll(Collection<? extends E> c) {
        int size = c.size();
        count += size;
        return super.addAll(c);
    }

    public int getCount() {
        return count;
    }
//
//    public static void main(String[] args) {
//        InstrumentHashSet<String> ins = new InstrumentHashSet<>();
//        ins.addAll(Arrays.asList("xinhua1", "xinhua2", "xinhua3"));
//        System.out.println("hashSet中的count为" + ins.getCount());
//        for (String item : ins) {
//            System.out.println(item);
//        }
//    }


}

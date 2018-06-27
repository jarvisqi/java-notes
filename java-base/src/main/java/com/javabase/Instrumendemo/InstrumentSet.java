package com.javabase.Instrumendemo;

import java.util.*;

/**
 * @author : Jarvis
 * @data : 2018/6/12
 */
public class InstrumentSet<T> extends ForwardingSet<T> {
    private int count;

    public InstrumentSet(Set<T> sets) {
        super(sets);
    }

    @Override
    public boolean add(T t) {
        count++;
        return super.add(t);
    }

    @Override
    public boolean addAll(Collection<? extends T> c) {
        int size = c.size();
        count += size;
        return super.addAll(c);
    }

    public int getCount() {
        return count;
    }


    public static void main(String[] args) {
        InstrumentSet<String> ins = new InstrumentSet<String>(new HashSet<String>());
        ins.addAll(Arrays.asList("xinhua1", "xinhua2", "xinhua3"));
        System.out.println("hashSet中的count为" + ins.getCount());
        for (String item : ins) {
            System.out.println(item);
        }
    }



}
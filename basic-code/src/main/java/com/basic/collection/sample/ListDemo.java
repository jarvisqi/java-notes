package com.basic.collection.sample;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author Jarvis
 * @date 2018/9/13
 */
public class ListDemo {

    public static void main(String[] args) {
        List<Integer> listA = new ArrayList<>();
        for (int i = 1; i <= 6; i++) {
            listA.add(i);
        }

        List<Integer> listB = new ArrayList<>();
        listB.add(2);
        listB.add(3);
        listB.add(7);
        listB.add(8);

        System.out.println(listA);
        System.out.println(listB);


        //交集
        List<Integer> intersection = listA.stream().filter(a -> listB.contains(a)).collect(Collectors.toList());
        System.out.println("----交集");
        System.out.println(intersection);

        //差集
        List<Integer> reduce1 = listA.stream().filter(a -> !listB.contains(a)).collect(Collectors.toList());
        System.out.println("----交集 listA - listB");
        System.out.println(reduce1);

        //并集
        listA.addAll(listB);
        System.out.println("---并集 listAll---");
        System.out.println(listA);

        //并集去重
        List<Integer> listAllDistinct = listA.stream().distinct().collect(Collectors.toList());

        System.out.println("---并集去重---");
        System.out.println(listAllDistinct);
    }

}

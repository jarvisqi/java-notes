package com.basecode.mapcode;


import cn.hutool.core.util.RandomUtil;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

/**
 * @author Jarvis
 * @date 2018/7/23
 */
public class HasMapDemo {

    public static void main(String[] args) {
       
        forIterator();
    }

    private static void forIterator() {
        //给定的默认容量为 16，负载因子为 0.75。Map 在使用过程中不断的往里面存放数据，
        //当数量达到了 16 * 0.75 = 12 就需要将当前 16 的容量进行扩容，而扩容这个过程涉及到 rehash、复制数据等操作，所以非常消耗性能。
        HashMap<Integer, String> map = new HashMap<>(100, 1);
        for (int i = 1; i <= 100; i++) {
            // 随机字符串
            var randStr = RandomUtil.randomString(5);
            map.put(i, randStr);
        }

        System.out.println("HasMap 大小：" + map.size());
        System.out.println("开始遍历：\n===========================================");

        Iterator<Map.Entry<Integer, String>> entryIterator = map.entrySet().iterator();
        while (entryIterator.hasNext()) {
            Map.Entry<Integer, String> item = entryIterator.next();
            System.out.println("key:" + item.getKey() + "-value:" + item.getValue());
        }


    }
}

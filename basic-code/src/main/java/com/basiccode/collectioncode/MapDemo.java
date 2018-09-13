package com.basiccode.collectioncode;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

/**
 * @author Jarvis
 * @date 2018/9/13
 */
public class MapDemo {

    public static void main(String[] args) {

        Map<String, String> map = new HashMap<>(3);
        map.put("1", "a");
        map.put("2", "b");
        map.put("3", "c");

        //第一种遍历：普遍使用，二次取值
        System.out.println("通过Map.keySet遍历key和value：");
        for (String key : map.keySet()) {
            System.out.println("key: " + key + "  value: " + map.get(key));
        }
        System.out.println();

        //第二中，Iterator 取值
        System.out.println("通过Map.entrySet使用iterator遍历key和value：");
        Iterator<Map.Entry<String, String>> iteratorMap = map.entrySet().iterator();
        while (iteratorMap.hasNext()) {
            Map.Entry<String, String> item = iteratorMap.next();
            System.out.println("key: " + item.getKey() + "  value: " + item.getValue());
        }
        System.out.println();

        //第三种：推荐，尤其是容量大时
        System.out.println("通过Map.entrySet遍历key和value");
        for (Map.Entry<String, String> entry : map.entrySet()) {
            System.out.println("key: " + entry.getKey() + "  value: " + entry.getValue());
        }
        System.out.println();

        //第四种：
        System.out.println("通过Map.values()遍历所有的value，但不能遍历key");
        for (String v : map.values()) {
            System.out.println("value: " + v);
        }

    }


}

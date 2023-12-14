package com.softmax.features;

public class InstanceofMatching {

    /**
     * 旧写法
     * @param obj 未知对象
     */
    static void testOld(Object obj) {
        if (obj instanceof String) {
            String s = (String) obj;
            if ("模式匹配".equals(s)) {
                System.err.println(s);
            }
        }
    }


    /**
     * 新写法
     * @param obj 未知对象
     */
    static void testNew(Object obj) {
        if (obj instanceof String s && "模式匹配".equals(s)) {
            System.err.println(s);
        }
    }

    public static void main(String[] args) {
        testOld("模式匹配");

        testNew("模式匹配");
    }

}

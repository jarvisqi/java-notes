package com.basecode.datastructure;

import java.time.LocalDateTime;

/**
 * @author Jarvis
 * @date 2018/8/17
 */
public class StringCode {

    public static void main(String[] args) {
        var originStr = "ABCD";
        var newStr = reverse(originStr);
        System.out.println(newStr);

        changeLen(1, 2, 3, 4, 5, 6);

        Integer a = Integer.valueOf(300);
        Integer b = Integer.valueOf(300);
        //true
        System.out.println(a.equals(b));
        //false
        System.out.println(a == b);
    }

    /**
     * 字符串反转
     *
     * @param originStr
     */
    private static String reverse(String originStr) {
        if (originStr == null || originStr.length() <= 1) {
            return originStr;
        }
        return reverse(originStr.substring(1)) + originStr.charAt(0);
    }

    /**
     * 字符串反转
     *
     * @param originStr
     */
    private static String reverse1(String originStr) {
        if (originStr == null || originStr.length() <= 1) {
            return originStr;
        }
        return new StringBuilder(originStr).reverse().toString();
    }


    /**
     * 可变参数
     *
     * @param first
     * @param list
     */
    private static void changeLen(int first, int... list) {
        System.out.println(first);
        for (int item : list) {
            System.out.println(item);
        }
    }


    private static void stringTest() {
        String str0 = "123";
        String str1 = "123";
        //对比值相等
        System.out.println(str0 == str1);

        String str2 = new String("234");
        String str3 = new String("234");
        //Java虚拟机的解释器每遇到一个new关键字，都会在堆内存中开辟一块内存来存放一个String对象,不同的引用 false
        System.out.println(str2 == str3);
        //对比值相等
        System.out.println(str2.equals(str3));

        //昨天的当前时刻
        LocalDateTime today = LocalDateTime.now();
        var yesterday = today.minusDays(1);
        System.out.println(yesterday);


    }

}

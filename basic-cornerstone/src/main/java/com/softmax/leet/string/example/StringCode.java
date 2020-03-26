package com.softmax.leet.string.example;

import java.util.Arrays;
import java.util.Comparator;

/**
 * @author Jarvis
 * @date 2018/7/25
 */
public class StringCode {
    public static void main(String[] args) {

        String s = "hello";
        reverseString(s);
    }

    /**
     * 反转字符串
     *
     * @param str
     * @return
     */
    public static String reverseString(String str) {
        if (str == null || str.length() == 0) {
            return str;
        }

        String result = new StringBuilder(str).reverse().toString();

        //第二种方法
//        for (int i = str.length() - 1; i >= 0; i--) {
//            result += str.charAt(i);
//        }

        System.out.println(result);
        return result;

    }
}

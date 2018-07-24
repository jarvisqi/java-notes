package com.javabase.datastructure.interview;

import java.util.*;

/**
 * @author Jarvis
 * @date 2018/7/16
 */
public class PrintMinNumber {

    public static void main(String[] args) {

//        int[] arrs = {3, 32, 321};
//        int[] arrs = {1, 8, 1};
//        String result = getResult(arrs);
//
//        System.out.println(result);

//        int[] nubmers = {0, 6, 3, 4, 2, 1, 6, 3, 11, 33, 223, 12, 232, 223, 33, 10};
//        findDuplicate(nubmers);

//        String sentence = "student. a am I ";
//        reverseSentence(sentence);

        String s = "abcXYZdef";
        leftRotateString(s, 3);
    }

    /**
     * 输入一个正整数数组，把数组里所有数字拼接起来排成一个数，打印能拼接出的所有数字中最小的一个。
     * 例如输入数组{3，32，321}，则打印出这三个数字能排成的最小数字为321323。
     *
     * @param numbers
     * @return
     */
    public static String getResult(int[] numbers) {
        StringBuilder result = new StringBuilder();
        // 先判断小于1
        int len = numbers.length;
        if (len < 1) {
            return result.toString();
        }
        // 定义数组
        ArrayList<Integer> list = new ArrayList<>();
        for (int i = 0; i < numbers.length; i++) {
            list.add(numbers[i]);
        }
        //排序数字
        list.sort((o1, o2) -> {
            // 两两比较
            System.out.println(o1 + "-" + o2);

            String r1 = o1 + "" + o2;
            String r2 = o2 + "" + o1;
            // 直接比较大小 r1 > r2返回1 否则 -1
            return r1.compareTo(r2);
        });
        // 遍历输出
//        Iterator<Integer> iterator = list.iterator();
//        while (iterator.hasNext()) {
//            result.append(iterator.next());
//        }

        for (Integer item : list) {
            result.append(item);
        }

        return result.toString();
    }

    /**
     * 在一个长度为n的数组里的所有数字都在0到n-1的范围内。 数组中某些数字是重复的，但不知道有几个数字是重复的。也不知道每个数字重复几次。
     * 请找出数组中任意一个重复的数字。 例如，如果输入长度为7的数组{2,3,1,0,2,5,3}，那么对应的输出是重复的数字2或者3。
     *
     * @param numbers
     * @return
     */
    public static void findDuplicate(int[] numbers) {
        //没有重复
        if (numbers.length < 1) {
            return;
        }
        ArrayList<Integer> arrayList = new ArrayList();
        for (Integer item : numbers) {
            arrayList.add(item);
        }
        // 排序
        arrayList.sort(new Comparator<Integer>() {
            @Override
            public int compare(Integer o1, Integer o2) {
                return o1.compareTo(o2);
            }
        });
        var len = arrayList.size();
        for (Integer i = 0; i < len; i++) {
            if ((i + 1) < len) {
                if (arrayList.get(i).equals(arrayList.get(i + 1))) {
                    //输出重复的数字
                    System.out.println(arrayList.get(i));
                }
            }
        }
    }

    /**
     * 反转句子 例如： “student. a am I”。翻转后：“I am a student.”
     *
     * @param sentence
     */
    public static void reverseSentence(String sentence) {
        String strc = sentence.trim();
        if (strc == "") {
            System.out.println(sentence);
            return;
        }
        StringBuilder stringBuilder = new StringBuilder();
        String[] words = sentence.split(" ");
        for (int i = words.length - 1; i >= 0; i--) {
            stringBuilder.append(words[i] + " ");
        }
        System.out.println(stringBuilder.toString());

    }

    /**
     * 汇编语言中有一种移位指令叫做循环左移（ROL），现在有个简单的任务，就是用字符串模拟这个指令的运算结果。
     * 对于一个给定的字符序列S，请你把其循环左移K位后的序列输出。
     * 例如，字符序列S=”abcXYZdef”,要求输出循环左移3位后的结果，即“XYZdefabc”。
     *
     * @param strc
     * @param k
     */
    public static void leftRotateString(String strc, int k) {
        //先判断长度
        if (strc.trim().length() == 0) {
            System.out.println(strc);
        }
        if (strc.trim().length() < 1) {
            System.out.println("");
        }
        if (strc.trim().length() < k) {
            System.out.println(strc);
        }
        System.out.println("原字符串：" + strc);
        var left = strc.substring(0, k);
        var right = strc.substring(k);
        System.out.println("新字符串：" + right + left);
    }
}

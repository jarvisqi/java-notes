package com.leetcode.arrayalgorithm;

import java.util.*;

/**
 * @author Jarvis
 * @date 2018/7/24
 */
public class ArraysCode {

    public static void main(String[] args) {
//        int[] nums = {1, 1, 2};
//        removeDuplicates(nums);

//        int[] prices = {7, 1, 5, 3, 6, 4};
//        maxProfit(prices);

        int[] arrs = {1, 2};
        rotate(arrs, 3);
    }

    /**
     * 数组删除重复数，返回新的长度
     *
     * @param nums 数组
     * @return
     */
    private static int removeDuplicates(int[] nums) {
        int len = nums.length;
        System.out.println("原始长度：" + len);
        if (len == 0 || len == 1) {
            return len;
        }

        ArrayList<Integer> list = new ArrayList<>();
        for (int item : nums) {
            if (!list.contains(item)) {
                list.add(item);
            }
        }

        int size = list.size();
        for (int i = 0; i < size; i++) {
            nums[i] = list.get(i);
        }
        System.out.println("新的长度：" + list.size());
        return size;
    }

    /**
     * 最大利润
     *
     * @param prices
     * @return
     */
    public static int maxProfit(int[] prices) {
        if (0 == prices.length || null == prices) {
            return 0;
        }
        int result = 0;
        for (int i = 0; i < prices.length - 1; i++) {
            int profit = prices[i + 1] - prices[i];
            if (profit > 0) {
                result += profit;
            }
        }

        System.out.println(result);
        return result;
    }

    /**
     * 给定一个数组，将数组中的元素向右移动 k 个位置，其中 k 是非负数。
     *
     * @param nums
     * @param k
     */
    public static void rotate(int[] nums, int k) {
        int len = nums.length;
        List<Integer> list = new ArrayList<>();
        for (int i = 0; i < len; i++) {
            list.add(nums[i]);
        }
        System.out.println(list);
        if (len == 0 || len == 1 || k == 0) {
            return;
        }

        for (int i = 0; i < k; i++) {
            //最后一位提到最前面
            int index = list.get(len - 1);
            List<Integer> newList = new ArrayList<>();
            newList.add(index);
            //后面的顺序排列
            for (Integer item : list) {
                if (item != index) {
                    newList.add(item);
                }
            }
            list.clear();
            list = newList;
        }

        for (int i = 0; i < list.size(); i++) {
            nums[i] = list.get(i);
        }

        System.out.println(list);
    }
}


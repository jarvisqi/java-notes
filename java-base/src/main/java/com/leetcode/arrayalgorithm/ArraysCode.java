package com.leetcode.arrayalgorithm;

import java.util.ArrayList;
import java.util.Comparator;

/**
 * @author Jarvis
 * @date 2018/7/24
 */
public class ArraysCode {

    public static void main(String[] args) {
        int[] nums = {1, 1, 2};
//        removeDuplicates(nums);

        int[] prices = {7, 1, 5, 3, 6, 4};
        maxProfit(prices);
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
}


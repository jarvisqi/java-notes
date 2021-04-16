package com.softmax.leet.array.example;

import java.util.*;


/**
 * @author Jarvis
 * @date 2018/7/24
 */
public class ArraySolution {

    public static void main(String[] args) {

        ArraySolution solution = new ArraySolution();

        int[] nums = {3, 2, 2, 3};
//        solution.removeDuplicates(nums);

//        int[] prices = {7, 1, 5, 3, 6, 4};
//        solution.maxProfit(prices);
//
//        int[] arrs = {1, 2, 3, 4, 5, 6, 7};
//        solution.rotate(arrs, 3);

//        solution.removeElement(nums, 2);
//
//        int[] arrs = {2, 7, 11, 15};
//        int[] ints = solution.twoSum(arrs, 9);
//        Arrays.stream(ints).forEach(i -> System.out.println(i));

//        int anInt = solution.reversalInt(32415);
//        System.out.println(anInt);

        solution.PascalTriangle(5);

    }

    /**
     * 数组删除重复数，返回新的长度
     *
     * @param nums 数组
     * @return
     */
    private int removeDuplicates(int[] nums) {
        int len = nums.length;
        System.out.println("原始长度：" + len);
        if (len == 0 || len == 1) {
            return len;
        }

//        ArrayList<Integer> list = new ArrayList<>();
//        for (int item : nums) {
//            if (!list.contains(item)) {
//                list.add(item);
//            }
//        }
//        int size = list.size();
//        for (int i = 0; i < size; i++) {
//            nums[i] = list.get(i);
//        }

        //去重
//        nums = Arrays.stream(nums).distinct().toArray();
//        int size = nums.length;

        int i = 0;
        for (int j = 1; j < nums.length; j++) {
            if (nums[j] != nums[i]) {
                i++;
                nums[i] = nums[j];
            }
        }
        int size = i + 1;


        System.out.println("新的长度：" + size);
        return size;
    }

    /**
     * 最大利润
     *
     * @param prices
     * @return
     */
    public int maxProfit(int[] prices) {
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
    public void rotate(int[] nums, int k) {

        int length = nums.length;
        if (length <= 0) {
            return;
        }
        int[] newArrs = new int[length];
        for (int i = 0; i < length; i++) {
            newArrs[i] = nums[i];
        }
        k = k % length;
        int t;
        for (int i = 0; i < length; i++) {
            t = (i + k) % length;
            nums[t] = newArrs[i];
        }
        /*
         *以上是最佳方案
         */

        //以下方法在本地对，leetcode 上不正确
//        int len = nums.length;
//        if (len <= 0) {
//            return;
//        }
//
//        int arrLen = nums.length;
//        if (arrLen <= 0) {
//            return;
//        }
//        for (int i = 0; i < k; i++) {
//            int[] newArr = new int[arrLen];
//            newArr[0] = nums[arrLen - 1];
//            for (int j = 0; j < arrLen; j++) {
//                if ((j + 1) < arrLen) {
//                    newArr[j + 1] = nums[j];
//                }
//            }
//            nums = newArr;
//        }

//        for (int i = 0; i < list.size(); i++) {
//            nums[i] = list.get(i);
//        }

        List<Integer> list = new ArrayList<>();
        for (int i = 0; i < length; i++) {
            list.add(nums[i]);
        }
        System.out.println(list);
    }

    public int removeElement(int[] nums, int val) {
        int len = 0;
        int j = 0;
        for (int i = 0; i < nums.length; i++) {
            if (nums[i] != val) {
                len++;
                nums[j] = nums[i];
                j++;
            }
        }
        return len;

    }

    public int[] twoSum(int[] nums, int target) {
        int[] indexs = new int[2];

        // 建立k-v ，一一对应的哈希表
        HashMap<Integer, Integer> hash = new HashMap<Integer, Integer>();
        for (int i = 0; i < nums.length; i++) {
            if (hash.containsKey(nums[i])) {
                indexs[0] = i;
                indexs[1] = hash.get(nums[i]);
                return indexs;
            }
            // 将数据存入 key为补数 ，value为下标
            hash.put(target - nums[i], i);
        }
        return indexs;
    }


    /**
     * 反转整数
     *
     * @param x
     * @return
     */
    public int reversalInt(int x) {
        long result = 0;
        while (x != 0) {
            result = result * 10 + x % 10;
            x = x / 10;
        }
        if (result > Integer.MAX_VALUE || result < Integer.MIN_VALUE) {
            return 0;
        }
        return (int) result;
    }

    /**
     * 杨辉三角
     * 给定一个非负整数 numRows，生成杨辉三角的前 numRows 行。
     * 每个数是它左上方和右上方的数的和
     *
     * @param numRows
     */
    public void PascalTriangle(int numRows) {
        if (numRows == 0) {
            throw new IllegalArgumentException("参数错误");
        }

        List<List<Integer>> res = new ArrayList<>();
        for (int i = 0; i < numRows; i++) {
            List<Integer> sub = new ArrayList<>();
            for (int j = 0; j <= i; j++) {
                if (j == 0 || j == i) {
                    sub.add(1);
                } else {
                    //上一行的数据
                    List<Integer> upSub = res.get(i - 1);
                    // 左上方和右上方的数的和
                    sub.add(upSub.get(j - 1) + upSub.get(j));
                }
            }
            res.add(sub);
        }
    }


}


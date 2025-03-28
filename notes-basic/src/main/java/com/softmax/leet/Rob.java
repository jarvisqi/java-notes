package com.softmax.leet;

/**
 * 抢劫一排住户，但是不能抢邻近的住户，求最大抢劫量。
 */
public class Rob {

    public static void main(String[] args) {
        int[] arr1 = {1, 2, 3, 1};
        int[] arr2 = {2, 7, 9, 3, 1};
        System.out.println(solution(arr1));
        System.out.println(solution(arr2));
    }

    /**
     * 实现的是一个动态规划算法，目的是求解在不能取相邻元素的情况下，数组中元素可以组成的最大值。
     *
     * @param arr
     * @return
     */
    private static int solution(int[] arr) {
        if (arr == null || arr.length == 0) return 0;
        if (arr.length == 1) return arr[0];
        int[] dp = new int[arr.length];
        dp[0] = arr[0];
        dp[1] = Math.max(dp[0], dp[1]);
        for (int i = 2; i < arr.length; i++) {
            dp[i] = Math.max(dp[i - 1], dp[i - 2] + arr[i]);
        }
        return dp[arr.length - 1];
    }
}

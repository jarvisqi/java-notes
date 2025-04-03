package com.softmax.leet;

public class SplitArray {

    public static void main(String[] args) {

        SplitArray solution = new SplitArray();
        int[] nums = {1, 2, 3, 4, 5, 6, 7, 8, 9};
        boolean b = solution.canPartition(nums);
        System.out.printf("b = %b", b);

    }

    public boolean canPartition(int[] nums) {
        // 01背包问题：
        // 可选数量为N = nums.length，背包最大容量为：w = sum / 2，
        // 这题和一般01背包的区别是，这里不要求价值最大，而是恰好装满，因此dp数组存放背包的当前体积即可。
        int sum = 0;
        for (int num : nums) { // 计算和
            sum += num;
        }
        if (sum % 2 != 0) {
            return false;//除不尽，说明总和一定不能分成两份
        }
        int len = nums.length;
        int W = sum / 2;
        //dp[i][j]，i是当前背包的件数，j是当前背包的体积
        int[][] dp = new int[len + 1][W + 1];
        //注意题目说只包含正整数，所以j从1开始
        for (int i = 1; i <= len; i++) {
            int w = nums[i - 1];
            for (int j = 1; j <= W; j++) {
                if (j >= w) { //背包容量装得下当前数字
                    dp[i][j] = Math.max(dp[i - 1][j], dp[i - 1][j - w] + w);
                } else {
                    dp[i][j] = dp[i - 1][j];
                }
                if (dp[i][j] == W) {
                    return true;
                }
            }

        }
        return false;
    }
}

package com.softmax.leet;

/**
 * 矩阵的路径数
 */
public class UniquePath {

    public static void main(String[] args) {
        System.out.println(solution(3, 3));
        System.out.println(solution(2, 2));
        System.out.println(solution(1, 1));
    }

    /**
     * 计算从左上角到(row, col)位置的不同路径数量
     *
     * @param row
     * @param col
     * @return
     */
    private static int solution(int row, int col) {
        if (row <= 0 || col <= 0) return 0;
        int[][] dp = new int[row][col];
        for (int i = 0; i < row; i++) {
            for (int j = 0; j < col; j++) {
                if (i == 0 && j == 0) {
                    dp[i][j] = 1;
                } else if (i == 0 && j != 0) {   //如果在第一行，只能从左边到达，因此路径数量与左边相同
                    dp[i][j] = dp[i][j - 1];
                } else if (j == 0 && i != 0) {   //如果在第一列，只能从上边到达，因此路径数量与上边相同
                    dp[i][j] = dp[i - 1][j];
                } else {
                    //对于其他位置，路径数量等于从上方和左方到达的数量之和
                    dp[i][j] = dp[i - 1][j] + dp[i][j - 1];
                }
            }
        }

        return dp[row - 1][col - 1];
    }

}

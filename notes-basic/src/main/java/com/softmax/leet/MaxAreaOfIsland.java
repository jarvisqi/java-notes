package com.softmax.leet;

/**
 * 计算并返回数组中最大的连通的 1 的面积。连通的 1 是指在水平或垂直方向上相邻的 1。
 * dfs 方法是一个深度优先搜索算法，用于遍历并计算连通区域的大小。
 */
public class MaxAreaOfIsland {

    // 查找最大连通面积
    public static void main(String[] args) {

        int[][] arr = {
                {1, 1, 0, 1},
                {1, 0, 1, 0},
                {1, 1, 1, 1},
                {1, 0, 1, 1}
        };

        System.out.println(solution(arr));
    }

    private static int solution(int[][] arr) {
        if (arr == null || arr.length == 0) {
            return 0;
        }

        int maxArea = 0;
        int row = arr.length;
        int col = arr[0].length;

        for (int i = 0; i < row; i++) {
            for (int j = 0; j < col; j++) {
                // 检查当前单元格是否为 1
                if (arr[i][j] == 1) {
                    // 将当前单元格标记为已访问
                    arr[i][j] = 0;
                    // 向四个方向递归搜索，并累加面积
                    int area = 1 + dfs(arr, i - 1, j, row, col)
                            + dfs(arr, i + 1, j, row, col)
                            + dfs(arr, i, j - 1, row, col)
                            + dfs(arr, i, j + 1, row, col);
                    // 更新最大面积
                    maxArea = Math.max(maxArea, area);
                }
            }
        }
        return maxArea;
    }

    private static int dfs(int[][] arr, int i, int j, int row, int col) {
        // 检查边界条件和当前单元格是否为 1
        if (i < 0 || i >= row || j < 0 || j >= col || arr[i][j] != 1) {
            return 0;
        }
        // 将当前单元格标记为已访问
        arr[i][j] = 0;
        // 向四个方向递归搜索，并累加面积
        return 1 + dfs(arr, i - 1, j, row, col)
                + dfs(arr, i + 1, j, row, col)
                + dfs(arr, i, j - 1, row, col)
                + dfs(arr, i, j + 1, row, col);
    }
}

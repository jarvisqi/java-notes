package com.softmax.leet;

import java.util.ArrayDeque;
import java.util.Queue;

/**
 * 基于广度优先搜索（BFS）的算法，用于在一个二维矩阵中找到从起点 (0, 0) 到目标点 (ti, tj) 的最短路径。
 * 矩阵中的 1 表示可以通过的路径，0 表示障碍物，无法通过。
 */
public class MinPathLength {

    public static void main(String[] args) {
        int[][] arr = {{1, 1, 0, 1}, {1, 0, 1, 0}, {1, 1, 1, 1}, {1, 0, 1, 1}};

        System.out.println(solution(arr, 3, 3));
    }


    private static int solution(int[][] arr, int ti, int tj) {
        //  数组表示四个可能的移动方向：下、上、右、左。
        int[][] direction = {{1, 0}, {-1, 0}, {0, 1}, {0, -1}};

        int row = arr.length;
        int col = arr[0].length;
        int len = 0;
        boolean[][] visited = new boolean[row][col];
        Queue<int[]> queue = new ArrayDeque<>();
        queue.offer(new int[]{0, 0});
        while (!queue.isEmpty()) {
            int size = queue.size(); //注意queue每次存放的是下一次可以遍历的相同距离的点
            len++; //queue不为空可以++
            // 每进入一层，路径长度加 1
            while (size-- > 0) {
                int[] temp = queue.poll();
                int i = temp[0], j = temp[1];
                visited[i][j] = true;
                for (int[] d : direction) {
                    int ii = i + d[0];
                    int jj = j + d[1];
                    if (ii < 0 || ii >= row || jj < 0 || jj >= col || arr[ii][jj] == 0 || visited[ii][jj]) {
                        continue;
                    }
                    if (ii == ti && jj == tj) {
                        return len;
                    }
                    queue.offer(new int[]{ii, jj});
                }
            }
        }
        return -1;
    }
}

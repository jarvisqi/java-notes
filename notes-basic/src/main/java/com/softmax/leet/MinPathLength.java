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

        // 外层 while 循环：当队列不为空时，继续搜索。
        //size：记录当前层的节点数量。每次处理完一层后，路径长度 len 加 1。
        while (!queue.isEmpty()) {
            int size = queue.size(); //注意queue每次存放的是下一次可以遍历的相同距离的点
            len++; //queue不为空可以++
            // 每进入一层，路径长度加 1
            while (size-- > 0) {
                //内层 while 循环：处理当前层的所有节点。
                //从队列中取出一个节点 (i, j)，并标记为已访问。
                //遍历四个方向，计算相邻节点的坐标 (ii, jj)。
                //如果相邻节点越界、是障碍物或已经访问过，则跳过。
                //如果相邻节点是目标点 (ti, tj)，则返回当前路径长度 len。
                //否则，将相邻节点加入队列，等待下一层处理。
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

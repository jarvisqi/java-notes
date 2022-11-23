package com.softmax.leet;

import cn.hutool.core.lang.Pair;
import org.checkerframework.checker.units.qual.min;

import java.util.LinkedList;
import java.util.Queue;

/**
 * 计算在网格中从原点到特定点的最短路径长度
 * [[1,1,0,1],
 * [1,0,1,0],
 * [1,1,1,1],
 * [1,0,1,1]]
 * 1 表示可以经过某个位置，求解从 (0, 0) 位置到 (tr, tc) 位置的最短路径长度。
 */
public class ShortestPath {

    /**
     * 队列：用来存储每一轮遍历得到的节点；
     * 标记：对于遍历过的节点，应该将它标记，防止重复遍历。
     *
     * @param grids
     * @param tr    target row
     * @param tc    target column
     */
    public static int shortestPath(int[][] grids, int tr, int tc) {
        if (grids[0][0] == 0 || grids[tr][tc] == 0) {
            return -1;
        }
        // 四个方向
        int[][] directions = {{1, 0}, {-1, 0}, {0, 1}, {0, -1}};

        //队列中存放每次访问到的点的坐标
        Queue<int[]> queue = new LinkedList<>();
        //(0,0)位置
        queue.offer(new int[]{0, 0});
        //标记
        grids[0][0] = 0;
        int num = 0;
        while (!queue.isEmpty()) {
            num++;
            //队列中是否有元素
            int size = queue.size();
            for (int i = 0; i < size; i++) {
                int[] prev = queue.poll();
                if (tr == prev[0] && tc == prev[1]) {
                    return num - 1;
                }
                int prevX = prev[0];
                int prevY = prev[1];
                for (int[] direction : directions) {
                    int newX = prevX + direction[0];
                    int newY = prevY + direction[1];
                    if (newX >= 0 && newX < grids.length && newY >= 0 && newY < grids[0].length) {
                        queue.offer(new int[]{newX, newY});
                        //标记为已经走过
                        grids[prevX][prevY] = 0;
                    }
                }
            }
        }

        return -1;
    }

    public static int shortestPathBinaryMatrix(int[][] grid) {
        if (grid == null || grid.length == 0 || grid[0].length == 0) {
            return -1;
        }
        // 如果起点就阻塞那就玩完啦
        if (grid[0][0] == 1) {
            return -1;
        }
        //定义 8个方向
        int[][] dir = {{1, -1}, {1, 0}, {1, 1}, {0, -1}, {0, 1}, {-1, -1}, {-1, 0}, {-1, 1}};
        int m = grid.length;
        int n = grid[0].length;
        //bfs的老套路 来个队列
        Queue<int[]> queue = new LinkedList<>();
        queue.add(new int[]{0, 0});   //把起点扔进去
        grid[0][0] = 1;        // 把起点标记为阻塞
        int path = 1;     // 层数

        while (!queue.isEmpty()) {
            int size = queue.size();
            while (size-- > 0) {
                int[] cur = queue.poll();
                int x = cur[0];
                int y = cur[1];

                //能放进队列里的都是为0可以走的（这一点在后面保证了）
                // 如果等于终点则返回
                if (x == m - 1 && y == n - 1) {    //
                    return path;
                }

                //开始八个方向的判断
                for (int[] d : dir) {
                    int x1 = x + d[0];
                    int y1 = y + d[1];
                    //这里开始过滤
                    if (x1 < 0 || x1 >= m || y1 < 0 || y1 >= n || grid[x1][y1] == 1) {
                        continue;
                    }
                    //把在数组范围内 并且为0不阻塞的放入队列中
                    queue.add(new int[]{x1, y1});
                    grid[x1][y1] = 1; // 标记
                }
            }
            path++;  //遍历完一层 这时候要 ++啦
        }
        return -1;
    }


    public static void main(String[] args) {
         /*
        grids: 测试用例
         */
        int[][] grids = {
                {1, 1, 0, 1},
                {1, 0, 1, 0},
                {1, 1, 1, 1},
                {1, 0, 1, 1}
        };
        int tr = 4;
        int tc = 4;
        //最短路径长度
        int length = shortestPath(grids, tr, tc);
        System.out.println(length);

        int shortPath = shortestPathBinaryMatrix(grids);
        System.out.println(shortPath);
    }
}

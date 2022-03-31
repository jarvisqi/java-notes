package com.softmax.leet;

/**
 * 杨辉三角
 */
public class PascalTriangle {


    /**
     * 根据用户传入值来打印正三角的方法
     *
     * @param n
     */
    public static void printForwardTriangle(int n) {
        //第一层1个，第二层三个，第三层5个...类比退出第n层就是last个*
        int last = 2 * (n - 1) + 1;
        //控制打印多少层
        for (int i = 0; i < n; i++) {
            //计算出每一层左边要填充空格的个数
            int fillingLeft = last / 2 - i;
            //打印完一层后需要换行
            System.out.println("");
            //控制本层要打印的样式，默认打印出正方形
            for (int j = 0; j <= last; j++) {
                //如果j比要填充的空格数量少或者相等或j大于填充的*所占用的位置数与空格填充的位置数之和，就打印空格
                if (j <= fillingLeft || j > fillingLeft + 2 * i + 1) {
                    System.out.print(" ");
                } else {
                    System.out.print("*");
                }
            }

        }
    }

    /**
     * 根据用户传入值来打印倒三角的方法
     *
     * @param n
     */
    public static void printInvertedTriangle(int n) {
        //最上面一层
        int last = 2 * n - 1;
        //控制打印多少层
        for (int i = n - 1; i >= 0; i--) {
            //计算出每一层左边要填充空格的个数
            int fillingLeft = n - i - 1;
            System.out.println("");
            for (int j = 0; j < last; j++) {
                if (j < fillingLeft || j >= fillingLeft + 2 * i + 1) {
                    System.out.print(" ");
                } else {
                    System.out.print("*");
                }
            }
        }
    }

    /**
     * 打印9*9乘法表
     */
    public static void printMultiplicationTable() {
        for (int i = 1; i <= 9; i++) {
            for (int j = 1; j < i + 1; j++) {
                System.out.print(j + "*" + i + "=" + i * j + "\t");
            }
            System.out.println();
        }

    }

    /**
     * 打印三角形数字
     */
    public static void printForwardTriangleOfNum(int n) {
        //创建二维数组
        int[][] triangle = new int[n][];
        //遍历二维数组的第一层
        for (int i = 0; i < triangle.length; i++) {
            //初始化第二层数组的大小
            triangle[i] = new int[i];
            //遍历第二层数组
            for (int j = 0; j < triangle[i].length; j++) {
                //两侧的数组元素赋值为1
                if (i == 0 || j == 0 || j == triangle[i].length - 1) {
                    //两侧的数组元素赋值为1
                    triangle[i][j] = 1;
                } else {
                    //其他数值通过公式计算
                    triangle[i][j] = triangle[i - 1][j] + triangle[i - 1][j - 1];
                }
                //输出数组元素
                System.out.print(triangle[i][j] + "\t");
            }
            System.out.println();
        }

    }

    /**
     * 打印三角形数字
     */
    public static void printInvertedTriangleOfNum() {
        int rows = 10;
        for (int i = 0; i < rows; i++) {
            int number = 1;
            // 打印空格字符串
            System.out.format("%" + (rows - i) * 2 + "s", "");
            for (int j = 0; j <= i; j++) {
                System.out.format("%4d", number);
                number = number * (i - j) / (j + 1);
            }
            System.out.println();
        }
    }

    public static void main(String[] args) {
        printForwardTriangle(5);
        System.out.println("\n ================================================");
        printInvertedTriangle(5);
        System.out.println("\n ================================================");
        printMultiplicationTable();
        System.out.println("\n ================================================");
        printForwardTriangleOfNum(8);
        System.out.println("\n ================================================");
        printInvertedTriangleOfNum();
    }
}

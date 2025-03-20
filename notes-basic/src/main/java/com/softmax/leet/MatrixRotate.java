package com.softmax.leet;

/**
 * 转置矩阵
 */
public class MatrixRotate {


    public static void main(String[] args) {

        int[][] matrix = {
                {1,  2,  3,  4},
                {5,  6,  7,  8},
                {9, 10, 11, 12},
                {13, 14, 15, 16}
        };

        printMatrix(matrix); // 打印原始矩阵

        rotate(matrix); // 旋转矩阵

        printMatrix(matrix); // 打印旋转后的矩阵
    }

    public static void rotate(int[][] matrix) {
        // 先转置，然后镜像对称
        for (int i = 0; i < matrix.length; i++) {
            // 注意 j < i， 因为求的是转置
            for (int j = 0; j < i; j++) {
                // 转置
                int temp = matrix[j][i];
                matrix[j][i] = matrix[i][j];
                matrix[i][j] = temp;
            }
        }
        for (int i = 0; i < matrix.length; i++) {
            // 镜像对称
            for (int j = 0; j < matrix[0].length / 2; j++) {
                int temp = matrix[i][j];
                matrix[i][j] = matrix[i][matrix[0].length - j - 1];
                matrix[i][matrix[0].length - j - 1] = temp;
            }
        }
    }

    public static void printMatrix(int[][] matrix) {
        for (int[] row : matrix) {
            for (int value : row) {
                System.out.print(value + " ");
            }
            System.out.println();
        }
        System.out.println();
    }

}

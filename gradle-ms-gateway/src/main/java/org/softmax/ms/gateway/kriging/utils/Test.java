package org.softmax.ms.gateway.kriging.utils;

public class Test {

    public static void main(String[] args) {

        double[] data = {30.01, 19.282248757940586, 19.282248757940586, 15.285506060425494};

        MathsUtil.kriging_matrix_chol2inv(data, 2);

        double[] data2 = {30.01, 19.282248757940586, 19.282248757940586, 15.285506060425494};
        kriging_matrix_chol2invOpt(data2, 2);
        System.out.println("finished");

        double[] data3 = {30.01, 19.282248757940586, 19.282248757940586, 15.285506060425494};
        MathsUtil.kriging_matrix_chol(data3, 2);


        double[] data4 = {30.01, 19.282248757940586, 19.282248757940586, 15.285506060425494};
        kriging_matrix_cholOpt(data4, 2);
        System.out.println("finished");

    }


    public static void kriging_matrix_chol2invOpt(final double[] matrix, final int size) {
        for (int i = 0; i < size; i++) {
            matrix[i * size + i] = 1 / matrix[i * size + i];
            for (int j = i + 1; j < size; j++) {
                double sum = 0;
                for (int k = i; k < j; k++) {
                    sum -= matrix[j * size + k] * matrix[k * size + i];
                }
                matrix[j * size + i] = sum / matrix[j * size + j];
            }
        }
        for (int i = 0; i < size; i++) {
            for (int j = i + 1; j < size; j++) {
                matrix[i * size + j] = 0;
            }
        }
        for (int i = 0; i < size; i++) {
            matrix[i * size + i] *= matrix[i * size + i];
            for (int k = i + 1; k < size; k++) {
                matrix[i * size + i] += matrix[k * size + i] * matrix[k * size + i];
            }
            for (int j = i + 1; j < size; j++) {
                for (int k = j; k < size; k++) {
                    matrix[i * size + j] += matrix[k * size + i] * matrix[k * size + j];
                }
            }
        }
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < i; j++) {
                matrix[i * size + j] = matrix[j * size + i];
            }
        }
    }


    public static boolean kriging_matrix_cholOpt(final double[] matrix, final int n) {
        final double[] diagonal = new double[n];
        for (int i = 0; i < n; i++) {
            diagonal[i] = matrix[i * n + i];
        }
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < i; j++) {
                double sum = matrix[i * n + j];
                for (int k = 0; k < j; k++) {
                    sum -= matrix[i * n + k] * matrix[j * n + k];
                }
                matrix[i * n + j] = sum / diagonal[j];
            }
            double sum = matrix[i * n + i];
            for (int k = 0; k < i; k++) {
                sum -= matrix[i * n + k] * matrix[i * n + k] * diagonal[k];
            }
            diagonal[i] = Math.sqrt(sum);
            if (diagonal[i] <= 0) {
                return false;
            }
            for (int j = i + 1; j < n; j++) {
                double sum2 = matrix[j * n + i];
                for (int k = 0; k < i; k++) {
                    sum2 -= matrix[j * n + k] * matrix[i * n + k] * diagonal[k];
                }
                matrix[j * n + i] = sum2 / diagonal[i];
            }
        }
        for (int i = 0; i < n; i++) {
            matrix[i * n + i] = diagonal[i];
        }
        return true;
    }

}

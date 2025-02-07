package org.softmax.ms.gateway.kriging.algorithm;

import java.util.concurrent.RecursiveTask;

public class KrigingMatrixSolverTask extends RecursiveTask<double[]> {
    /**
     * 阈值
     */
    private static final int THRESHOLD = 4;

    private final double[] arr;
    private final int inputSize;

    public KrigingMatrixSolverTask(double[] arr, int inputSize) {
        this.arr = arr;
        this.inputSize = inputSize;
    }

    @Override
    protected double[] compute() {
        if (arr.length <= THRESHOLD) {
            return matrix_solve(arr, inputSize);
        } else {
            int mid = arr.length / 2;
            double[] left = new double[mid];
            double[] right = new double[arr.length - mid];
            System.arraycopy(arr, 0, left, 0, mid);
            System.arraycopy(arr, mid, right, 0, arr.length - mid);
            KrigingMatrixSolverTask leftTask = new KrigingMatrixSolverTask(left, inputSize);
            KrigingMatrixSolverTask rightTask = new KrigingMatrixSolverTask(right, inputSize);
            invokeAll(leftTask, rightTask);

            return merge(leftTask.join(), rightTask.join());
        }
    }

    private double[] merge(double[] left, double[] right) {
        double[] result = new double[left.length + right.length];
        int i = 0, j = 0, k = 0;
        while (i < left.length && j < right.length) {
            if (left[i] < right[j]) {
                result[k++] = left[i++];
            } else {
                result[k++] = right[j++];
            }
        }
        while (i < left.length) {
            result[k++] = left[i++];
        }
        while (j < right.length) {
            result[k++] = right[j++];
        }
        return result;
    }

    private double[] matrix_solve(double[] arr, int inputSize) {
        double[] data = arr.clone();
        int m = inputSize;
        double[] b = new double[inputSize * inputSize];
        int[] indxc = new int[inputSize];
        int[] indxr = new int[inputSize];
        double[] ipiv = new double[inputSize];
        int i = 0, icol = 0, irow = 0, j = 0, k = 0, l = 0, ll = 0;
        double big, dum, pivinv, temp;
        for (i = 0; i < inputSize; i++) {
            for (j = 0; j < inputSize; j++) {
                if (i == j) {
                    b[i * inputSize + j] = 1;
                } else {
                    b[i * inputSize + j] = 0;
                }
            }
        }
        for (j = 0; j < inputSize; j++) {
            ipiv[j] = 0;
        }

        for (i = 0; i < inputSize; i++) {
            big = 0;
            for (j = 0; j < inputSize; j++) {
                if (ipiv[j] != 1) {
                    for (k = 0; k < inputSize; k++) {
                        if (ipiv[k] == 0) {
                            if (Math.abs(data[j * inputSize + k]) >= big) {
                                big = Math.abs(data[j * inputSize + k]);
                                irow = j;
                                icol = k;
                            }
                        }
                    }
                }
            }
            ++(ipiv[icol]);

            if (irow != icol) {
                for (l = 0; l < inputSize; l++) {
                    temp = data[irow * inputSize + l];
                    data[irow * inputSize + l] = data[icol * inputSize + l];
                    data[icol * inputSize + l] = temp;
                }
                for (l = 0; l < m; l++) {
                    temp = b[irow * inputSize + l];
                    b[irow * inputSize + l] = b[icol * inputSize + l];
                    b[icol * inputSize + l] = temp;
                }
            }
            indxr[i] = irow;
            indxc[i] = icol;
            // Singular
            if (data[icol * inputSize + icol] == 0) {
                return data;
            }
            pivinv = 1 / data[icol * inputSize + icol];
            data[icol * inputSize + icol] = 1;
            for (l = 0; l < inputSize; l++) {
                data[icol * inputSize + l] *= pivinv;
            }
            for (l = 0; l < m; l++) {
                b[icol * inputSize + l] *= pivinv;
            }

            for (ll = 0; ll < inputSize; ll++) {
                if (ll != icol) {
                    dum = data[ll * inputSize + icol];
                    data[ll * inputSize + icol] = 0;
                    for (l = 0; l < inputSize; l++) {
                        data[ll * inputSize + l] -= data[icol * inputSize + l] * dum;
                    }
                    for (l = 0; l < m; l++) {
                        b[ll * inputSize + l] -= b[icol * inputSize + l] * dum;
                    }
                }
            }
        }
        for (l = (inputSize - 1); l >= 0; l--) {
            if (indxr[l] != indxc[l]) {
                for (k = 0; k < inputSize; k++) {
                    temp = data[k * inputSize + indxr[l]];
                    data[k * inputSize + indxr[l]] = data[k * inputSize + indxc[l]];
                    data[k * inputSize + indxc[l]] = temp;
                }
            }
        }
        return data;
    }


}
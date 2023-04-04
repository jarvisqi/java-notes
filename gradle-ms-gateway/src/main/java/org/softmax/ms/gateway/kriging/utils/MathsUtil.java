package org.softmax.ms.gateway.kriging.utils;

import org.apache.commons.math3.linear.*;

import java.util.Arrays;

/**
 * MathsUtil
 *
 * @author xuwei
 */
public class MathsUtil {

    public static double[] kriging_matrix_diag(final double c, final int n) {
        int i = 0;
        final double[] Z = new double[n * n];
        for (i = 0; i < n; i++) {
            Z[i * n + i] = c;
        }
        return Z;
    }

    public static double[] kriging_matrix_diagOpt(final double c, final int n) {
        final double[] Z = new double[n * n];
        Arrays.parallelSetAll(Z, i -> i % (n + 1) == 0 ? c : 0);
        return Z;
    }

    public static double[] kriging_matrix_transpose(final double[] X, final int n, final int m) {
        int i, j;
        final double[] Z = new double[m * n];
        for (i = 0; i < n; i++) {
            for (j = 0; j < m; j++) {
                Z[j * n + i] = X[i * m + j];
            }
        }
        return Z;
    }

    public static void kriging_matrix_scale(double[] X, double c, int n, int m) {
        int i = 0, j = 0;
        for (i = 0; i < n; i++) {
            for (j = 0; j < m; j++) {
                X[i * m + j] *= c;
            }
        }
    }

    public static double[] kriging_matrix_add(final double[] X, final double[] Y, final int n, final int m) {
        int i, j;
        final double[] Z = new double[n * m];
        for (i = 0; i < n; i++)
            for (j = 0; j < m; j++)
                Z[i * m + j] = X[i * m + j] + Y[i * m + j];
        return Z;
    }

    // Naive matrix multiplication
    public static double[] kriging_matrix_multiply(final double[] X, final double[] Y, final int n, final int m, final int p) {
        int i, j, k;
        final double[] Z = new double[n * p];
        for (i = 0; i < n; i++) {
            for (j = 0; j < p; j++) {
                Z[i * p + j] = 0;
                for (k = 0; k < m; k++) {
                    Z[i * p + j] += X[i * m + k] * Y[k * p + j];
                }
            }
        }
        return Z;
    }

    public static double[] kriging_matrix_multiplyOpt(final double[] X, final double[] Y, final int n, final int m, final int p) {
        int i, j, k;
        double[][] X_matrix = new double[n][m];
        double[][] Y_matrix = new double[m][p];
        for (i = 0; i < n; i++) {
            for (j = 0; j < m; j++) {
                X_matrix[i][j] = X[i * m + j];
            }
        }
        for (i = 0; i < m; i++) {
            for (j = 0; j < p; j++) {
                Y_matrix[i][j] = Y[i * p + j];
            }
        }

        Array2DRowRealMatrix xarrMatrix = new Array2DRowRealMatrix(X_matrix);
        Array2DRowRealMatrix yarrMatrix = new Array2DRowRealMatrix(Y_matrix);
        Array2DRowRealMatrix zarrMatrix = xarrMatrix.multiply(yarrMatrix);
        double[][] data = zarrMatrix.getData();
        double[] doubles = Arrays.stream(data).flatMapToDouble(Arrays::stream).toArray();

        // Then, we can use matrix multiplication to calculate Z
        double[][] Z_matrix = new double[n][p];
        for (i = 0; i < n; i++) {
            for (j = 0; j < p; j++) {
                for (k = 0; k < m; k++) {
                    Z_matrix[i][j] += X_matrix[i][k] * Y_matrix[k][j];
                }
            }
        }
        // Finally, we need to reshape Z_matrix into a 1D array
        double[] Z = new double[n * p];
        for (i = 0; i < n; i++) {
            for (j = 0; j < p; j++) {
                Z[i * p + j] = Z_matrix[i][j];
            }
        }
        return Z;
    }

    public static double[] matrix_multiplyOpt(final double[] X, final double[] Y, final int n, final int m, final int p) {
        int i, j, k;
        double[][] X_matrix = new double[n][m];
        double[][] Y_matrix = new double[m][p];
        for (i = 0; i < n; i++) {
            for (j = 0; j < m; j++) {
                X_matrix[i][j] = X[i * m + j];
            }
        }
        for (i = 0; i < m; i++) {
            for (j = 0; j < p; j++) {
                Y_matrix[i][j] = Y[i * p + j];
            }
        }
        Array2DRowRealMatrix xarrMatrix = new Array2DRowRealMatrix(X_matrix);
        Array2DRowRealMatrix yarrMatrix = new Array2DRowRealMatrix(Y_matrix);
        Array2DRowRealMatrix zarrMatrix = xarrMatrix.multiply(yarrMatrix);
        double[][] data = zarrMatrix.getData();
        double[] doubles = Arrays.stream(data).flatMapToDouble(Arrays::stream).toArray();
        return doubles;
    }


    public static boolean kriging_matrix_cholOpt(final double[] X, final int n) {
        int i, j, k;
        final double[] p = new double[n];
        for (i = 0; i < n; i++) {
            p[i] = X[i * n + i];
        }
        for (i = 0; i < n; i++) {
            double sum = 0;
            for (j = 0; j < i; j++) {
                sum += X[i * n + j] * X[i * n + j];
            }
            p[i] -= sum;
            if (p[i] <= 0) {
                return false;
            }
            p[i] = Math.sqrt(p[i]);
            for (j = i + 1; j < n; j++) {
                double sum2 = 0;
                for (k = 0; k < i; k++) {
                    sum2 += X[j * n + k] * X[i * n + k];
                }
                X[j * n + i] = (X[j * n + i] - sum2) / p[i];
            }
        }
        for (i = 0; i < n; i++) {
            X[i * n + i] = p[i];
        }
        return true;
    }


    // Cholesky decomposition
    public static boolean kriging_matrix_chol(final double[] X, final int n) {
        int i, j, k;
        final double[] p = new double[n];
        for (i = 0; i < n; i++) {
            p[i] = X[i * n + i];
        }
        for (i = 0; i < n; i++) {
            for (j = 0; j < i; j++) {
                p[i] -= X[i * n + j] * X[i * n + j];
            }
            if (p[i] <= 0) {
                return false;
            }
            p[i] = Math.sqrt(p[i]);
            for (j = i + 1; j < n; j++) {
                for (k = 0; k < i; k++) {
                    X[j * n + i] -= X[j * n + k] * X[i * n + k];
                }
                X[j * n + i] /= p[i];
            }
        }
        for (i = 0; i < n; i++) {
            X[i * n + i] = p[i];
        }
        return true;
    }


    // Inversion of cholesky decomposition
    public static void kriging_matrix_chol2inv(final double[] X, final int n) {
        int i, j, k;
        double sum;
        for (i = 0; i < n; i++) {
            double inv = 1 / X[i * n + i];
            X[i * n + i] = inv;
            for (j = i + 1; j < n; j++) {
                sum = 0;
                for (k = i; k < j; k++) {
                    sum -= X[j * n + k] * X[k * n + i];
                }
                X[j * n + i] = sum / X[j * n + j];
            }
        }

        for (i = 0; i < n; i++) {
            for (j = i + 1; j < n; j++) {
                X[i * n + j] = 0;
            }
        }

        for (i = 0; i < n; i++) {
            X[i * n + i] *= X[i * n + i];
            for (k = i + 1; k < n; k++) {
                X[i * n + i] += X[k * n + i] * X[k * n + i];
            }
            for (j = i + 1; j < n; j++) {
                for (k = j; k < n; k++) {
                    X[i * n + j] += X[k * n + i] * X[k * n + j];
                }
            }
        }
        for (i = 0; i < n; i++) {
            for (j = 0; j < i; j++) {
                X[i * n + j] = X[j * n + i];
            }
        }
    }

    public static double[] matrix_chol2inv(final double[] data, final int inputSize) {
        double[][] coefficients = new double[inputSize][inputSize];
        for (int i = 0; i < inputSize; i++) {
            for (int j = 0; j < inputSize; j++) {
                coefficients[i][j] = data[i * inputSize + j];
            }
        }
        RealMatrix matrix = MatrixUtils.createRealMatrix(coefficients);
        LUDecomposition cholesky = new LUDecomposition(matrix);
        RealMatrix inverse = cholesky.getSolver().getInverse();
        RealMatrix pseudoInverse = new SingularValueDecomposition(matrix).getSolver().getInverse();
        double[][] result = inverse.getData();
        return Arrays.stream(result).flatMapToDouble(Arrays::stream).toArray();
    }

    // Inversion via gauss-jordan elimination
    public static boolean kriging_matrix_solve(double[] X, int n) {
        int m = n;
        double[] b = new double[n * n];
        int[] indxc = new int[n];
        int[] indxr = new int[n];
        double[] ipiv = new double[n];
        int i = 0, icol = 0, irow = 0, j = 0, k = 0, l = 0, ll = 0;
        double big, dum, pivinv, temp;

        for (i = 0; i < n; i++) {
            for (j = 0; j < n; j++) {
                if (i == j) {
                    b[i * n + j] = 1;
                } else {
                    b[i * n + j] = 0;
                }
            }
        }
        for (j = 0; j < n; j++) {
            ipiv[j] = 0;
        }
        for (i = 0; i < n; i++) {
            big = 0;
            for (j = 0; j < n; j++) {
                if (ipiv[j] != 1) {
                    for (k = 0; k < n; k++) {
                        if (ipiv[k] == 0) {
                            if (Math.abs(X[j * n + k]) >= big) {
                                big = Math.abs(X[j * n + k]);
                                irow = j;
                                icol = k;
                            }
                        }
                    }
                }
            }
            ++(ipiv[icol]);

            if (irow != icol) {
                for (l = 0; l < n; l++) {
                    temp = X[irow * n + l];
                    X[irow * n + l] = X[icol * n + l];
                    X[icol * n + l] = temp;
                }
                for (l = 0; l < m; l++) {
                    temp = b[irow * n + l];
                    b[irow * n + l] = b[icol * n + l];
                    b[icol * n + l] = temp;
                }
            }
            indxr[i] = irow;
            indxc[i] = icol;

            if (X[icol * n + icol] == 0) return false; // Singular

            pivinv = 1 / X[icol * n + icol];
            X[icol * n + icol] = 1;
            for (l = 0; l < n; l++) X[icol * n + l] *= pivinv;
            for (l = 0; l < m; l++) b[icol * n + l] *= pivinv;

            for (ll = 0; ll < n; ll++) {
                if (ll != icol) {
                    dum = X[ll * n + icol];
                    X[ll * n + icol] = 0;
                    for (l = 0; l < n; l++) X[ll * n + l] -= X[icol * n + l] * dum;
                    for (l = 0; l < m; l++) b[ll * n + l] -= b[icol * n + l] * dum;
                }
            }
        }
        for (l = (n - 1); l >= 0; l--) {
            if (indxr[l] != indxc[l]) {
                for (k = 0; k < n; k++) {
                    temp = X[k * n + indxr[l]];
                    X[k * n + indxr[l]] = X[k * n + indxc[l]];
                    X[k * n + indxc[l]] = temp;
                }
            }
        }
        return true;
    }


    public static double[] kriging_matrix_solveTask(double[] X, int n) {
        int m = n;
        double[] b = new double[n * n];
        int[] indxc = new int[n];
        int[] indxr = new int[n];
        double[] ipiv = new double[n];
        int i = 0, icol = 0, irow = 0, j = 0, k = 0, l = 0, ll = 0;
        double big, dum, pivinv, temp;

        for (i = 0; i < n; i++) {
            for (j = 0; j < n; j++) {
                if (i == j) {
                    b[i * n + j] = 1;
                } else {
                    b[i * n + j] = 0;
                }
            }
        }
        for (j = 0; j < n; j++) {
            ipiv[j] = 0;
        }
        for (i = 0; i < n; i++) {
            big = 0;
            for (j = 0; j < n; j++) {
                if (ipiv[j] != 1) {
                    for (k = 0; k < n; k++) {
                        if (ipiv[k] == 0) {
                            if (Math.abs(X[j * n + k]) >= big) {
                                big = Math.abs(X[j * n + k]);
                                irow = j;
                                icol = k;
                            }
                        }
                    }
                }
            }
            ++(ipiv[icol]);

            if (irow != icol) {
                for (l = 0; l < n; l++) {
                    temp = X[irow * n + l];
                    X[irow * n + l] = X[icol * n + l];
                    X[icol * n + l] = temp;
                }
                for (l = 0; l < m; l++) {
                    temp = b[irow * n + l];
                    b[irow * n + l] = b[icol * n + l];
                    b[icol * n + l] = temp;
                }
            }
            indxr[i] = irow;
            indxc[i] = icol;

            if (X[icol * n + icol] == 0) {
                return X; // Singular
            }

            pivinv = 1 / X[icol * n + icol];
            X[icol * n + icol] = 1;
            for (l = 0; l < n; l++) {
                X[icol * n + l] *= pivinv;
            }
            for (l = 0; l < m; l++) {
                b[icol * n + l] *= pivinv;
            }

            for (ll = 0; ll < n; ll++) {
                if (ll != icol) {
                    dum = X[ll * n + icol];
                    X[ll * n + icol] = 0;
                    for (l = 0; l < n; l++) {
                        X[ll * n + l] -= X[icol * n + l] * dum;
                    }
                    for (l = 0; l < m; l++) {
                        b[ll * n + l] -= b[icol * n + l] * dum;
                    }
                }
            }
        }
        for (l = (n - 1); l >= 0; l--) {
            if (indxr[l] != indxc[l]) {
                for (k = 0; k < n; k++) {
                    temp = X[k * n + indxr[l]];
                    X[k * n + indxr[l]] = X[k * n + indxc[l]];
                    X[k * n + indxc[l]] = temp;
                }
            }
        }
        return X;
    }
}
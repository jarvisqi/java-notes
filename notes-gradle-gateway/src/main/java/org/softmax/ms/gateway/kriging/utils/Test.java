package org.softmax.ms.gateway.kriging.utils;

import java.time.Duration;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.concurrent.*;

public class Test {

    public static void main(String[] args) throws ExecutionException, InterruptedException {

        double[][] trinaData = getData();
        int size = trinaData.length;
        double[] targetValues = new double[size];

        double[] xList = new double[size];
        double[] yList = new double[size];
        for (int i = 0; i < size; i++) {
            xList[i] = trinaData[i][0];
            yList[i] = trinaData[i][1];
            targetValues[i] = trinaData[i][2];
        }
        double[] targetCopy = targetValues.clone();
        double[] xCopy = xList.clone();
        double[] yCopy = yList.clone();

        double[] x = {1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 0.05655792587349262, 0.18843457211200035, 0.23239480520494585, 0.29210738827004123, 0.33402025399827645, 0.39174286557321253, 0.4354989924060204, 0.48127154134097916, 0.5291976795515924, 0.572975649465243, 0.6086574542122617, 0.650604188111871, 0.6971696775974319, 0.7272619841637786, 0.756568142513871, 0.7951674521801572, 0.837942949142864, 0.8560668154869782, 0.8895783273400342, 0.909760095443869,
                0.9251901820042505, 0.9472702583396522, 0.9648604762216701, 0.9797705112886838, 0.9975951186095573, 0.9978386020296411};
        double[] y = {1.0, 0.05655792587349262, 1.0, 0.18843457211200035, 1.0, 0.23239480520494585, 1.0, 0.29210738827004123, 1.0, 0.33402025399827645, 1.0, 0.39174286557321253, 1.0, 0.4354989924060204, 1.0, 0.48127154134097916, 1.0, 0.5291976795515924, 1.0, 0.572975649465243, 1.0, 0.6086574542122617, 1.0, 0.650604188111871, 1.0, 0.6971696775974319, 1.0, 0.7272619841637786, 1.0, 0.756568142513871, 1.0, 0.7951674521801572, 1.0, 0.837942949142864, 1.0, 0.8560668154869782, 1.0, 0.8895783273400342, 1.0, 0.909760095443869, 1.0, 0.9251901820042505, 1.0, 0.9472702583396522,
                1.0, 0.9648604762216701, 1.0, 0.9797705112886838, 1.0, 0.9975951186095573, 1.0, 0.9978386020296411};

//        int len = 10000;
//        double[] data = new double[len];
//        for (int i = 0; i < len; i++) {
//            data[i] = i;
//        }
//
//        double[] target = new double[len];
//        Instant now = Instant.now();
//        for (int i = 0; i < len; i++) {
//            System.arraycopy(data, 0, target, 0, len);
//        }
//        System.out.println(Duration.between(now, Instant.now()).toMillis());
//        System.out.println("len:" + target.length);
//
//        Instant now1 = Instant.now();
//        for (int i = 0; i < len; i++) {
//            double[] clone = data.clone();
//        }
//        System.out.println(Duration.between(now1, Instant.now()).toMillis());

        Instant now = Instant.now();
        double[] doubles = MathsUtil.kriging_matrix_diag((1 / 100), 10000);
        System.out.println(Duration.between(now, Instant.now()).toMillis());

        Instant now1 = Instant.now();
        double[] doublesOpt = MathsUtil.kriging_matrix_diagOpt((1 / 100), 10000);
        System.out.println(Duration.between(now1, Instant.now()).toMillis());

        boolean equals = Arrays.equals(doubles, doublesOpt);
        System.out.println(equals);

//        Instant now = Instant.now();
//        for (int i = 0; i < 1000000; i++) {
//            double[] doublesOpt = MathsUtil.kriging_matrix_multiplyOpt(x, y, 2, 26, 2);
//        }
//        System.out.println(Duration.between(now, Instant.now()).toMillis());
//
//        Instant now1 = Instant.now();
//        for (int i = 0; i < 1000000; i++) {
//            double[] doubles = MathsUtil.kriging_matrix_multiply(x, y, 2, 26, 2);
//        }
//        System.out.println(Duration.between(now1, Instant.now()).toMillis());

//        int inputSize = 2;
//        double[] X = {5.1000000000000005, 17.055503908482375, 3.3442164526436025, 1.414710971502525};
//        double[] cloneArr = targetValues.clone();
//        double[][] coefficients = new double[inputSize][inputSize];
//        for (int i = 0; i < inputSize; i++) {
//            for (int j = 0; j < inputSize; j++) {
//                coefficients[i][j] = X[i * inputSize + j];
//            }
//        }
//        Kriging kriging = new Kriging("Spherical", 0, 100);
//        kriging.train(targetValues, xList, yList);


        System.out.println("=============");
    }

    private void funOpt() throws ExecutionException, InterruptedException {
        double[] X = {5.1000000000000005, 17.055503908482375, 3.3442164526436025, 1.414710971502525};
        int n = 2;
        int numThreads = Runtime.getRuntime().availableProcessors();
        ExecutorService executor = Executors.newFixedThreadPool(numThreads);
        int chunkSize = n / numThreads;
        ArrayList<Future<double[]>> resultList = new ArrayList<>();
        for (int i = 0; i < numThreads; i++) {
            int start = i * chunkSize;
            int end = (i == numThreads - 1) ? X.length : (i + 1) * chunkSize;
            Future future = executor.submit(new MatrixMultiplicationTask(X, n, start, end));
            resultList.add(future);
        }
        executor.shutdown();
        //遍历任务的结果
        for (Future<double[]> fs : resultList) {
            try {
                //打印各个线程（任务）执行的结果
                System.out.println(fs.get());
            } catch (InterruptedException e) {
                e.printStackTrace();
            } catch (ExecutionException e) {
                e.printStackTrace();
            } finally {
                //启动一次顺序关闭，执行以前提交的任务，但不接受新任务。如果已经关闭，则调用没有其他作用。
                executor.shutdown();
            }
        }
        try {
            executor.awaitTermination(Long.MAX_VALUE, TimeUnit.NANOSECONDS);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    class MatrixMultiplicationTask implements Callable<double[]> {
        private final double[] X;
        private final int n;
        private final int start;
        private final int end;

        public MatrixMultiplicationTask(double[] X, int n, int start, int end) {
            this.X = X;
            this.n = n;
            this.start = start;
            this.end = end;
        }

        @Override
        public double[] call() {
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
            return X;
        }
    }

    private static double[][] getData() {
        double[][] trainData = new double[15][3];
        trainData[0][0] = Double.parseDouble("114.5383");  //经度
        trainData[0][1] = Double.parseDouble("32.2075");  //维度
        trainData[0][2] = Double.parseDouble("0.56");     //颜色 //值

        trainData[1][0] = Double.parseDouble("112.9625");
        trainData[1][1] = Double.parseDouble("33.0347");
        trainData[1][2] = Double.parseDouble("0.21");

        trainData[2][0] = Double.parseDouble("116.1508");
        trainData[2][1] = Double.parseDouble("34.2831");
        trainData[2][2] = Double.parseDouble("0.74");

        trainData[3][0] = Double.parseDouble("115.3156");
        trainData[3][1] = Double.parseDouble("34.4867");
        trainData[3][2] = Double.parseDouble("0.09");

        trainData[4][0] = Double.parseDouble("115.4933");
        trainData[4][1] = Double.parseDouble("33.8817");
        trainData[4][2] = Double.parseDouble("0.16");

        trainData[5][0] = Double.parseDouble("115.2414");
        trainData[5][1] = Double.parseDouble("36.0581");
        trainData[5][2] = Double.parseDouble("0.28");

        trainData[6][0] = Double.parseDouble("114.9753");
        trainData[6][1] = Double.parseDouble("32.7369");
        trainData[6][2] = Double.parseDouble("0.53");

        trainData[7][0] = Double.parseDouble("115.8619");
        trainData[7][1] = Double.parseDouble("35.9842");
        trainData[7][2] = Double.parseDouble("0.37");

        trainData[8][0] = Double.parseDouble("114.6317");
        trainData[8][1] = Double.parseDouble("33.6117");
        trainData[8][2] = Double.parseDouble("0.71");

        trainData[9][0] = Double.parseDouble("112.3717");
        trainData[9][1] = Double.parseDouble("32.5825");
        trainData[9][2] = Double.parseDouble("0.23");

        trainData[10][0] = Double.parseDouble("114.4519");
        trainData[10][1] = Double.parseDouble("35.5233");
        trainData[10][2] = Double.parseDouble("0.18");

        trainData[11][0] = Double.parseDouble("115.4236");
        trainData[11][1] = Double.parseDouble("32.4656");
        trainData[11][2] = Double.parseDouble("0.04");

        trainData[12][0] = Double.parseDouble("113.9131");
        trainData[12][1] = Double.parseDouble("32.9403");
        trainData[12][2] = Double.parseDouble("0.85");

        trainData[13][0] = Double.parseDouble("113.0503");
        trainData[13][1] = Double.parseDouble("34.4922");
        trainData[13][2] = Double.parseDouble("0.66");

        trainData[14][0] = Double.parseDouble("114.5825");
        trainData[14][1] = Double.parseDouble("33.7819");
        trainData[14][2] = Double.parseDouble("0.64");

        return trainData;
    }

}


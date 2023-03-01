package com.softmax.basic.ellipse;

import org.apache.commons.math3.stat.regression.OLSMultipleLinearRegression;
import org.ejml.simple.SimpleMatrix;

public class EllipseTest {

    public static void main(String[] args) {

        float[] myX = {0.2f, 0.68f, 1.4f, 2.5f, 2.6f};
        float[] myY = {1.3f, 0.75f, 2.4f, 1.6f, 0.9f};
        float[] myh = {0.3f, 0.35f, 0.4f, 0.63f, 0.95f};

        double[] x = {0.2f, 0.68f, 1.4f, 2.5f, 2.6f};
        double[] y = {1.3f, 0.75f, 2.4f, 1.6f, 0.9f};

        float[][] xd = {{0.15838438f, 0.03979575f, 0.44552622f, 0.01476191f, 0.83672756f, 0.50546285f,
                0.58180001f, 0.14812361f, 0.95032889f, 0.64475014f, 0.33640724f, 0.86694551f,
                0.55559616f, 0.29443716f, 0.65168281f, 0.95903793f, 0.15737295f, 0.98403964f,
                0.48944001f, 0.06221623f},
                {0.01821707f, 0.5986827f, 0.75479841f, 0.21804788f, 0.60061423f, 0.33743815f,
                        0.49785266f, 0.26816113f, 0.45719833f, 0.46750447f, 0.60492337f, 0.68892821f,
                        0.38155246f, 0.63115199f, 0.88337083f, 0.06049841f, 0.19578404f, 0.64777301f,
                        0.18199079f, 0.61106463f}};

//        Instant start = Instant.now();
//        for (int i = 0; i < 100000; i++) {
//            float v = normDiffFun(myX, myY, myh);
//        }
//        Instant finish = Instant.now();
//        long timeElapsed = Duration.between(start, finish).toMillis();
//        System.out.println(timeElapsed);
//
//
//        Instant start1 = Instant.now();
//        for (int i = 0; i < 100000; i++) {
//            float v1 = normDiffFunOpt(myX, myY, myh);
//        }
//        Instant finish1 = Instant.now();
//        long timeElapsed1 = Duration.between(start1, finish1).toMillis();
//        System.out.println(timeElapsed1);
//



    }


    private static float CalLamta(float[] myX, float[] myY, float K) {
        int num = myX.length;
        float num2 = XuzsMath.sum(myX) / (float) myX.length;
        float num3 = XuzsMath.sum(myY) / (float) myY.length;
        float num4 = 0f;
        for (int i = 0; i < num; i++) {
            float num5 = 1f / (float) Math.sqrt(K * K + 1f) * Math.abs(K * myX[i] + (num3 - K * num2) - myY[i]);
            if (num5 > num4) {
                num4 = num5;
            }
        }
        float num6 = num4;
        K = ((K != 0f) ? (-1f / K) : 10000f);
        num4 = 0f;
        for (int j = 0; j < num; j++) {
            float num5 = 1f / (float) Math.sqrt(K * K + 1f) * Math.abs(K * myX[j] + (num3 - K * num2) - myY[j]);
            if (num5 > num4) {
                num4 = num5;
            }
        }
        float num7 = num4;
        return num7 / num6;
    }

    private static float CalLamtaOpt(float[] myX, float[] myY, float K) {
        int num = myX.length;
        float num2 = XuzsMath.sum(myX) / (float) myX.length;
        float num3 = XuzsMath.sum(myY) / (float) myY.length;
        float num4 = 0f;
        for (int i = 0; i < num; i++) {
            float num5 = 1f / (float) Math.sqrt(K * K + 1f) * Math.abs(K * myX[i] + (num3 - K * num2) - myY[i]);
            if (num5 > num4) {
                num4 = num5;
            }
        }
        float num6 = num4;
        K = ((K != 0f) ? (-1f / K) : 10000f);
        num4 = 0f;
        for (int j = 0; j < num; j++) {
            float num5 = 1f / (float) Math.sqrt(K * K + 1f) * Math.abs(K * myX[j] + (num3 - K * num2) - myY[j]);
            if (num5 > num4) {
                num4 = num5;
            }
        }
        float num7 = num4;

        return num7 / num6;
    }

    public double[] train(double[][] features, double[] targets) {

        OLSMultipleLinearRegression regression = new OLSMultipleLinearRegression();
        //计算
        regression.newSampleData(targets, features);

        return regression.estimateRegressionParameters();
    }


    public static float normDiffFun(float[] smpData, float[] ctrlData, float[] h) {
        int num = smpData.length - 1;
        float num2 = 0f;
        float num3 = 1f;

        for (int i = 0; i < num + 1; i++) {
            float num4 = (smpData[i] - ctrlData[i]) / ((float) Math.sqrt(2.0) * h[i]);
            num2 += num4 * num4;
            num3 *= h[i];
        }
        num3 *= (float) Math.pow(Math.PI * 2.0, (num + 1) / 2);
        return 1f / num3 * (float) Math.exp(0f - num2);
    }

    public static float normDiffFunOpt(float[] smpData, float[] ctrlData, float[] h) {
        int num = smpData.length - 1;
        float t3 = 1f;
        SimpleMatrix smpMatrix = new SimpleMatrix(smpData);
        SimpleMatrix ctrlMatrix = new SimpleMatrix(ctrlData);
        SimpleMatrix hMatrix = new SimpleMatrix(h);
        SimpleMatrix matrixC = smpMatrix.minus(ctrlMatrix);
        SimpleMatrix scaleH = hMatrix.scale(Math.sqrt(2.0));
        SimpleMatrix matrixD = matrixC.elementDiv(scaleH);
        float num2 = (float) matrixD.elementPower(2).elementSum();
        for (float v : h) {
            t3 *= v;
        }
        t3 *= (float) Math.pow(Math.PI * 2.0, (num + 1) / 2);
        return 1f / t3 * (float) Math.exp(0f - num2);
    }


    public static double[] getRow(float[][] myA, int iRow) {
        SimpleMatrix smpMatrix = new SimpleMatrix(myA);
        SimpleMatrix rowMatrix = smpMatrix.getRow(iRow);
        double[] doubles = rowMatrix.toArray2()[0];
        return doubles;
    }

    public static float[] getRowOpt(float[][] myA, int iRow) {
        if (iRow < 0 || iRow >= myA.length) {
            return null;
        }
        float[] array = new float[myA[0].length];
        for (int i = 0; i < array.length; i++) {
            array[i] = myA[iRow][i];
        }
        return array;
    }



}

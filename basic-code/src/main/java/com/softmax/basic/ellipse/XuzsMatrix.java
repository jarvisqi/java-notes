package com.softmax.basic.ellipse;

import java.util.ArrayList;
import java.util.List;

public class XuzsMatrix {

    public static float[] getRow(float[][] myA, int iRow) {
        if (iRow < 0 || iRow >= myA.length) {
            return null;
        }
        float[] array = new float[myA[0].length];
        for (int i = 0; i < array.length; i++) {
            array[i] = myA[iRow][i];
        }
        return array;
    }

    public static float[][] getRows(float[][] myA, int Lr, int Ur) {
        if (Lr < 0 || Ur >= myA.length || Lr > Ur) {
            return null;
        }
        float[][] array = new float[Ur - Lr + 1][myA[0].length];
        for (int i = 0; i < Ur - Lr + 1; i++) {
            for (int j = 0; j < myA[0].length; j++) {
                array[i][j] = myA[Lr + i][j];
            }
        }
        return array;
    }

    public static float[] getColumn(float[][] myA, int iColumn) {
        if (iColumn < 0 || iColumn >= myA[0].length) {
            return null;
        }
        float[] array = new float[myA.length];
        for (int i = 0; i < array.length; i++) {
            array[i] = myA[i][iColumn];
        }
        return array;
    }

    public static float[][] getColumns(float[][] myA, int Lc, int Uc) {
        if (Lc < 0 || Uc >= myA[0].length || Lc > Uc) {
            return null;
        }
        float[][] array = new float[myA.length][Uc - Lc + 1];
        for (int i = 0; i < myA.length; i++) {
            for (int j = 0; j < Uc - Lc + 1; j++) {
                array[i][j] = myA[i][j + Lc];
            }
        }
        return array;
    }

    public static float[][] transpose(float[][] myA) {
        int length = myA.length;
        int length2 = myA[0].length;
        float[][] array = new float[length2][length];
        for (int i = 0; i < length; i++) {
            for (int j = 0; j < length2; j++) {
                array[j][i] = myA[i][j];
            }
        }
        return array;
    }

    public static List<float[]> matrixToJaggedArr(float[][] myA) {
        List<float[]> array = new ArrayList<>(myA.length);
        int length = myA[0].length;
        for (int i = 0; i < array.size(); i++) {
            float[] floats = new float[length];
            for (int j = 0; j < length; j++) {
                floats[j] = myA[i][j];
            }
            array.add(floats);
        }
        return array;
    }

    public static float[][] jaggedArrToMatrix(List<float[]> jagArr) {
        try {
            int num = jagArr.size();
            int num2 = jagArr.get(0).length;
            float[][] array = new float[num][num2];
            for (int i = 0; i < num; i++) {
                for (int j = 0; j < num2; j++) {
                    array[i][j] = jagArr.get(i)[j];
                }
            }
            return array;
        } catch (Exception e) {
            System.out.println("JaggedArrToMatrix出现异常" + e);
            return null;
        }
    }
}

package com.softmax.basic.ellipse;

import java.util.Random;

public class XuzsMath {
    public static float sum(float[] myX) {
        float num = 0f;
        for (int i = 0; i < myX.length; i++) {
            num += myX[i];
        }
        return num;
    }

    public static int search(float[] myX, float t) {
        for (int i = 0; i < myX.length; i++) {
            if (myX[i] > t) {
                return i - 1;
            }
        }
        return myX.length - 1;
    }

    public static float searchX(float[] myX, float t) {
        if (t < myX[0]) {
            return -1f;
        }
        int num = myX.length;
        if (t > myX[num - 1]) {
            return num;
        }
        for (int i = 1; i < num; i++) {
            if (myX[i] > t) {
                return (float) i - (myX[i] - t) / (myX[i] - myX[i - 1]);
            }
        }
        return (float) num - 1f;
    }

    public static void sort(float[] aArray) {
        int num = aArray.length;
        for (int i = 0; i < num - 1; i++) {
            for (int j = i + 1; j < num; j++) {
                if (aArray[i] > aArray[j]) {
                    float num2 = aArray[i];
                    aArray[i] = aArray[j];
                    aArray[j] = num2;
                }
            }
        }
    }

    public static float max(float[] arrF) {
        float num = arrF[0];
        for (int i = 1; i < arrF.length; i++) {
            if (arrF[i] > num) {
                num = arrF[i];
            }
        }
        return num;
    }

    public static int max(int[] arrI) {
        int num = arrI[0];
        for (int i = 1; i < arrI.length; i++) {
            if (arrI[i] > num) {
                num = arrI[i];
            }
        }
        return num;
    }

    public static float min(float[] arrF) {
        float num = arrF[0];
        for (int i = 1; i < arrF.length; i++) {
            if (arrF[i] < num) {
                num = arrF[i];
            }
        }
        return num;
    }

    public static XuzsMathMin iMin(float[] arrF) {
        float num = arrF[0];
        int iMin = 0;
        for (int i = 1; i < arrF.length; i++) {
            if (arrF[i] < num) {
                num = arrF[i];
                iMin = i;
            }
        }
        XuzsMathMin xuzsMathMin = new XuzsMathMin();
        xuzsMathMin.setNum(num);
        xuzsMathMin.setiMin(iMin);
        return xuzsMathMin;
    }

    public static float MeanValue(float[] myArray) {
        float num = 0f;
        for (int i = 0; i < myArray.length; i++) {
            num += myArray[i];
        }
        return num / (float) myArray.length;
    }

    public static float NormRandom(float miu, float sigma) {
        Random random = new Random();
        float num3;
        float num5;
        do {
            // 默认生成大于等于0.0且小于1.0的double型随机数，即0<=Math.random()<1.0。
            float num = (float) Math.random();
            float num2 = (float) Math.random();
            num3 = 2f * num - 1f;
            float num4 = 2f * num2 - 1f;
            num5 = num3 * num3 + num4 * num4;
        }
        while (num5 > 1f);
        float num6 = num3 * (float) Math.sqrt(-2.0 * Math.log(num5) / (double) num5);
        return miu + sigma * num6;
    }

    public static float LineRegress(float[] myX, float[] myY) {
        int num = myX.length;
        float[] array = new float[num];
        float[] array2 = new float[num];
        float[] array3 = new float[num];
        float num2 = sum(myX) / (float) num;
        float num3 = sum(myY) / (float) num;
        for (int i = 0; i < num; i++) {
            array[i] = (myX[i] - num2) * (myY[i] - num3);
            array2[i] = (myX[i] - num2) * (myX[i] - num2) - (myY[i] - num3) * (myY[i] - num3);
        }
        float num4 = sum(array);
        float num5 = sum(array2);
        float num6;
        float num7;
        if (num4 == 0f) {
            for (int j = 0; j < num; j++) {
                array3[j] = (float) Math.pow(myY[j] - num3, 2.0);
            }
            num6 = sum(array3);
            for (int k = 0; k < num; k++) {
                array3[k] = (float) Math.pow(myX[k] - num2, 2.0);
            }
            num7 = sum(array3);
            if (num7 < num6) {
                return 100000f;
            }
            return 0f;
        }
        float num8 = (0f - num5 + (float) Math.sqrt(num5 * num5 + 4f * num4 * num4)) / (2f * num4);
        for (int l = 0; l < num; l++) {
            array3[l] = (float) Math.pow(num8 * myX[l] + (num3 - num8 * num2) - myY[l], 2.0);
        }
        num6 = 1f / (num8 * num8 + 1f) * sum(array3);
        float num9 = (0f - num5 - (float) Math.sqrt(num5 * num5 + 4f * num4 * num4)) / (2f * num4);
        for (int m = 0; m < num; m++) {
            array3[m] = (float) Math.pow(num9 * myX[m] + (num3 - num9 * num2) - myY[m], 2.0);
        }
        num7 = 1f / (num9 * num9 + 1f) * sum(array3);
        if (num7 < num6) {
            return num9;
        }
        return num8;
    }
}

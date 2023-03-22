package com.softmax.basic.ellipse;

public class EllipseDiff {
//    public static InfdiffEvaluate evaluate(float[][] myS, List<float[]> myCtrl, float[] myK, float[] myLamta, float[] myInput) {
//        float[] myR;
//        int length = myS.length;
//        int num = myS[0].length - 1;
//        List<float[]> array = new ArrayList<>(num);
//        float[] array2 = new float[num];
//        float[] array3 = NormDiff.normCalculateH(myS);
//        for (int i = 0; i < num; i++) {
//            float[] floats = new float[myCtrl.get(i).length];
//
//            floats = Arrays.copyOf(myCtrl.get(i), myCtrl.get(i).length);
//            array.add(floats);
//            array2[i] = array3[i];
//        }
//        InputLinarInterpFuzzy fuzzy = NormDiff.inputLinarInterpFuzzy(array, myInput);
//        Float[] w = fuzzy.getW();
//        List<Float[]> arrayList = fuzzy.getArrayList();
//        myR = new float[myCtrl.get(num).length];
//        float[] array4 = new float[num + 1];
//        for (int j = 0; j < myCtrl.get(num).length; j++) {
//            myR[j] = 0f;
//            array4[num] = myCtrl.get(num)[j];
//            for (int k = 0; k < length; k++) {
//                for (int l = 0; l < arrayList.size(); l++) {
//                    for (int m = 0; m < num; m++) {
//                        array4[m] = arrayList.get(l)[m];
//                    }
//                    myR[j] += ellipseFun(XuzsMatrix.getRow(myS, k), array4, array3, myK, myLamta) * w[l];
//                }
//            }
//        }
//        float num2 = myR[0];
//        int num3 = 0;
//        for (int n = 0; n < myR.length; n++) {
//            if (myR[n] > num2) {
//                num2 = myR[n];
//                num3 = n;
//            }
//        }
//        if (num2 != 0f) {
//            for (int num4 = 0; num4 < myR.length; num4++) {
//                myR[num4] /= num2;
//            }
//        }
//        InfdiffEvaluate infdiffEvaluate = new InfdiffEvaluate();
//        infdiffEvaluate.setMyR(myR);
//        infdiffEvaluate.setMyCtrl(myCtrl.get(num)[num3]);
//        return infdiffEvaluate;
//    }
//
//    public static InfdiffEvaluate evaluate(float[][] s, List<float[]> ctrl, float[] input) {
//        CalKLamta calKLamta = calKLamta(s, ctrl, calStandInput(s));
//        float[] myLamta = calKLamta.getMyLamta();
//        float[] myK = calKLamta.getArray();
//        return evaluate(s, ctrl, myK, myLamta, input);
//    }
//
//    private static float[] calStandInput(float[][] myS) {
//        int num = myS[0].length - 1;
//        float[] array = new float[num];
//        if (myS.length <= 10) {
//            XuzsMathMin xuzsMathMin = XuzsMath.iMin(XuzsMatrix.getColumn(myS, num));
//            int iMin = xuzsMathMin.getiMin();
//            for (int i = 0; i < num; i++) {
//                array[i] = myS[iMin][i];
//            }
//            return array;
//        }
//        int num2 = 5;
//        if (myS.length > 100) {
//            num2 = Math.round((float) myS.length / 20f);
//        }
//        float[][] array2 = new float[num2][num];
//        float[] column = XuzsMatrix.getColumn(myS, num);
//        float num3 = XuzsMath.max(column);
//        for (int j = 0; j < num2; j++) {
//            int iMin2 = XuzsMath.iMin(column).getIMin();
//            for (int k = 0; k < num; k++) {
//                array2[j][k] = myS[iMin2][k];
//            }
//            column[iMin2] = num3;
//        }
//        for (int l = 0; l < num; l++) {
//            array[l] = XuzsMath.MeanValue(XuzsMatrix.getColumn(array2, l));
//        }
//        return array;
//    }
//
//    private static CalKLamta calKLamta(float[][] myS, List<float[]> myCtrl, float[] standInput) {
//        int length = myS.length;
//        int num = myS[0].length - 1;
//        float[] array = new float[num];
//        float[] myLamta = new float[num];
//        float[] array2 = new float[length];
//        float[] array3 = new float[length];
//        float[] array4 = new float[length];
//        float[] array5 = NormDiff.normCalculateH(myS);
//        for (int i = 0; i < num; i++) {
//            array2 = Arrays.copyOf(standInput, standInput.length);
//            for (int j = 0; j < length; j++) {
//                array2[i] = myS[j][i];
//                array3[j] = myS[j][i] / ((float) Math.sqrt(2.0) * array5[i]);
//                NormDiffEvaluate evaluate = NormDiff.evaluate(myS, myCtrl, array2);
//                array4[j] = evaluate.getMyCtrl() / ((float) Math.sqrt(2.0) * array5[num]);
//            }
//            array[i] = CalK(array3, array4);
//            myLamta[i] = CalLamta(array3, array4, array[i]);
//        }
//        CalKLamta calKLamta = new CalKLamta();
//        calKLamta.setArray(array);
//        calKLamta.setMyLamta(myLamta);
//        return calKLamta;
//    }
//
//    private static float CalLamta(float[] myX, float[] myY, float K) {
//        int num = myX.length;
//        float num2 = XuzsMath.sum(myX) / (float) myX.length;
//        float num3 = XuzsMath.sum(myY) / (float) myY.length;
//        float num4 = 0f;
//        for (int i = 0; i < num; i++) {
//            float num5 = 1f / (float) Math.sqrt(K * K + 1f) * Math.abs(K * myX[i] + (num3 - K * num2) - myY[i]);
//            if (num5 > num4) {
//                num4 = num5;
//            }
//        }
//        float num6 = num4;
//        K = ((K != 0f) ? (-1f / K) : 10000f);
//        num4 = 0f;
//        for (int j = 0; j < num; j++) {
//            float num5 = 1f / (float) Math.sqrt(K * K + 1f) * Math.abs(K * myX[j] + (num3 - K * num2) - myY[j]);
//            if (num5 > num4) {
//                num4 = num5;
//            }
//        }
//        float num7 = num4;
//        return num7 / num6;
//    }
//
//    private static float CalK(float[] myX, float[] myY) {
//        int num = myX.length;
//        float[] array = new float[num];
//        float[] array2 = new float[num];
//        float[] array3 = new float[num];
//        float num2 = XuzsMath.sum(myX) / (float) myX.length;
//        float num3 = XuzsMath.sum(myY) / (float) myY.length;
//        for (int i = 0; i < num; i++) {
//            array[i] = (myX[i] - num2) * (myY[i] - num3);
//            array2[i] = (myX[i] - num2) * (myX[i] - num2) - (myY[i] - num3) * (myY[i] - num3);
//        }
//        float num4 = XuzsMath.sum(array);
//        float num5 = XuzsMath.sum(array2);
//        float num6;
//        float num7;
//        if (num4 == 0f) {
//            for (int j = 0; j < num; j++) {
//                array3[j] = (float) Math.pow(myY[j] - num3, 2.0);
//            }
//            num6 = XuzsMath.sum(array3);
//            for (int k = 0; k < num; k++) {
//                array3[k] = (float) Math.pow(myX[k] - num2, 2.0);
//            }
//            num7 = XuzsMath.sum(array3);
//            if (num7 < num6) {
//                return 10000f;
//            }
//            return 0f;
//        }
//        float num8 = (0f - num5 + (float) Math.sqrt(num5 * num5 + 4f * num4 * num4)) / (2f * num4);
//        for (int l = 0; l < num; l++) {
//            array3[l] = (float) Math.pow(num8 * myX[l] + (num3 - num8 * num2) - myY[l], 2.0);
//        }
//        num6 = 1f / (num8 * num8 + 1f) * XuzsMath.sum(array3);
//        float num9 = (0f - num5 - (float) Math.sqrt(num5 * num5 + 4f * num4 * num4)) / (2f * num4);
//        for (int m = 0; m < num; m++) {
//            array3[m] = (float) Math.pow(num9 * myX[m] + (num3 - num9 * num2) - myY[m], 2.0);
//        }
//        num7 = 1f / (num9 * num9 + 1f) * XuzsMath.sum(array3);
//        if (num7 < num6) {
//            return num9;
//        }
//        return num8;
//    }
//
//
//    public static float ellipseFun(float[] inputData, float[] ctrlData, float[] h, float[] myK, float[] lamta) {
//        int num = inputData.length - 1;
//        float[] array = new float[num];
//        float[] array2 = new float[num];
//        float[] array3 = new float[num + 1];
//        for (int i = 0; i < num; i++) {
//            array[i] = myK[i] / (float) Math.sqrt(myK[i] * myK[i] + 1f);
//            array2[i] = 1f / (float) Math.sqrt(myK[i] * myK[i] + 1f);
//            array3[i] = (inputData[i] - ctrlData[i]) / ((float) Math.sqrt(2.0) * h[i]);
//        }
//        array3[num] = (inputData[num] - ctrlData[num]) / ((float) Math.sqrt(2.0) * h[num]);
//        float num2 = 0f;
//        float num3;
//        float num4;
//        for (int j = 0; j < num; j++) {
//            num3 = 0f;
//            for (int k = j + 1; k < num; k++) {
//                num4 = 1f;
//                for (int l = j + 1; l < k - 1; l++) {
//                    num4 *= array2[l];
//                }
//                num3 += array3[k] * array[k] * array[j] * num4;
//            }
//            num4 = 1f;
//            for (int m = j + 1; m < num; m++) {
//                num4 *= array2[m];
//            }
//            num2 += (float) Math.pow(array3[j] * array2[j] - num3 + array3[num] * array[j] * num4, 2.0) / lamta[j];
//        }
//        num3 = 0f;
//        for (int n = 0; n < num; n++) {
//            num4 = 1f;
//            for (int num5 = 0; num5 < n - 1; num5++) {
//                num4 *= array2[num5];
//            }
//            num3 += array3[n] * array[n] * num4;
//        }
//        num4 = 1f;
//        float num6 = 1f;
//        for (int num7 = 0; num7 < num; num7++) {
//            num4 *= array2[num7];
//            num6 *= h[num7];
//        }
//        float num8 = (float) Math.pow(0f - num3 + array3[num] * num4, 2.0);
//        return 1f / ((float) Math.pow(Math.PI * 2.0, (num + 1) / 2) * h[num] * num6) * (float) Math.exp(0f - num2 - num8);
//    }
}

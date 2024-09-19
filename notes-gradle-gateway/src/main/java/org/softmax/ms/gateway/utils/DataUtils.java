package org.softmax.ms.gateway.utils;

import org.apache.commons.math3.stat.descriptive.moment.StandardDeviation;
import org.springframework.stereotype.Component;

@Component
public class DataUtils {

    /**
     * 标准化数组
     */
    public double[] standardizationArray(double array[]) {
        StandardDeviation deviation = new StandardDeviation();
        double sum = 0;
        for (double i : array) {
            sum += i;
        }
        //均值
        double avg = sum / array.length;
        //标准差
        double evaluate = deviation.evaluate(array);
        //进行标准化
        for (int i = 0; i < array.length; i++) {
            array[i] = (array[i] - avg) / evaluate;
        }
        return array;
    }

    /**
     * 标准化多维数组
     */
    public double[][] standardization2DArray(double arrays[][]) {
        int maxLen = arrays.length;
        int len = arrays[0].length;
        double[][] result = new double[maxLen][len];
        for (int i = 0; i < maxLen; i++) {
            double[] doubles = standardizationArray(arrays[i]);
            result[i] = doubles;
        }
        return result;
    }
}

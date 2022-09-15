package org.softmax.gradle.algorithm;

import org.apache.commons.math3.stat.regression.OLSMultipleLinearRegression;
import org.apache.commons.math3.stat.regression.SimpleRegression;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Jarvis
 */
@Component
public class LinearRegression {

    /**
     * 训练模型
     *
     * @param features 特征值
     * @param targets  标签值
     * @return 模型参数
     */
    public double[] train(double[][] features, double[] targets) {

        OLSMultipleLinearRegression regression = new OLSMultipleLinearRegression();
        //计算
        regression.newSampleData(targets, features);

        return regression.estimateRegressionParameters();
    }

    /**
     * 干热风线性回归方程
     *
     * @param x1
     * @param x2
     * @param x3
     * @return
     */
    public double lineDwh(double x1, double x2, double x3) {
        return -1.44206454 + x1 * 0.06453205 + x2 * -0.01132909 + x3 * 0.02011533;
    }

    /**
     * 霜冻线性回归方程
     *
     * @param x1
     * @param x2
     * @param x3
     * @param x4
     * @return
     */
    public double lineFrosty(double x1, double x2, double x3, double x4) {
        return 0.0676719 + x1 * -0.0212784 + x2 * 0.00024638 + x3 * -0.05241971 + x4 * 0.21565205;
    }

    /**
     * 风灾线性回归方程
     *
     * @param x
     * @return
     */
    public double lineWd(double x) {
        return 0.18603874 + x * 0.02074528;
    }

    /**
     * 干旱线性回归方程
     *
     * @param x
     * @return
     */
    public double lineDrt(double x) {

        return 0.35471673 + x * -0.00535057;
    }

    /**
     * 连阴雨 线性回归方程
     *
     * @param x1
     * @param x2
     * @return
     */
    public double lineWtr(double x1, double x2) {

        return -0.07705596 + x1 * 0.02364479 + x2 * 0.15185185;
    }

    /**
     * 干旱 损失率回归函数
     *
     * @param x
     * @return
     */
    public double droughtFun(double x) {

        return 0.00009046 + x * 0.06021913;
    }

    /**
     * 病虫害 损失率回归函数
     *
     * @param x
     * @return
     */
    public double pestFun(double x) {

        return 0.00011161 + x * 0.06047739;
    }

    /**
     * 暴风 损失率回归函数
     *
     * @param x
     * @return
     */
    public double stormFUn(double x) {

        return 0.00018171 + x * 0.06023479;
    }
}

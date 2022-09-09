package org.softmax.gradle.ml;

public class LogisicRegressionTest {

    public static void main(String[] args) {
        double[][] sourceData = new double[][]{{-1, 1, 0.7}, {0, 1, 0.06}, {1, -1, 1}, {1, 0, -1}, {0, 0.1, 0.8}, {0, -0.1, 1}, {-1, -1.1, 0.6}, {1, 0.9, -1}};
        double[] classValue = new double[]{1, 1, 0, 0, 1, 0, 0, 0};

        LogisticRegression logisticRegression = new LogisticRegression(0.01, 500);
        double[] modle = logisticRegression.train(sourceData, classValue);
        double logicValue0 = logisticRegression.prediction(new double[]{-1, 1}, modle);
        double logicValue1 = logisticRegression.prediction(new double[]{0, 1}, modle);
        double logicValue2 = logisticRegression.prediction(new double[]{1, -1}, modle);
        double logicValue3 = logisticRegression.prediction(new double[]{1, 0}, modle);
        double logicValue4 = logisticRegression.prediction(new double[]{0, 0.1}, modle);
        double logicValue5 = logisticRegression.prediction(new double[]{0, -0.1}, modle);
        System.out.println("---model---");
        for (double v : modle) {
            System.out.println(v);
        }
        System.out.println("-----------");
        System.out.println(logicValue0);
        System.out.println(logicValue1);
        System.out.println(logicValue2);
        System.out.println(logicValue3);
        System.out.println(logicValue4);
        System.out.println(logicValue5);

    }
}

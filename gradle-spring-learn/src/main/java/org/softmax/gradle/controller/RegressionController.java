package org.softmax.gradle.controller;

import org.softmax.gradle.algorithm.LinearRegression;
import org.softmax.gradle.algorithm.LogisticRegression;
import org.softmax.gradle.algorithm.SampleInfo;
import org.softmax.gradle.utils.DataUtils;
import org.softmax.gradle.utils.YamlUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.text.DecimalFormat;
import java.util.Arrays;
import java.util.Map;

/**
 * @author Jarvis
 */
@RestController
@RequestMapping(value = "/regression", name = "RegressionController")
public class RegressionController {

    private final YamlUtils yamlUtils;
    private final DataUtils dataUtils;
    private final LinearRegression linearRegression;

    public RegressionController(YamlUtils yamlUtils, DataUtils dataUtils, LinearRegression linearRegression) {
        this.yamlUtils = yamlUtils;
        this.dataUtils = dataUtils;
        this.linearRegression = linearRegression;
    }

    /**
     * 干热风
     */
    @RequestMapping(value = "/log/dwh")
    public void logisticRegressionApi() {
        SampleInfo sampleInfo = yamlUtils.loadDhwData();
        //训练模型
        LogisticRegression logisticRegression = new LogisticRegression(0.01, 500);
        double[] regressionModel = logisticRegression.train(sampleInfo.features, sampleInfo.targets);
        //使用模型预测
        double logicValue0 = logisticRegression.prediction(new double[]{28.5, 95, 1.8}, regressionModel);
        double logicValue1 = logisticRegression.prediction(new double[]{32.5, 27, 3.8}, regressionModel);
        double logicValue2 = logisticRegression.prediction(new double[]{36.5, 86, 1.8}, regressionModel);
        double logicValue3 = logisticRegression.prediction(new double[]{39.5, 24, 3.1}, regressionModel);
        System.out.println("预测值：" + logicValue0 + "实际值：0");
        System.out.println("预测值：" + logicValue1 + "实际值：1");
        System.out.println("预测值：" + logicValue2 + "实际值：0");
        System.out.println("预测值：" + logicValue3 + "实际值：1");
    }

    /**
     * 干热风回归方程
     */
    @RequestMapping(value = "/line/dwh/fun")
    public String lineDwhFun() {
        SampleInfo sampleInfo = yamlUtils.loadDhwData();
        double[][] features = sampleInfo.features;
        double[] levels = sampleInfo.levels;
        double[] dhws = Arrays.stream(levels).filter(x -> x != 0.0d).toArray();
        // 去掉level为0的样本
        double lineFeatures[][] = new double[dhws.length][];
        double newLevels[] = new double[dhws.length];
        int j = 0;
        for (int i = 0; i < levels.length; i++) {
            if (levels[i] == 0.0d) {
                continue;
            } else {
                lineFeatures[j] = features[i];
                newLevels[j] = levels[i];
            }
            j++;
        }
        double[] train = linearRegression.train(lineFeatures, newLevels);
        StringBuilder funText = new StringBuilder("f(x)=");
        for (int i = 0; i < train.length; i++) {
            DecimalFormat format = new DecimalFormat("#.########");
            if (i == 0) {
                funText.append(format.format(train[0]));
            } else {
                String parameter = String.format("+ x%s*%s ", i, format.format(train[i]));
                funText.append(parameter);
            }
        }
        System.out.println("干热风回归方程：" + funText);
        return funText.toString();
    }

    /**
     * 干热风线性回归预测
     */
    @RequestMapping(value = "/line/dwh/predict")
    public void lineRegressionDwhApi() {
        double[][] data = new double[5][3];
        data[0] = new double[]{37.6, 24, 3.4};
        data[1] = new double[]{36.1, 95, 3.7};
        data[2] = new double[]{40.1, 19, 3.8};
        data[3] = new double[]{42.6, 21, 3.3};
        data[4] = new double[]{35.7, 88, 3.2};
        /**
         *  y >= 0.8 风险为：高
         *  0.8 > y >= 0.5风险为：中
         *  y < 0.5 风险为：低
         */

        for (double[] datum : data) {
            double level = linearRegression.lineDwh(datum[0], datum[1], datum[2]);
            System.out.println(level);
        }
    }

    /**
     * 霜冻逻辑回归
     */
    @RequestMapping(value = "/log/frosty")
    public void logFrostyApi() {
        SampleInfo sampleInfo = yamlUtils.loadFrostyData();
        //训练模型
        LogisticRegression logisticRegression = new LogisticRegression(0.01, 500);

        double[][] features = dataUtils.standardization2DArray(sampleInfo.features);
        double[] targets = dataUtils.standardizationArray(sampleInfo.targets);

        double[] regressionModel = logisticRegression.train(features, targets);
        //使用模型预测
        double logicValue0 = logisticRegression.prediction(new double[]{5, 22, 12, 3}, regressionModel);
        double logicValue1 = logisticRegression.prediction(new double[]{4, 15, -1.5, 2}, regressionModel);
        double logicValue2 = logisticRegression.prediction(new double[]{1, 25, -7.5, 1}, regressionModel);
        System.out.println("预测值：" + logicValue0 + "实际值：0");
        System.out.println("预测值：" + logicValue1 + "实际值：1");
        System.out.println("预测值：" + logicValue2 + "实际值：0");
    }

    /**
     * 霜冻线性回归方程
     */
    @RequestMapping(value = "/line/frosty/fun")
    public String lineFrostyFun() {
        SampleInfo sampleInfo = yamlUtils.loadFrostyData();
        double[][] features = sampleInfo.features;
        double[] levels = sampleInfo.levels;
        //训练模型
        double[] frosty = Arrays.stream(levels).filter(x -> x != 0.0d).toArray();
        // 去掉level为0的样本
        double lineFeatures[][] = new double[frosty.length][];
        double lineLevels[] = new double[frosty.length];
        int j = 0;
        for (int i = 0; i < levels.length; i++) {
            if (levels[i] == 0.0d) {
                continue;
            } else {
                lineFeatures[j] = features[i];
                lineLevels[j] = levels[i];
            }
            j++;
        }
        double[] train = linearRegression.train(lineFeatures, lineLevels);
        StringBuilder funText = new StringBuilder("f(x)=");
        for (int i = 0; i < train.length; i++) {
            DecimalFormat format = new DecimalFormat("#.########");
            if (i == 0) {
                funText.append(format.format(train[0]));
            } else {
                String parameter = String.format("+ x%s*%s ", i, format.format(train[i]));
                funText.append(parameter);
            }
        }
        System.out.println("干热风回归方程：" + funText);
        return funText.toString();
    }

    /**
     * 霜冻线性回归预测
     */
    @RequestMapping(value = "/line/frosty/predict")
    public void lineFrostyApi() {
        double[][] data = new double[6][4];
        data[0] = new double[]{1, 10, -8.5, 1}; //0.9
        data[1] = new double[]{2, 6, -9, 1};  //0.8
        data[2] = new double[]{3, 23, -0.5, 2}; //0.3
        data[3] = new double[]{4, 15, -1.5, 2}; //0.7
        data[4] = new double[]{5, 2, -0.5, 2}; //0.2
        data[5] = new double[]{6, 10, -2.5, 3}; //0.7
        /**
         *  y >= 0.8 风险为：高
         *  0.8 > y >= 0.5风险为：中
         *  y < 0.5 风险为：低
         */
        for (double[] datum : data) {
            double level = linearRegression.lineFrosty(datum[0], datum[1], datum[2], datum[3]);
            System.out.println(level);
        }
    }


    /**
     * 风灾逻辑回归
     */
    @RequestMapping(value = "/log/wd")
    public void logWdApi() {
        SampleInfo sampleInfo = yamlUtils.loadWdData();
        //训练模型
        LogisticRegression logisticRegression = new LogisticRegression(0.01, 500);

        double[][] features = dataUtils.standardization2DArray(sampleInfo.features);
        double[] targets = dataUtils.standardizationArray(sampleInfo.targets);

        double[] regressionModel = logisticRegression.train(features, targets);
        //使用模型预测
        double logicValue0 = logisticRegression.prediction(new double[]{15}, regressionModel);
        double logicValue1 = logisticRegression.prediction(new double[]{6}, regressionModel);
        double logicValue2 = logisticRegression.prediction(new double[]{35}, regressionModel);
        System.out.println("预测值：" + logicValue0 + "实际值：1");
        System.out.println("预测值：" + logicValue1 + "实际值：0");
        System.out.println("预测值：" + logicValue2 + "实际值：1");
    }

    /**
     * 风灾 线性回归方程
     */
    @RequestMapping(value = "/line/wd/fun")
    public String lineWdFun() {
        SampleInfo sampleInfo = yamlUtils.loadWdData();
        double[][] features = sampleInfo.features;
        double[] levels = sampleInfo.levels;
        //训练模型
        double[] frosty = Arrays.stream(levels).filter(x -> x != 0.0d).toArray();
        // 去掉level为0的样本
        double lineFeatures[][] = new double[frosty.length][];
        double lineLevels[] = new double[frosty.length];
        int j = 0;
        for (int i = 0; i < levels.length; i++) {
            if (levels[i] == 0.0d) {
                continue;
            } else {
                lineFeatures[j] = features[i];
                lineLevels[j] = levels[i];
            }
            j++;
        }
        double[] train = linearRegression.train(lineFeatures, lineLevels);
        StringBuilder funText = new StringBuilder("f(x)=");
        for (int i = 0; i < train.length; i++) {
            DecimalFormat format = new DecimalFormat("#.########");
            if (i == 0) {
                funText.append(format.format(train[0]));
            } else {
                String parameter = String.format("+ x%s*%s ", i, format.format(train[i]));
                funText.append(parameter);
            }
        }
        System.out.println("风灾回归方程：" + funText);
        return funText.toString();
    }

    /**
     * 风灾 线性回归预测
     */
    @RequestMapping(value = "/line/wd/predict")
    public void lineWdApi() {

        double[][] data = new double[6][1];
        data[0] = new double[]{3};
        data[1] = new double[]{8};
        data[2] = new double[]{14};
        data[3] = new double[]{21};
        data[4] = new double[]{25};
        data[5] = new double[]{35};

        for (double[] datum : data) {
            double level = linearRegression.lineWd(datum[0]);
            System.out.println(level);
        }
    }


    /**
     * 干旱 逻辑回归
     */
    @RequestMapping(value = "/log/drt")
    public void logDrtApi() {
        SampleInfo sampleInfo = yamlUtils.loadDrtData();
        //训练模型
        LogisticRegression logisticRegression = new LogisticRegression(0.01, 500);

        double[][] features = dataUtils.standardization2DArray(sampleInfo.features);
        double[] targets = dataUtils.standardizationArray(sampleInfo.targets);

        double[] regressionModel = logisticRegression.train(features, targets);
        //使用模型预测
        double logicValue0 = logisticRegression.prediction(new double[]{-105}, regressionModel);
        double logicValue1 = logisticRegression.prediction(new double[]{-26}, regressionModel);
        double logicValue2 = logisticRegression.prediction(new double[]{-75}, regressionModel);
        System.out.println("预测值：" + logicValue0 + "实际值：1");
        System.out.println("预测值：" + logicValue1 + "实际值：0");
        System.out.println("预测值：" + logicValue2 + "实际值：1");
    }

    /**
     * 干旱 线性回归方程
     */
    @RequestMapping(value = "/line/drt/fun")
    public String lineDrtFun() {
        SampleInfo sampleInfo = yamlUtils.loadDrtData();
        double[][] features = sampleInfo.features;
        double[] levels = sampleInfo.levels;
        //训练模型
        double[] frosty = Arrays.stream(levels).filter(x -> x != 0.0d).toArray();
        // 去掉level为0的样本
        double lineFeatures[][] = new double[frosty.length][];
        double lineLevels[] = new double[frosty.length];
        int j = 0;
        for (int i = 0; i < levels.length; i++) {
            if (levels[i] == 0.0d) {
                continue;
            } else {
                lineFeatures[j] = features[i];
                lineLevels[j] = levels[i];
            }
            j++;
        }

        double[] train = linearRegression.train(lineFeatures, lineLevels);
        StringBuilder funText = new StringBuilder("f(x)=");
        for (int i = 0; i < train.length; i++) {
            DecimalFormat format = new DecimalFormat("#.########");
            if (i == 0) {
                funText.append(format.format(train[0]));
            } else {
                String parameter = String.format("+ x%s*%s ", i, format.format(train[i]));
                funText.append(parameter);
            }
        }
        System.out.println("干旱回归方程：" + funText);
        return funText.toString();
    }

    /**
     * 干旱 线性回归预测
     */
    @RequestMapping(value = "/line/drt/predict")
    public void lineDrtApi() {
        double[][] data = new double[4][1];
        data[0] = new double[]{-3};
        data[1] = new double[]{-20};
        data[2] = new double[]{-55};
        data[3] = new double[]{-120};

        for (double[] datum : data) {
            double level = linearRegression.lineDrt(datum[0]);
            System.out.println(level);
        }
    }


    /**
     * 连阴雨逻辑回归
     */
    @RequestMapping(value = "/log/wtr")
    public void logWtrApi() {
        SampleInfo sampleInfo = yamlUtils.loadWtrData();
        //训练模型
        LogisticRegression logisticRegression = new LogisticRegression(0.01, 500);

        double[][] features = dataUtils.standardization2DArray(sampleInfo.features);
        double[] targets = dataUtils.standardizationArray(sampleInfo.targets);

        double[] regressionModel = logisticRegression.train(features, targets);
        //使用模型预测
        double logicValue0 = logisticRegression.prediction(new double[]{0.6}, regressionModel);
        double logicValue1 = logisticRegression.prediction(new double[]{1.1}, regressionModel);
        double logicValue2 = logisticRegression.prediction(new double[]{3.5}, regressionModel);
        System.out.println("预测值：" + logicValue0 + "实际值：0");
        System.out.println("预测值：" + logicValue1 + "实际值：1");
        System.out.println("预测值：" + logicValue2 + "实际值：1");
    }

    /**
     * 连阴雨 线性回归方程
     */
    @RequestMapping(value = "/line/wtr/fun")
    public String lineWtrFun() {
        SampleInfo sampleInfo = yamlUtils.loadWtrData();
        double[][] features = sampleInfo.features;
        double[] levels = sampleInfo.levels;
        //训练模型
        double[] frosty = Arrays.stream(levels).filter(x -> x != 0.0d).toArray();
        // 去掉level为0的样本
        double lineFeatures[][] = new double[frosty.length][];
        double lineLevels[] = new double[frosty.length];
        int j = 0;
        for (int i = 0; i < levels.length; i++) {
            if (levels[i] == 0.0d) {
                continue;
            } else {
                lineFeatures[j] = features[i];
                lineLevels[j] = levels[i];
            }
            j++;
        }

        double[] train = linearRegression.train(lineFeatures, lineLevels);
        StringBuilder funText = new StringBuilder("f(x)=");
        for (int i = 0; i < train.length; i++) {
            DecimalFormat format = new DecimalFormat("#.########");
            if (i == 0) {
                funText.append(format.format(train[0]));
            } else {
                String parameter = String.format("+ x%s*%s ", i, format.format(train[i]));
                funText.append(parameter);
            }
        }
        System.out.println("连阴雨回归方程：" + funText);
        return funText.toString();
    }

    /**
     * 连阴雨 线性回归预测
     */
    @RequestMapping(value = "/line/wtr/predict")
    public void lineWtrApi() {
        double[][] data = new double[4][1];
        data[0] = new double[]{1.1, 4};
        data[1] = new double[]{1.2, 6};
        data[2] = new double[]{5, 4};
        data[3] = new double[]{8, 6};

        for (double[] datum : data) {
            double level = linearRegression.lineWtr(datum[0], datum[1]);
            System.out.println(level);
        }
    }


    /**
     * 损失率线性回归方程
     */
    @RequestMapping(value = "/line/loss/fun")
    public void lineDroughtFun() {
        String[] files = new String[]{"loss-rate/drought.yml", "loss-rate/pest.yml", "loss-rate/storm.yml"};
        String[] nodes = new String[]{"drought", "pest", "storm"};

        for (int i = 0; i < files.length; i++) {
            String file = files[i];
            String node = nodes[i];

            SampleInfo sample = yamlUtils.loadLossRateData(file, node);
            //训练模型
            double[] train = linearRegression.train(sample.features, sample.targets);
            StringBuilder funText = new StringBuilder("f(x)=");
            for (int j = 0; j < train.length; j++) {
                DecimalFormat format = new DecimalFormat("#.########");
                if (j == 0) {
                    funText.append(format.format(train[0]));
                } else {
                    String parameter = String.format("+ x%s*%s ", j, format.format(train[j]));
                    funText.append(parameter);
                }
            }
            System.out.println(node + "回归方程：" + funText);
        }
    }


    /**
     * 干旱 线性回归预测
     */
    @RequestMapping(value = "/line/loss/predict")
    public void lineLossApi() {

        DecimalFormat format = new DecimalFormat("#.####");

        double[][] dataDrt = new double[3][1];
        dataDrt[0] = new double[]{0.122426852, 2.026832442};
        dataDrt[1] = new double[]{0.069385724, 1.148716039};
        dataDrt[2] = new double[]{0.022935, 0.369002};

        for (double[] datum : dataDrt) {
            double level = linearRegression.droughtFun(datum[1]);
            System.out.println("【干旱】损失率 预测值：" + format.format(level) + " 实际值：" + format.format(datum[0]));
        }

        double[][] dataPest = new double[3][1];
        dataPest[0] = new double[]{0.1812, 2.999867};
        dataPest[1] = new double[]{0.293842225, 4.864720056};
        dataPest[2] = new double[]{0.063, 1.030707};

        for (double[] datum : dataPest) {
            double level = linearRegression.pestFun(datum[1]);
            System.out.println("【病虫害】损失率 预测值：" + format.format(level) + " 实际值：" + format.format(datum[0]));
        }

        double[][] dataStorm = new double[3][1];
        dataStorm[0] = new double[]{0.256, 4.238218};
        dataStorm[1] = new double[]{0.074116938, 1.227043892};
        dataStorm[2] = new double[]{0.039455, 0.657589};

        for (double[] datum : dataStorm) {
            double level = linearRegression.stormFUn(datum[1]);
            System.out.println("【暴风】损失率 预测值：" + format.format(level) + " 实际值：" + format.format(datum[0]));
        }
    }

}

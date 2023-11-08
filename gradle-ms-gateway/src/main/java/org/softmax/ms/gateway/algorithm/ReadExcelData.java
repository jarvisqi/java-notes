package org.softmax.ms.gateway.algorithm;

import cn.hutool.core.io.FileUtil;
import cn.hutool.poi.excel.ExcelReader;
import cn.hutool.poi.excel.ExcelUtil;
import org.apache.commons.math3.stat.regression.OLSMultipleLinearRegression;

import java.text.DecimalFormat;
import java.util.List;
import java.util.Map;

public class ReadExcelData {

    public static void main(String[] arg) {

//        drought();
//        rain();
//        diseases();
//        storm();
        rainstorm();
    }

    public void freezingDamage() {

        double[][] targets = new double[710][8];
        double[] t1 = new double[710];
        double[] t2 = new double[710];
        double[] t3 = new double[710];
        ExcelReader reader = ExcelUtil.getReader(FileUtil.file("/Users/jarvis/Downloads/data.xlsx"), 0);
        List<Map<String, Object>> readAll = reader.readAll();

        int t = 0, n = 0;
        for (Map<String, Object> stringObjectMap : readAll) {
            double x1 = Double.valueOf(stringObjectMap.get("生长期值").toString());
            double x2 = Double.valueOf(stringObjectMap.get("08-08时降水量").toString());
            double x3 = Double.valueOf(stringObjectMap.get("日照时数（直接辐射计算值）").toString());
            double x4 = Double.valueOf(stringObjectMap.get("过程最低气温（℃）").toString());
            double x5 = Double.valueOf(stringObjectMap.get("过程平均日最低气温（℃）").toString());
            double x6 = Double.valueOf(stringObjectMap.get("过程平均日照时数（小时）").toString());
            double x7 = Double.valueOf(stringObjectMap.get("过程降水量（毫米）").toString());
            double x8 = Double.valueOf(stringObjectMap.get("冻害低温持续时间（日）").toString());
            double x9 = Double.valueOf(stringObjectMap.get("日最低气温（℃）").toString());

            targets[t] = new double[]{x1, x2, x3, x4, x5, x6, x7, x8, x9};

            double x10 = Double.valueOf(stringObjectMap.get("风险值").toString());
            double x11 = Double.valueOf(stringObjectMap.get("赔付率").toString());
            double x12 = Double.valueOf(stringObjectMap.get("损失率").toString());
//            t1[n] = x10;
//            t2[n] = x11;
            t3[n] = x12;
            t++;
            n++;

        }


        OLSMultipleLinearRegression regression = new OLSMultipleLinearRegression();
        //计算
        regression.newSampleData(t3, targets);

        double[] train = regression.estimateRegressionParameters();
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
        System.out.println("冻害回归方程：" + funText);
    }

    public static void drought() {

        int rows = 902;
        double[][] targets = new double[rows][3];
        double[] t1 = new double[rows];
        double[] t2 = new double[rows];
        double[] t3 = new double[rows];
        ExcelReader reader = ExcelUtil.getReader(FileUtil.file("/Users/jarvis/Downloads/data.xlsx"), 1);
        List<Map<String, Object>> readAll = reader.readAll();

        int t = 0, n = 0;
        for (Map<String, Object> stringObjectMap : readAll) {
            double x1 = Double.valueOf(stringObjectMap.get("生长期值").toString());
            double x2 = Double.valueOf(stringObjectMap.get("连续无降水天数（日）").toString());
            double x3 = Double.valueOf(stringObjectMap.get("降水量距平百分率（月度）").toString());

            targets[t] = new double[]{x1, x2, x3};

            double x10 = Double.valueOf(stringObjectMap.get("风险值").toString());
            double x11 = Double.valueOf(stringObjectMap.get("赔付率").toString());
            double x12 = Double.valueOf(stringObjectMap.get("损失率").toString());
            t1[n] = x10;
            t2[n] = x11;
            t3[n] = x12;
            t++;
            n++;

        }


        OLSMultipleLinearRegression regression1 = new OLSMultipleLinearRegression();
        //计算
        regression1.newSampleData(t3, targets);

        double[] train1 = regression1.estimateRegressionParameters();
        StringBuilder funText1 = new StringBuilder("f(x)=");
        for (int i = 0; i < train1.length; i++) {
            DecimalFormat format = new DecimalFormat("#.########");
            if (i == 0) {
                funText1.append(format.format(train1[0]));
            } else {
                String parameter = String.format("+ x%s*%s ", i, format.format(train1[i]));
                funText1.append(parameter);
            }
        }

        OLSMultipleLinearRegression regression2 = new OLSMultipleLinearRegression();
        //计算
        regression2.newSampleData(t2, targets);
        double[] train2 = regression2.estimateRegressionParameters();
        StringBuilder funText2 = new StringBuilder("f(x)=");
        for (int i = 0; i < train2.length; i++) {
            DecimalFormat format = new DecimalFormat("#.########");
            if (i == 0) {
                funText2.append(format.format(train2[0]));
            } else {
                String parameter = String.format("+ x%s*%s ", i, format.format(train2[i]));
                funText2.append(parameter);
            }
        }

        OLSMultipleLinearRegression regression3 = new OLSMultipleLinearRegression();
        //计算
        regression3.newSampleData(t3, targets);
        double[] train3 = regression3.estimateRegressionParameters();
        StringBuilder funText3 = new StringBuilder("f(x)=");
        for (int i = 0; i < train3.length; i++) {
            DecimalFormat format = new DecimalFormat("#.########");
            if (i == 0) {
                funText3.append(format.format(train3[0]));
            } else {
                String parameter = String.format("+ x%s*%s ", i, format.format(train3[i]));
                funText3.append(parameter);
            }
        }

        System.out.println("干旱回归方程：" + funText1);
        System.out.println("干旱回归方程：" + funText2);
        System.out.println("干旱回归方程：" + funText3);
    }


    public static void rain() {

        int rows = 177;
        double[][] targets = new double[rows][6];
        double[] t1 = new double[rows];
        double[] t2 = new double[rows];
        double[] t3 = new double[rows];
        ExcelReader reader = ExcelUtil.getReader(FileUtil.file("/Users/jarvis/Downloads/data.xlsx"), 2);
        List<Map<String, Object>> readAll = reader.readAll();

        int t = 0, n = 0;
        for (Map<String, Object> stringObjectMap : readAll) {
            double x1 = Double.valueOf(stringObjectMap.get("生长期值").toString());
            double x2 = Double.valueOf(stringObjectMap.get("08-08时降水量").toString());
            double x3 = Double.valueOf(stringObjectMap.get("日照时数（直接辐射计算值）").toString());
            int x4 = Integer.valueOf(stringObjectMap.get("连续降雨天数").toString());
            double x5 = Double.valueOf(stringObjectMap.get("过程降水量（毫米）").toString());
            double x6 = Double.valueOf(stringObjectMap.get("总日照时数（小时）").toString());

            targets[t] = new double[]{x1, x2, x3, x4, x5, x6};

            double x10 = Double.valueOf(stringObjectMap.get("风险值").toString());
            double x11 = Double.valueOf(stringObjectMap.get("赔付率").toString());
            double x12 = Double.valueOf(stringObjectMap.get("损失率").toString());

            t1[n] = x10;
            t2[n] = x11;
            t3[n] = x12;

            t++;
            n++;
        }

        OLSMultipleLinearRegression regression1 = new OLSMultipleLinearRegression();
        //计算
        regression1.newSampleData(t1, targets);

        double[] train1 = regression1.estimateRegressionParameters();
        StringBuilder funText1 = new StringBuilder("f(x)=");
        for (int i = 0; i < train1.length; i++) {
            DecimalFormat format = new DecimalFormat("#.########");
            if (i == 0) {
                funText1.append(format.format(train1[0]));
            } else {
                String parameter = String.format("+ x%s*%s ", i, format.format(train1[i]));
                funText1.append(parameter);
            }
        }

        OLSMultipleLinearRegression regression2 = new OLSMultipleLinearRegression();
        //计算
        regression2.newSampleData(t2, targets);
        double[] train2 = regression2.estimateRegressionParameters();
        StringBuilder funText2 = new StringBuilder("f(x)=");
        for (int i = 0; i < train2.length; i++) {
            DecimalFormat format = new DecimalFormat("#.########");
            if (i == 0) {
                funText2.append(format.format(train2[0]));
            } else {
                String parameter = String.format("+ x%s*%s ", i, format.format(train2[i]));
                funText2.append(parameter);
            }
        }

        OLSMultipleLinearRegression regression3 = new OLSMultipleLinearRegression();
        //计算
        regression3.newSampleData(t3, targets);
        double[] train3 = regression3.estimateRegressionParameters();
        StringBuilder funText3 = new StringBuilder("f(x)=");
        for (int i = 0; i < train3.length; i++) {
            DecimalFormat format = new DecimalFormat("#.########");
            if (i == 0) {
                funText3.append(format.format(train3[0]));
            } else {
                String parameter = String.format("+ x%s*%s ", i, format.format(train3[i]));
                funText3.append(parameter);
            }
        }
//
//        System.out.println("连阴雨回归方程：" + funText1);
//        System.out.println("连阴雨回归方程：" + funText2);
        System.out.println("连阴雨回归方程：" + funText3);
    }


    public static void diseases() {

        int rows = 3153;
        double[][] targets = new double[rows][7];
        double[] t1 = new double[rows];
        double[] t2 = new double[rows];
        double[] t3 = new double[rows];
        ExcelReader reader = ExcelUtil.getReader(FileUtil.file("/Users/jarvis/Downloads/data.xlsx"), 3);
        List<Map<String, Object>> readAll = reader.readAll();

        int t = 0, n = 0;
        for (Map<String, Object> stringObjectMap : readAll) {
            double x1 = Double.valueOf(stringObjectMap.get("生长期值").toString());
            double x2 = Double.valueOf(stringObjectMap.get("平均气温").toString());
            double x3 = Double.valueOf(stringObjectMap.get("平均相对湿度").toString());
            double x4 = Double.valueOf(stringObjectMap.get("08-08时降水量").toString());
            double x5 = Double.valueOf(stringObjectMap.get("旬降水量（毫米）").toString());
            double x6 = Double.valueOf(stringObjectMap.get("旬平均气温（℃）").toString());
            double x7 = Double.valueOf(stringObjectMap.get("旬平均相对湿度（%）").toString());

            targets[t] = new double[]{x1, x2, x3, x4, x5, x6, x7};

            double x10 = Double.valueOf(stringObjectMap.get("风险值").toString());
            double x11 = Double.valueOf(stringObjectMap.get("赔付率").toString());
            double x12 = Double.valueOf(stringObjectMap.get("损失率").toString());

            t1[n] = x10;
            t2[n] = x11;
            t3[n] = x12;

            t++;
            n++;
        }

        OLSMultipleLinearRegression regression1 = new OLSMultipleLinearRegression();
        //计算
        regression1.newSampleData(t1, targets);

        double[] train1 = regression1.estimateRegressionParameters();
        StringBuilder funText1 = new StringBuilder("f(x)=");
        for (int i = 0; i < train1.length; i++) {
            DecimalFormat format = new DecimalFormat("#.########");
            if (i == 0) {
                funText1.append(format.format(train1[0]));
            } else {
                String parameter = String.format("+ x%s*%s ", i, format.format(train1[i]));
                funText1.append(parameter);
            }
        }

        OLSMultipleLinearRegression regression2 = new OLSMultipleLinearRegression();
        //计算
        regression2.newSampleData(t2, targets);
        double[] train2 = regression2.estimateRegressionParameters();
        StringBuilder funText2 = new StringBuilder("f(x)=");
        for (int i = 0; i < train2.length; i++) {
            DecimalFormat format = new DecimalFormat("#.########");
            if (i == 0) {
                funText2.append(format.format(train2[0]));
            } else {
                String parameter = String.format("+ x%s*%s ", i, format.format(train2[i]));
                funText2.append(parameter);
            }
        }

        OLSMultipleLinearRegression regression3 = new OLSMultipleLinearRegression();
        //计算
        regression3.newSampleData(t3, targets);
        double[] train3 = regression3.estimateRegressionParameters();
        StringBuilder funText3 = new StringBuilder("f(x)=");
        for (int i = 0; i < train3.length; i++) {
            DecimalFormat format = new DecimalFormat("#.########");
            if (i == 0) {
                funText3.append(format.format(train3[0]));
            } else {
                String parameter = String.format("+ x%s*%s ", i, format.format(train3[i]));
                funText3.append(parameter);
            }
        }

        System.out.println("病虫害回归方程：" + funText1);
        System.out.println("病虫害回归方程：" + funText2);
        System.out.println("病虫害回归方程：" + funText3);
    }


    public static void storm() {

        int rows = 747;
        double[][] targets = new double[rows][3];
        double[] t1 = new double[rows];
        double[] t2 = new double[rows];
        double[] t3 = new double[rows];
        ExcelReader reader = ExcelUtil.getReader(FileUtil.file("/Users/jarvis/Downloads/data.xlsx"), 4);
        List<Map<String, Object>> readAll = reader.readAll();

        int t = 0, n = 0;
        for (Map<String, Object> stringObjectMap : readAll) {
            double x1 = Double.valueOf(stringObjectMap.get("生长期值").toString());
            double x2 = Double.valueOf(stringObjectMap.get("最大风速").toString());
            double x3 = Double.valueOf(stringObjectMap.get("极大风速").toString());

            targets[t] = new double[]{x1, x2, x3};

            double x10 = Double.valueOf(stringObjectMap.get("风险值").toString());
            double x11 = Double.valueOf(stringObjectMap.get("赔付率").toString());
            double x12 = Double.valueOf(stringObjectMap.get("损失率").toString());

            t1[n] = x10;
            t2[n] = x11;
            t3[n] = x12;

            t++;
            n++;
        }

        OLSMultipleLinearRegression regression1 = new OLSMultipleLinearRegression();
        //计算
        regression1.newSampleData(t1, targets);

        double[] train1 = regression1.estimateRegressionParameters();
        StringBuilder funText1 = new StringBuilder("f(x)=");
        for (int i = 0; i < train1.length; i++) {
            DecimalFormat format = new DecimalFormat("#.########");
            if (i == 0) {
                funText1.append(format.format(train1[0]));
            } else {
                String parameter = String.format("+ x%s*%s ", i, format.format(train1[i]));
                funText1.append(parameter);
            }
        }

        OLSMultipleLinearRegression regression2 = new OLSMultipleLinearRegression();
        //计算
        regression2.newSampleData(t2, targets);
        double[] train2 = regression2.estimateRegressionParameters();
        StringBuilder funText2 = new StringBuilder("f(x)=");
        for (int i = 0; i < train2.length; i++) {
            DecimalFormat format = new DecimalFormat("#.########");
            if (i == 0) {
                funText2.append(format.format(train2[0]));
            } else {
                String parameter = String.format("+ x%s*%s ", i, format.format(train2[i]));
                funText2.append(parameter);
            }
        }

        OLSMultipleLinearRegression regression3 = new OLSMultipleLinearRegression();
        //计算
        regression3.newSampleData(t3, targets);
        double[] train3 = regression3.estimateRegressionParameters();
        StringBuilder funText3 = new StringBuilder("f(x)=");
        for (int i = 0; i < train3.length; i++) {
            DecimalFormat format = new DecimalFormat("#.########");
            if (i == 0) {
                funText3.append(format.format(train3[0]));
            } else {
                String parameter = String.format("+ x%s*%s ", i, format.format(train3[i]));
                funText3.append(parameter);
            }
        }

        System.out.println("暴风回归方程：" + funText1);
        System.out.println("暴风回归方程：" + funText2);
        System.out.println("暴风回归方程：" + funText3);
    }

    public static void rainstorm() {

        int rows = 331;
        double[][] targets = new double[rows][2];
        double[] t1 = new double[rows];
        double[] t2 = new double[rows];
        double[] t3 = new double[rows];
        ExcelReader reader = ExcelUtil.getReader(FileUtil.file("/Users/jarvis/Downloads/data.xlsx"), 5);
        List<Map<String, Object>> readAll = reader.readAll();

        int t = 0, n = 0;
        for (Map<String, Object> stringObjectMap : readAll) {
            double x1 = Double.valueOf(stringObjectMap.get("生长期值").toString());
            double x2 = Double.valueOf(stringObjectMap.get("08-08时降水量").toString());


            targets[t] = new double[]{x1, x2};

            double x10 = Double.valueOf(stringObjectMap.get("风险值").toString());
            double x11 = Double.valueOf(stringObjectMap.get("赔付率").toString());
            double x12 = Double.valueOf(stringObjectMap.get("损失率").toString());

            t1[n] = x10;
            t2[n] = x11;
            t3[n] = x12;

            t++;
            n++;
        }

        OLSMultipleLinearRegression regression1 = new OLSMultipleLinearRegression();
        //计算
        regression1.newSampleData(t1, targets);

        double[] train1 = regression1.estimateRegressionParameters();
        StringBuilder funText1 = new StringBuilder("f(x)=");
        for (int i = 0; i < train1.length; i++) {
            DecimalFormat format = new DecimalFormat("#.########");
            if (i == 0) {
                funText1.append(format.format(train1[0]));
            } else {
                String parameter = String.format("+ x%s*%s ", i, format.format(train1[i]));
                funText1.append(parameter);
            }
        }

        OLSMultipleLinearRegression regression2 = new OLSMultipleLinearRegression();
        //计算
        regression2.newSampleData(t2, targets);
        double[] train2 = regression2.estimateRegressionParameters();
        StringBuilder funText2 = new StringBuilder("f(x)=");
        for (int i = 0; i < train2.length; i++) {
            DecimalFormat format = new DecimalFormat("#.########");
            if (i == 0) {
                funText2.append(format.format(train2[0]));
            } else {
                String parameter = String.format("+ x%s*%s ", i, format.format(train2[i]));
                funText2.append(parameter);
            }
        }

        OLSMultipleLinearRegression regression3 = new OLSMultipleLinearRegression();
        //计算
        regression3.newSampleData(t3, targets);
        double[] train3 = regression3.estimateRegressionParameters();
        StringBuilder funText3 = new StringBuilder("f(x)=");
        for (int i = 0; i < train3.length; i++) {
            DecimalFormat format = new DecimalFormat("#.########");
            if (i == 0) {
                funText3.append(format.format(train3[0]));
            } else {
                String parameter = String.format("+ x%s*%s ", i, format.format(train3[i]));
                funText3.append(parameter);
            }
        }

        System.out.println("暴雨回归方程：" + funText1);
        System.out.println("暴雨回归方程：" + funText2);
        System.out.println("暴雨回归方程：" + funText3);
    }

}

package org.softmax.ms.gateway.algorithm;

public class HistoryFun {

    /**
     * 冻害风险值
     * <p>
     * * "生长期值")
     * * "08-08时降水量")
     * * "日照时数（直接辐射计算值
     * * "日最低气温（℃）
     *
     * @param x1
     * @param x2
     * @param x3
     * @param x4
     * @return
     */
    public static double freezeRisk(double x1, double x2, double x3, double x4) {
        double result = 0.42455205 + x1 * -0.01321685 + x2 * 0.00320702 + x3 * 0.00901156 + x4 * -0.01975046;
        return result < 0 ? 0 : result;
    }

    /**
     * 冻害赔付率
     * * "生长期值")
     * * "08-08时降水量")
     * * "日照时数（直接辐射计算值
     * * "日最低气温（℃）
     *
     * @param x1
     * @param x2
     * @param x3
     * @param x4
     * @return
     */
    public static double freezeCompensate(double x1, double x2, double x3, double x4) {
        double result = -33.92998109 + x1 * 6.04176074 + x2 * 0.35478996 + x3 * 0.31841719 + x4 * 1.28819394;
        return result < 0 ? 0 : result;
    }

    /**
     * 冻害损失率
     * * "生长期值")
     * * "08-08时降水量")
     * * "日照时数（直接辐射计算值
     * * "日最低气温（℃）
     *
     * @param x1
     * @param x2
     * @param x3
     * @param x4
     * @return
     */
    public static double freezeLoss(double x1, double x2, double x3, double x4) {
        double result = 0.42455205 + x1 * -0.01321685 + x2 * 0.00320702 + x3 * 0.00901156 + x4 * -0.01975046;
        return result < 0 ? 0 : result;
    }

    /**
     * 干旱风险值
     * 生长期值
     * 湿度
     *
     * @param x1
     * @param x2
     * @return
     */
    public static double drtRisk(double x1, double x2) {
        double result = 15.24816156 + x1 * -0.33469871 + x2 * 0.00000115;
        return result < 0 ? 0 : result;
    }

    /**
     * 干旱赔付率
     * 生长期值
     * 湿度
     *
     * @param x1
     * @param x2
     * @return
     */
    public static double drtCompensate(double x1, double x2) {
        double result = 253.03379608 + x1 * -5.55749637 + x2 * 0.00001901;
        return result < 0 ? 0 : result;
    }

    /**
     * 干旱损失率
     * 生长期值
     * 湿度
     *
     * @param x1
     * @param x2
     * @return
     */
    public static double drtLoss(double x1, double x2) {
        double result = 15.24816156 + x1 * -0.33469871 + x2 * 0.00000115;
        return result < 0 ? 0 : result;
    }


    /**
     * 连阴雨风险值
     * ("生长期值
     * ("08-08时降水量
     * ("日照时数（直接辐射计算值）
     *
     * @param x1
     * @param x2
     * @param x3
     * @return
     */
    public static double rainRisk(double x1, double x2, double x3) {
        double result = 0.17215833 + x1 * 0.00026962 + x2 * 0.00023921 + x3 * 0.00617665;
        return result < 0 ? 0 : result;
    }

    /**
     * 连阴雨赔付率
     * <p>
     * ("生长期值
     * ("08-08时降水量
     * ("日照时数（直接辐射计算值）
     *
     * @param x1
     * @param x2
     * @param x3
     * @return
     */
    public static double rainCompensate(double x1, double x2, double x3) {
        double result = 46.62751047 + x1 * 0.1966473 + x2 * 0.26881812 + x3 * 1.29376449;
        return result < 0 ? 0 : result;
    }

    /**
     * 病虫害风险值
     * <p>
     * "生长期值
     * "平均气温
     * "平均相对湿度
     * "08-08时降水量"
     *
     * @param x1
     * @param x2
     * @param x3
     * @param x4
     * @return
     */
    public static double diseasesRisk(double x1, double x2, double x3, double x4) {
        double result = 0.14987293 + x1 * 0.00820675 + x2 * -0.00000008 + x3 * 0.00000009 + x4 * 0.00515979;
        return result < 0 ? 0 : result;
    }


    /**
     * 暴风风险值
     * <p>
     * 生长期值
     * 最大风速
     * 极大风速
     *
     * @param x1
     * @param x2
     * @param x3
     * @return
     */
    public static double stormRisk(double x1, double x2, double x3) {
        double result = 0.04970283 + x1 * -0.00361367 + x2 * 0.03335267 + x3 * 0.00544931;
        return result < 0 ? 0 : result;
    }

    /**
     * 暴风赔付率
     * <p>
     * 生长期值
     * 最大风速
     * 极大风速
     *
     * @param x1
     * @param x2
     * @param x3
     * @return
     */
    public static double stormCompensate(double x1, double x2, double x3) {
        double result = -42.27174224 + x1 * -0.44600274 + x2 * 1.89896667 + x3 * 6.75135176;
        return result < 0 ? 0 : result;
    }

    /**
     * 暴风损失率
     * <p>
     * 生长期值
     * 最大风速
     * 极大风速
     *
     * @param x1
     * @param x2
     * @param x3
     * @return
     */
    public static double stormLoss(double x1, double x2, double x3) {
        double result = -2.53328674 + x1 * -0.02656009 + x2 * 0.11459873 + x3 * 0.40621985;
        return result < 0 ? 0 : result;
    }


    /**
     * 暴雨风险值
     * <p>
     * * 生长期值
     * * 08-08时降水量
     *
     * @param x1
     * @param x2
     * @return
     */
    public static double rainstormRisk(double x1, double x2) {
        double result = -0.34670757 + x1 * 0.01136261 + x2 * 0.01577149;
        return result < 0 ? 0 : result;
    }

    /**
     * 暴雨赔付率
     * <p>
     * * 生长期值
     * * 08-08时降水量
     *
     * @param x1
     * @param x2
     * @return
     */
    public static double rainstormCompensate(double x1, double x2) {
        double result = -11.51775834 + x1 * 1.91248439 + x2 * 0.15583495;
        return result < 0 ? 0 : result;
    }

    /**
     * 暴雨损失率
     * <p>
     * 生长期值
     * 08-08时降水量
     *
     * @param x1
     * @param x2
     * @return
     */
    public static double rainstormLoss(double x1, double x2) {
        double result = -0.68827277 + x1 * 0.10909459 + x2 * 0.00973192;
        return result < 0 ? 0 : result;
    }
}

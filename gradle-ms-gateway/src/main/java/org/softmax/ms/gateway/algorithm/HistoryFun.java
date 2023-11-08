package org.softmax.ms.gateway.algorithm;

public class HistoryFun {


    /**
     * 冻害风险值
     * <p>
     * "生长期值")
     * "08-08时降水量")
     * "日照时数（直接辐射计算值
     * "过程最低气温（℃）
     * "过程平均日最低气温（℃
     * "过程平均日照时数（小时
     * "过程降水量（毫米）
     * "冻害低温持续时间（日
     * "日最低气温（℃）
     *
     * @param x1
     * @param x2
     * @param x3
     * @param x4
     * @param x5
     * @param x6
     * @param x7
     * @param x8
     * @param x9
     * @return
     */
    public static double freezeRisk(double x1, double x2, double x3, double x4, double x5, double x6, double x7, double x8, double x9) {
        double result = 0.35039163 + x1 * -0.0166164 + x2 * 0.00355372 + x3 * -0.00100981 + x4 * -0.00620832 +
                x5 * -0.01477011 + x6 * 0.01900672 + x7 * -0.00289628 + x8 * 0.01153698 + x9 * 0.00144749;
        return result < 0 ? 0 : result;
    }

    /**
     * 冻害赔付率
     * * "生长期值")
     * * "08-08时降水量")
     * * "日照时数（直接辐射计算值
     * * "过程最低气温（℃）
     * * "过程平均日最低气温（℃
     * * "过程平均日照时数（小时
     * * "过程降水量（毫米）")
     * * "冻害低温持续时间（日
     * * "日最低气温（℃）
     *
     * @param x1
     * @param x2
     * @param x3
     * @param x4
     * @param x5
     * @param x6
     * @param x7
     * @param x8
     * @param x9
     * @return
     */
    public static double freezeCompensate(double x1, double x2, double x3, double x4, double x5, double x6, double x7, double x8, double x9) {
        double result = -29.23383148 + x1 * 6.15078877 + x2 * 0.05381411 + x3 * 1.01263648 + x4 * -0.04338191 +
                x5 * 0.74668236 + x6 * -1.27411449 + x7 * 0.50724165 + x8 * -0.7322796 + x9 * 0.48406087;
        return result < 0 ? 0 : result;
    }

    /**
     * 冻害损失率
     * * "生长期值")
     * * "08-08时降水量")
     * * "日照时数（直接辐射计算值
     * * "过程最低气温（℃）
     * * "过程平均日最低气温（℃
     * * "过程平均日照时数（小时
     * * "过程降水量（毫米）")
     * * "冻害低温持续时间（日
     * * "日最低气温（℃）
     *
     * @param x1
     * @param x2
     * @param x3
     * @param x4
     * @param x5
     * @param x6
     * @param x7
     * @param x8
     * @param x9
     * @return
     */
    public static double freezeLoss(double x1, double x2, double x3, double x4, double x5, double x6, double x7, double x8, double x9) {
        double result = -1.71343631 + x1 * 0.37107656 + x2 * 0.00339368 + x3 * 0.06098527 + x4 * -0.0024397 +
                x5 * 0.04491035 + x6 * -0.07676592 + x7 * 0.03031654 + x8 * -0.04416608 + x9 * 0.02912271;
        return result < 0 ? 0 : result;
    }

    /**
     * 干旱风险值
     * 生长期值").toStr
     * 连续无降水天数（日）")
     * 降水量距平百分率（月度）
     *
     * @param x1
     * @param x2
     * @param x3
     * @return
     */
    public static double drtRisk(double x1, double x2, double x3) {
        double result = 16.14807018 + x1 * -0.28105177 + x2 * -0.12352405 + x3 * 0.05353386;
        return result < 0 ? 0 : result;
    }

    /**
     * 干旱赔付率
     * 生长期值").toStr
     * 连续无降水天数（日）")
     * 降水量距平百分率（月度）
     *
     * @param x1
     * @param x2
     * @param x3
     * @return
     */
    public static double drtCompensate(double x1, double x2, double x3) {
        double result = 268.00955 + x1 * -4.66650686 + x2 * -2.0527936 + x3 * 0.88822463;
        return result < 0 ? 0 : result;
    }

    /**
     * 干旱损失率
     * 生长期值").toStr
     * 连续无降水天数（日）")
     * 降水量距平百分率（月度）
     *
     * @param x1
     * @param x2
     * @param x3
     * @return
     */
    public static double drtLoss(double x1, double x2, double x3) {
        double result = 16.14807018 + x1 * -0.28105177 + x2 * -0.12352405 + x3 * 0.05353386;
        return result < 0 ? 0 : result;
    }


    /**
     * 连阴雨风险值
     * ("生长期值
     * ("08-08时降水量
     * ("日照时数（直接辐射计算值）
     * 连续降雨天数")
     * ("过程降水量（毫米）
     * ("总日照时数（小时）
     *
     * @param x1
     * @param x2
     * @param x3
     * @param x4
     * @param x5
     * @param x6
     * @return
     */
    public static double rainRisk(double x1, double x2, double x3, double x4, double x5, double x6) {
        double result = 0.15592687 + x1 * 0.00041637 + x2 * 0.00038282 + x3 * 0.00650131 + x4 * 0.00462228 +
                x5 * -0.00011448 + x6 * -0.00075859;
        return result < 0 ? 0 : result;
    }

    /**
     * 连阴雨赔付率
     *
     * @param x1
     * @param x2
     * @param x3
     * @param x4
     * @param x5
     * @param x6
     * @return
     */
    public static double rainCompensate(double x1, double x2, double x3, double x4, double x5, double x6) {
        double result = 58.12242406 + x1 * 0.09324408 + x2 * -0.18362281 + x3 * 1.87435844 + x4 * -4.00596456
                + x5 * 0.55629234 + x6 * 0.27931538;
        return result < 0 ? 0 : result;
    }

    /**
     * 病虫害风险值
     * <p>
     * "生长期值
     * "平均气温
     * "平均相对湿度
     * "08-08时降水量"
     * "旬降水量（毫米
     * "旬平均气温（℃）
     * "旬平均相对湿度（%）
     *
     * @param x1
     * @param x2
     * @param x3
     * @param x4
     * @param x5
     * @param x6
     * @param x7
     * @return
     */
    public static double diseasesRisk(double x1, double x2, double x3, double x4, double x5, double x6, double x7) {
        double result = 0.00899982 + x1 * 0.00855536 + x2 * -0.00000012 + x3 * 0.00000011 + x4 * 0.00302059
                + x5 * 0.0005639 + x6 * -0.007948 + x7 * 0.00181232;
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

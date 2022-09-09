package org.softmax.gradle.ml;

import org.ujmp.core.DenseMatrix;
import org.ujmp.core.Matrix;

/**
 * 逻辑回归i
 *
 * @author Jarvis
 */
public class LogisticRegression {

    public double step;
    public int epochs;

    public LogisticRegression() {
        this.step = 0.01;
        this.epochs = 500;
    }

    public LogisticRegression(double step, int epochs) {
        this.step = step;
        this.epochs = epochs;
    }

    /**
     * 训练模型
     *
     * @param features 特征值
     * @param targets  标签值
     * @return 模型参数
     */
    public double[] train(double[][] features, double[] targets) {

        if (features != null && targets != null && features.length == targets.length) {
//            期望矩阵
            Matrix matrWeights = DenseMatrix.Factory.zeros(features[0].length, 1);
//            数据矩阵
            Matrix matrData = DenseMatrix.Factory.zeros(features.length, features[0].length);
//            标志矩阵
            Matrix matrLable = DenseMatrix.Factory.zeros(features.length, 1);
//            统计difference的总体失误的辅助矩阵
            Matrix matrDiffUtil = DenseMatrix.Factory.zeros(features[0].length, features.length);
            for (int i = 0; i < features.length; i++) {
                for (int j = 0; j < features[0].length; j++) {
                    matrDiffUtil.setAsDouble(1, j, i);
                }
            }
            for (int i = 0; i < features.length; i++) {
//                初始化标志矩阵
                matrLable.setAsDouble(targets[i], i, 0);
                for (int j = 0; j < features[0].length; j++) {
//                    初始化数据矩阵
                    matrData.setAsDouble(features[i][j], i, j);
                    if (i == 0) {
//                        初始化期望矩阵
                        matrWeights.setAsDouble(1.0, j, 0);
                    }
                }
            }

            /*
             * 使用梯度下降法的思想：
             * 矩阵运算，参考：https://blog.csdn.net/lionel_fengj/article/details/53400715
             * */
            for (int i = 0; i < this.epochs; i++) {
//                将想要函数转换为sigmoid函数并得到的值
                Matrix result = sigmoid(matrData.mtimes(matrWeights));
//                求出预期和真实的差值
                Matrix difference = matrLable.minus(result);
//              matrData转置后和difference相乘，得到预期和真实差值的每一个值
                matrWeights = matrWeights.plus(matrData.transpose().mtimes(difference).times(this.step));
            }
            double[] rtn = new double[(int) matrWeights.getRowCount()];
            for (long i = 0; i < matrWeights.getRowCount(); i++) {
                rtn[(int) i] = matrWeights.getAsDouble(i, 0);
            }
            return rtn;
        }
        return null;
    }

    /**
     * 二分类sigmoid函数
     *
     * @param sourceMatrix
     * @return
     */
    public Matrix sigmoid(Matrix sourceMatrix) {
        Matrix rtn = DenseMatrix.Factory.zeros(sourceMatrix.getRowCount(), sourceMatrix.getColumnCount());
        for (int i = 0; i < sourceMatrix.getRowCount(); i++) {
            for (int j = 0; j < sourceMatrix.getColumnCount(); j++) {
                rtn.setAsDouble(sigmoid(sourceMatrix.getAsDouble(i, j)), i, j);
            }

        }
        return rtn;
    }

    public double sigmoid(double source) {
        return 1.0 / (1 + Math.exp(-1 * source));
    }

    /**
     * 预测值
     *
     * @param features 特征值参数
     * @param model    模型参数
     * @return 预测结果值
     */
    public double prediction(double[] features, double[] model) {
        // double logisticRegressionValue = model[0];
        double logisticRegressionValue = 0;
        for (int i = 0; i < features.length; i++) {
            logisticRegressionValue = logisticRegressionValue + features[i] * model[i];
        }
        logisticRegressionValue = sigmoid(logisticRegressionValue);
        double result = 0;
        if (logisticRegressionValue > 0.5) {
            result = 1;
        }
        return result;
    }

}

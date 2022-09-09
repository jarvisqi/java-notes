package org.softmax.gradle.controller;

import org.softmax.gradle.ml.LogisticRegression;
import org.springframework.beans.factory.config.YamlMapFactoryBean;
import org.springframework.core.io.ClassPathResource;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

/**
 * @author Jarvis
 */
@RestController
@RequestMapping(value = "/logistics", name = "RegressionController")
public class RegressionController {

    /**
     * 干热风
     */
    @RequestMapping(value = "/dwh")
    public void logisticRegressionApi() {

        //加载样本
        YamlMapFactoryBean yamlMapFactoryBean = new YamlMapFactoryBean();
        yamlMapFactoryBean.setResources(new ClassPathResource("dhw.yml"));
        Map<String, Object> objectMap = yamlMapFactoryBean.getObject();
        List<Map<String, String>> dhws = (List<Map<String, String>>) objectMap.get("dhws");

        double features[][] = new double[dhws.size()][];
        double targets[] = new double[dhws.size()];
        int i = 0;
        for (Map<String, String> dhw : dhws) {
            double mt = Double.parseDouble(dhw.get("mt"));
            double rh = Double.parseDouble(dhw.get("rh"));
            double ws = Double.parseDouble(dhw.get("ws"));
            double y = Double.parseDouble(dhw.get("y"));
            features[i] = new double[]{mt, rh, ws};
            targets[i] = y;

            i++;
        }
        //训练模型
        LogisticRegression logisticRegression = new LogisticRegression(0.01, 500);
        double[] regressionModel = logisticRegression.train(features, targets);
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
}

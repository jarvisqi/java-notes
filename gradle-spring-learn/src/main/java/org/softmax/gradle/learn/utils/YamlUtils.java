package org.softmax.gradle.learn.utils;

import org.softmax.gradle.learn.algorithm.SampleInfo;
import org.springframework.beans.factory.config.YamlMapFactoryBean;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

/**
 * @author Jarvis
 */
@Component
public class YamlUtils {

    /**
     * 加载干热风样本
     *
     * @return
     */
    public SampleInfo loadDhwData() {
        //加载样本
        List<Map<String, String>> dhws = loadConfig("index/dhw.yml", "dhws");

        double features[][] = new double[dhws.size()][];
        double targets[] = new double[dhws.size()];
        double level[] = new double[dhws.size()];
        int i = 0;
        for (Map<String, String> dhw : dhws) {
            double mt = Double.parseDouble(dhw.get("mt"));
            double rh = Double.parseDouble(dhw.get("rh"));
            double ws = Double.parseDouble(dhw.get("ws"));
            double y = Double.parseDouble(dhw.get("y"));
            double l = Double.parseDouble(dhw.get("l"));
            features[i] = new double[]{mt, rh, ws};
            targets[i] = y;
            level[i] = l;
            i++;
        }
        return new SampleInfo(features, targets, level);
    }

    /**
     * 加载霜冻样本
     *
     * @return
     */
    public SampleInfo loadFrostyData() {
        //加载样本
        List<Map<String, String>> frosty = loadConfig("index/frosty.yml", "frosty");

        double features[][] = new double[frosty.size()][];
        double targets[] = new double[frosty.size()];
        double level[] = new double[frosty.size()];
        int i = 0;
        for (Map<String, String> item : frosty) {
            double m = Double.parseDouble(item.get("m"));
            double d = Double.parseDouble(item.get("d"));
            double t = Double.parseDouble(item.get("t"));
            double gc = Double.parseDouble(item.get("gc"));
            double y = Double.parseDouble(item.get("y"));
            double l = Double.parseDouble(item.get("l"));
            features[i] = new double[]{m, d, t, gc};
            targets[i] = y;
            level[i] = l;
            i++;
        }
        return new SampleInfo(features, targets, level);
    }

    /**
     * 加载风灾样本
     *
     * @return
     */
    public SampleInfo loadWdData() {
        //加载样本
        List<Map<String, String>> wds = loadConfig("index/wd.yml", "wds");
        double features[][] = new double[wds.size()][];
        double targets[] = new double[wds.size()];
        double level[] = new double[wds.size()];
        int i = 0;
        for (Map<String, String> item : wds) {
            double ws = Double.parseDouble(item.get("ws"));
            double y = Double.parseDouble(item.get("y"));
            double l = Double.parseDouble(item.get("l"));
            features[i] = new double[]{ws};
            targets[i] = y;
            level[i] = l;
            i++;
        }
        return new SampleInfo(features, targets, level);
    }


    /**
     * 加载干旱样本
     *
     * @return
     */
    public SampleInfo loadDrtData() {
        //加载样本
        List<Map<String, String>> drts = loadConfig("index/drt.yml", "drts");
        double features[][] = new double[drts.size()][];
        double targets[] = new double[drts.size()];
        double level[] = new double[drts.size()];
        int i = 0;
        for (Map<String, String> item : drts) {
            double ws = Double.parseDouble(item.get("pa"));
            double y = Double.parseDouble(item.get("y"));
            double l = Double.parseDouble(item.get("l"));
            features[i] = new double[]{ws};
            targets[i] = y;
            level[i] = l;
            i++;
        }
        return new SampleInfo(features, targets, level);
    }


    /**
     * 加载连阴雨样本
     *
     * @return
     */
    public SampleInfo loadWtrData() {
        //加载样本
        List<Map<String, String>> wtrs = loadConfig("index/wtr.yml", "wtrs");
        double features[][] = new double[wtrs.size()][];
        double targets[] = new double[wtrs.size()];
        double level[] = new double[wtrs.size()];
        int i = 0;
        for (Map<String, String> item : wtrs) {
            double q = Double.parseDouble(item.get("q"));
            double d = Double.parseDouble(item.get("d"));
            double y = Double.parseDouble(item.get("y"));
            double l = Double.parseDouble(item.get("l"));
            features[i] = new double[]{q, d};
            targets[i] = y;
            level[i] = l;
            i++;
        }
        return new SampleInfo(features, targets, level);
    }


    /**
     * 加载损失率样本
     *
     * @param fileName
     * @param nodeName
     * @return
     */
    public SampleInfo loadLossRateData(String fileName, String nodeName) {
        //加载样本
        List<Map<String, String>> dataList = loadConfig(fileName, nodeName);
        double features[][] = new double[dataList.size()][];
        double targets[] = new double[dataList.size()];
        int i = 0;
        for (Map<String, String> item : dataList) {
            //赔付率
            double cr = Double.parseDouble(item.get("cr"));
            //赔付率
            double lr = Double.parseDouble(item.get("lr"));
            features[i] = new double[]{cr};
            targets[i] = lr;
            i++;
        }
        return new SampleInfo(features, targets);
    }


    private List<Map<String, String>> loadConfig(String fileName, String nodeName) {
        YamlMapFactoryBean yamlMapFactoryBean = new YamlMapFactoryBean();
        yamlMapFactoryBean.setResources(new ClassPathResource(fileName));
        Map<String, Object> objectMap = yamlMapFactoryBean.getObject();
        List<Map<String, String>> mapList = (List<Map<String, String>>) objectMap.get(nodeName);

        return mapList;
    }
}

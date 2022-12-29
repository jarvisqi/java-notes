package org.softmax.gradle.learn.controller;

import org.softmax.gradle.learn.utils.InterpolationUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/geo", name = "RegressionController")
public class GeoJsonController {

    @RequestMapping(value = "/h")
    public String getGeoJson() {
        double[][] trainData = new double[15][3];
        trainData[0][0] = Double.parseDouble("114.5383");  //经度
        trainData[0][1] = Double.parseDouble("32.2075");  //维度
        trainData[0][2] = Double.parseDouble("0.56");     //颜色 //值

        trainData[1][0] = Double.parseDouble("112.9625");
        trainData[1][1] = Double.parseDouble("33.0347");
        trainData[1][2] = Double.parseDouble("0.21");

        trainData[2][0] = Double.parseDouble("116.1508");
        trainData[2][1] = Double.parseDouble("34.2831");
        trainData[2][2] = Double.parseDouble("0.74");

        trainData[3][0] = Double.parseDouble("115.3156");
        trainData[3][1] = Double.parseDouble("34.4867");
        trainData[3][2] = Double.parseDouble("0.09");

        trainData[4][0] = Double.parseDouble("115.4933");
        trainData[4][1] = Double.parseDouble("33.8817");
        trainData[4][2] = Double.parseDouble("0.16");

        trainData[5][0] = Double.parseDouble("115.2414");
        trainData[5][1] = Double.parseDouble("36.0581");
        trainData[5][2] = Double.parseDouble("0.28");

        trainData[6][0] = Double.parseDouble("114.9753");
        trainData[6][1] = Double.parseDouble("32.7369");
        trainData[6][2] = Double.parseDouble("0.53");

        trainData[7][0] = Double.parseDouble("115.8619");
        trainData[7][1] = Double.parseDouble("35.9842");
        trainData[7][2] = Double.parseDouble("0.37");

        trainData[8][0] = Double.parseDouble("114.6317");
        trainData[8][1] = Double.parseDouble("33.6117");
        trainData[8][2] = Double.parseDouble("0.71");

        trainData[9][0] = Double.parseDouble("112.3717");
        trainData[9][1] = Double.parseDouble("32.5825");
        trainData[9][2] = Double.parseDouble("0.23");

        trainData[10][0] = Double.parseDouble("114.4519");
        trainData[10][1] = Double.parseDouble("35.5233");
        trainData[10][2] = Double.parseDouble("0.18");

        trainData[11][0] = Double.parseDouble("115.4236");
        trainData[11][1] = Double.parseDouble("32.4656");
        trainData[11][2] = Double.parseDouble("0.04");

        trainData[12][0] = Double.parseDouble("113.9131");
        trainData[12][1] = Double.parseDouble("32.9403");
        trainData[12][2] = Double.parseDouble("0.85");

        trainData[13][0] = Double.parseDouble("113.0503");
        trainData[13][1] = Double.parseDouble("34.4922");
        trainData[13][2] = Double.parseDouble("0.66");

        trainData[14][0] = Double.parseDouble("114.5825");
        trainData[14][1] = Double.parseDouble("33.7819");
        trainData[14][2] = Double.parseDouble("0.64");

        String shapFile = "C:\\Users\\Jarvis\\Desktop\\henan_geojson\\河南省(不含县区)\\410000.shp";
        int[] size = new int[]{600, 600};
        boolean isclip = true;
//        double[] dataInterval = new double[]{0.0001, 2, 6, 10, 20, 30, 40, 50};
        double[] dataInterval = new double[]{0.001, 0.05, 0.1, 0.5, 2, 4, 6, 10};

        String geojson = InterpolationUtils.calEquiSurface(trainData, dataInterval, size, shapFile, isclip);
        System.out.println("生成完成");
        return geojson;
    }

}

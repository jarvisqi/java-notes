package org.softmax.ms.gateway.kriging;

import org.geotools.data.crs.ForceCoordinateSystemFeatureResults;
import org.geotools.feature.FeatureCollection;
import org.geotools.filter.FilterFactoryImpl;
import org.geotools.filter.text.ecql.ECQL;
import org.geotools.geometry.jts.ReferencedEnvelope;
import org.geotools.map.FeatureLayer;
import org.geotools.map.Layer;
import org.geotools.map.MapContent;
import org.geotools.referencing.CRS;
import org.geotools.referencing.crs.DefaultGeographicCRS;
import org.geotools.renderer.lite.StreamingRenderer;
import org.geotools.styling.*;
import org.opengis.feature.simple.SimpleFeatureType;
import org.opengis.filter.Filter;
import org.opengis.filter.FilterFactory;
import org.opengis.referencing.crs.CoordinateReferenceSystem;
import org.softmax.ms.gateway.utils.InterpolationUtils;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.HashMap;
import java.util.Map;

public class KrigingTest {

    public static void main(String[] args) {

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
        FeatureCollection geojson = InterpolationUtils.calEquiSurface(trainData, dataInterval, size, shapFile, isclip);

        Map<Double, String> levPros = new HashMap<>();
        levPros.put(0.0,"#ffffff");
        levPros.put(50.9999,"#ffffff");
        levPros.put(51.00,"#ff0000");
        addShapeLayer(geojson, levPros,0.9f);
        getMapContent(params, outPath);
    }


    private static MapContent map = new MapContent();

    public static void getMapContent(Map params, String imgPath) {
        try {
            double[] bbox = (double[]) params.get("bbox");
            double x1 = bbox[0], y1 = bbox[1],
                    x2 = bbox[2], y2 = bbox[3];
            int width = Integer.parseInt(params.get("width").toString()),
                    height = Integer.parseInt(params.get("height").toString());
            // 设置输出范围
            CoordinateReferenceSystem crs = DefaultGeographicCRS.WGS84;
            ReferencedEnvelope mapArea = new ReferencedEnvelope(x1, x2, y1, y2, crs);
            // 初始化渲染器
            StreamingRenderer sr = new StreamingRenderer();
            sr.setMapContent(map);
            // 初始化输出图像
            BufferedImage bi = new BufferedImage(width, height,
                    BufferedImage.TYPE_INT_ARGB);
            Graphics g = bi.getGraphics();
            ((Graphics2D) g).setRenderingHint(RenderingHints.KEY_ANTIALIASING,
                    RenderingHints.VALUE_ANTIALIAS_ON);
            ((Graphics2D) g).setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING,
                    RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
            Rectangle rect = new Rectangle(0, 0, width, height);
            // 绘制地图
            sr.paint((Graphics2D) g, rect, mapArea);
            //将BufferedImage变量写入文件中。
            ImageIO.write(bi, "png", new File(imgPath));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 添加featureCollection等值面图层
     *
     * @param featureCollection 等值面要素几何
     * @param levelProps        色阶,结构如：{0.1:"#a5f38d"}
     * @param opacity           透明度
     */
    public static void addShapeLayer(FeatureCollection featureCollection, Map<Double, String> levelProps, float opacity) {
        try {
            // 由坐标顺序引发坐标变换，这三行由于修正数据，不加的话会出现要素漏缺。
            SimpleFeatureType simpleFeatureType = (SimpleFeatureType) featureCollection.getSchema();
            String crs = CRS.lookupIdentifier(simpleFeatureType.getCoordinateReferenceSystem(), true);
            featureCollection = new ForceCoordinateSystemFeatureResults(featureCollection,
                    CRS.decode(crs, true));
            //创建样式
            StyleFactory sf = new StyleFactoryImpl();
            FilterFactory ff = new FilterFactoryImpl();
            FeatureTypeStyle fts = sf.createFeatureTypeStyle();

            for (Map.Entry entry : levelProps.entrySet()) {
                double key = (Double) entry.getKey();
                String value = (String) entry.getValue();

                Fill fill = sf.createFill(ff.literal(value), ff.literal(opacity));
                org.geotools.styling.Stroke stroke = sf.createStroke(ff.literal("#ffffff"), ff.literal(0), ff.literal(0));
                Symbolizer symbolizer = sf.createPolygonSymbolizer(stroke, fill, "the_geom");
                Rule rule = sf.createRule();
                rule.setName("dzm_" + key);
                rule.symbolizers().add(symbolizer);
                Filter filter = ECQL.toFilter("value=" + key);
                rule.setFilter(filter);
                fts.rules().add(rule);
            }

            Style style = sf.createStyle();
            style.setName("style_dzm");
            style.featureTypeStyles().add(fts);

            Layer layer = new FeatureLayer(featureCollection, style);
            map.addLayer(layer);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

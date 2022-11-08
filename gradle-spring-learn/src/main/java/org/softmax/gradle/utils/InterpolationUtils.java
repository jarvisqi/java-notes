package org.softmax.gradle.utils;

import Jama.Matrix;
import net.sf.json.JSONObject;
import org.geotools.data.DataUtilities;
import org.geotools.data.FeatureSource;
import org.geotools.data.collection.ListFeatureCollection;
import org.geotools.data.shapefile.ShapefileDataStore;
import org.geotools.data.simple.SimpleFeatureCollection;
import org.geotools.data.simple.SimpleFeatureIterator;
import org.geotools.data.simple.SimpleFeatureSource;
import org.geotools.feature.FeatureCollection;
import org.geotools.feature.FeatureIterator;
import org.geotools.feature.SchemaException;
import org.geotools.feature.simple.SimpleFeatureBuilder;
import org.geotools.geojson.feature.FeatureJSON;
import org.locationtech.jts.geom.Geometry;
import org.opengis.feature.Feature;
import org.opengis.feature.simple.SimpleFeature;
import org.opengis.feature.simple.SimpleFeatureType;
import org.softmax.gradle.kriging.algorithm.Kriging;
import wcontour.Contour;
import wcontour.global.Border;
import wcontour.global.PointD;
import wcontour.global.PolyLine;
import wcontour.global.Polygon;
import wcontour.Interpolate;

import java.io.File;
import java.io.IOException;
import java.io.StringWriter;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class InterpolationUtils {

    /**
     * 生成等值面
     *
     * @param trainData    训练数据double[size][3],r，lon、lat、value
     * @param dataInterval 数据间隔double[0.0001,25,50,100,200,300]
     * @param size         大小，宽，高new int[]{100, 100};
     * @param boundryFile  四至.shp
     * @param isclip       是否裁剪
     * @return
     */
    public static String calEquiSurface(double[][] trainData, double[] dataInterval, int[] size, String boundryFile,
                                        boolean isclip) {
        String geojsonpogylon = "";
        try {
            double _undefData = -9999.0;
            // 多边形集合
            SimpleFeatureCollection polygonCollection = null;
            // 多边形线集合
            List<PolyLine> cPolylineList = new ArrayList<PolyLine>();
            // 多边形List
            List<Polygon> cPolygonList = new ArrayList<Polygon>();

            int width = size[0], height = size[1];
            double[] _X = new double[width + 1];
            double[] _Y = new double[height + 1];
            File file = new File(boundryFile);

            // 源shape文件
            ShapefileDataStore shpDataStore = null;
            shpDataStore = new ShapefileDataStore(file.toURI().toURL());

            // 设置编码
            Charset charset = StandardCharsets.UTF_8;
            shpDataStore.setCharset(charset);
            // 获取文件后缀名
            String typeName = shpDataStore.getTypeNames()[0];
            // SimpleFeatureSource相当于AE中的featureClass，设置属性
            SimpleFeatureSource featureSource = null;
            featureSource = shpDataStore.getFeatureSource();
            SimpleFeatureCollection fc = featureSource.getFeatures();

            // 最大最小经纬度
            double minX = fc.getBounds().getMinX();
            double minY = fc.getBounds().getMinY();
            double maxX = fc.getBounds().getMaxX();
            double maxY = fc.getBounds().getMaxY();

            Interpolate.createGridXY_Num(minX, minY, maxX, maxY, _X, _Y);
            double[][] _gridData = new double[width][height];
            // 数据间隔长度
            int nc = dataInterval.length;
            // IDW插值 （训练数据（离散数据阵列）、宽（栅格X阵）、高（栅格Y阵）、默认数（最近邻居数））
//            _gridData = Interpolate.interpolation_IDW_Neighbor(trainData, _X, _Y, 12, _undefData);
            _gridData = getKrigingData(trainData, fc);

            double[] lon = {maxX};
            double[] lat = {minY};

            int[][] S1 = new int[_gridData.length][_gridData[0].length];
            // 边界
            List<Border> _borders = Contour.tracingBorders(_gridData, _X, _Y, S1, _undefData);
            // 生成等值线（IDW插值、宽、高、数据间隔长度、数据间隔、默认值、边界、IDW长度）
            cPolylineList = Contour.tracingContourLines(_gridData, _X, _Y, nc, dataInterval, _undefData, _borders, S1);
            // 平滑
            cPolylineList = Contour.smoothLines(cPolylineList);
            cPolygonList = Contour.tracingPolygons(_gridData, cPolylineList, _borders, dataInterval);
            // 多边形Json
            geojsonpogylon = getPolygonGeoJson(cPolygonList);
            if (isclip) {
                // 读取GeoJSON字符串 返回 SimpleFeatureCollection 要素集合
                polygonCollection = GeoJSONUtil.readGeoJsonByString(geojsonpogylon);
                // 裁剪等值面
                //FeatureSource dc = clipFeatureCollection(fc, polygonCollection);

                SimpleFeatureCollection simpleFeatureCollection = clipPolygonFeatureCollection(fc, polygonCollection);
                FeatureCollection featureCollection = simpleFeatureCollection;
                FeatureJSON featureJSON = new FeatureJSON();
                StringWriter writer = new StringWriter();
                featureJSON.writeFeatureCollection(featureCollection, writer);
                geojsonpogylon = writer.toString();

                // 得到多边形GeoJson
                //geojsonpogylon = getPolygonGeoJson(dc.getFeatures());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return geojsonpogylon;
    }


    /**
     * 裁剪图形
     *
     * @param fc
     * @return
     */
    private static SimpleFeatureCollection clipPolygonFeatureCollection(FeatureCollection fc,
                                                                        SimpleFeatureCollection gs) throws SchemaException {
        SimpleFeatureCollection simpleFeatureCollection = null;
        SimpleFeatureType typePolygons = DataUtilities.createType("polygons",
                "the_geom:MultiPolygon,lvalue:double,hvalue:double");

        List<SimpleFeature> list = new ArrayList<>();
        SimpleFeatureIterator featureIterator = gs.features();
        FeatureIterator dataFeatureIterator = fc.features();

        while (dataFeatureIterator.hasNext()) {
            SimpleFeature dataFeature = (SimpleFeature) dataFeatureIterator.next();
            Geometry dataGeometry = (Geometry) dataFeature.getDefaultGeometry();
            featureIterator = gs.features();
            while (featureIterator.hasNext()) {
                SimpleFeature contourFeature = featureIterator.next();
                Geometry contourGeometry = (Geometry) contourFeature.getDefaultGeometry();
                Double lv = (Double) contourFeature.getProperty("lvalue").getValue();
                Double hv = (Double) contourFeature.getProperty("hvalue").getValue();
                if (dataGeometry.getGeometryType().equals("MultiPolygon")) {
                    for (int i = 0; i < dataGeometry.getNumGeometries(); i++) {
                        Geometry geom = dataGeometry.getGeometryN(i);
                        if (geom.intersects(contourGeometry)) {
                            Geometry geo = geom.intersection(contourGeometry);
                            SimpleFeatureBuilder featureBuilder = new SimpleFeatureBuilder(typePolygons);
                            featureBuilder.add(geo);
                            featureBuilder.add(lv);
                            featureBuilder.add(hv);
                            SimpleFeature feature = featureBuilder.buildFeature(null);

                            list.add(feature);
                        }
                    }
                } else {
                    if (dataGeometry.intersects(contourGeometry)) {
                        Geometry geo = dataGeometry.intersection(contourGeometry);
                        SimpleFeatureBuilder featureBuilder = new SimpleFeatureBuilder(typePolygons);
                        featureBuilder.add(geo);
                        featureBuilder.add(lv);
                        featureBuilder.add(hv);
                        SimpleFeature feature = featureBuilder.buildFeature(null);
                        list.add(feature);

                    }
                }
            }
        }
        featureIterator.close();
        dataFeatureIterator.close();
        simpleFeatureCollection = new ListFeatureCollection(typePolygons, list);
        return simpleFeatureCollection;
    }


    /**
     * 生成等值线
     *
     * @param trainData    训练数据
     * @param dataInterval 数据间隔
     * @param size         大小，宽，高
     * @param boundryFile  四至
     * @param isclip       是否裁剪
     * @return
     */
    public static String calEquiSurfaceLine(double[][] trainData, double[] dataInterval, int[] size, String boundryFile,
                                            boolean isclip) {
        String geojsonline = "";
        try {
            double _undefData = -9999.0;
            SimpleFeatureCollection polylineCollection = null;
            List<PolyLine> cPolylineList = new ArrayList<PolyLine>();
            List<Polygon> cPolygonList = new ArrayList<Polygon>();

            int width = size[0],
                    height = size[1];
            double[] _X = new double[width];
            double[] _Y = new double[height];

            File file = new File(boundryFile);
            ShapefileDataStore shpDataStore = null;

            shpDataStore = new ShapefileDataStore(file.toURL());
            //设置编码
            Charset charset = Charset.forName("GBK");
            shpDataStore.setCharset(charset);
            String typeName = shpDataStore.getTypeNames()[0];
            SimpleFeatureSource featureSource = null;
            featureSource = shpDataStore.getFeatureSource(typeName);
            SimpleFeatureCollection fc = featureSource.getFeatures();

            double minX = fc.getBounds().getMinX();
            double minY = fc.getBounds().getMinY();
            double maxX = fc.getBounds().getMaxX();
            double maxY = fc.getBounds().getMaxY();

            Interpolate.createGridXY_Num(minX, minY, maxX, maxY, _X, _Y);
            double[][] _gridData = new double[width][height];

            int nc = dataInterval.length;

            // IDW插值
            _gridData = Interpolate.interpolation_IDW_Neighbor(trainData, _X, _Y, 12, _undefData);

            int[][] S1 = new int[_gridData.length][_gridData[0].length];
            /**
             * double[][] S0,
             * double[] X,
             * double[] Y,
             * int[][] S1,
             * double undefData
             */
            List<Border> _borders = Contour.tracingBorders(_gridData, _X, _Y, S1, _undefData);

            /**
             * double[][] S0,
             * double[] X,
             * double[] Y,
             * int nc,
             * double[] contour,
             * double undefData,
             * List<Border> borders,
             * int[][] S1
             */

            // 生成等值线
            cPolylineList = Contour.tracingContourLines(_gridData, _X, _Y, nc, dataInterval, _undefData, _borders, S1);

            // 平滑
            cPolylineList = Contour.smoothLines(cPolylineList);
            geojsonline = getPolylineGeoJson(cPolylineList);

            if (isclip) {
                polylineCollection = GeoJSONUtil.readGeoJsonByString(geojsonline);
                FeatureSource dc = clipFeatureLineCollection(fc, polylineCollection);
                geojsonline = getPolylineGeoJson(dc.getFeatures());
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return geojsonline;
    }

    // 等值面
    private static String getPolygonGeoJson(List<Polygon> cPolygonList) {
        StringBuilder geo = new StringBuilder();
        String geometry = " { \"type\":\"Feature\",\"geometry\":";
        String properties = ",\"properties\":{ \"hvalue\":";
        String head = "{\"type\": \"FeatureCollection\"," + "\"features\": [";
        String end = "  ] }";
        if (cPolygonList == null || cPolygonList.size() == 0) {
            return null;
        }
        try {
            for (Polygon pPolygon : cPolygonList) {
                List<Object> ptsTotal = new ArrayList<Object>();
                List<Object> pts = new ArrayList<Object>();
                PolyLine pline = pPolygon.OutLine;
                for (PointD ptD : pline.PointList) {
                    List<Double> pt = new ArrayList<Double>();
                    pt.add(ptD.X);
                    pt.add(ptD.Y);
                    pts.add(pt);
                }
                ptsTotal.add(pts);
                if (pPolygon.HasHoles()) {
                    for (PolyLine cptLine : pPolygon.HoleLines) {
                        List<Object> cpts = new ArrayList<Object>();
                        for (PointD ccptD : cptLine.PointList) {
                            List<Double> pt = new ArrayList<Double>();
                            pt.add(ccptD.X);
                            pt.add(ccptD.Y);
                            cpts.add(pt);
                        }
                        if (cpts.size() > 0) {
                            ptsTotal.add(cpts);
                        }
                    }
                }

                JSONObject js = new JSONObject();
                js.put("type", "Polygon");
                js.put("coordinates", ptsTotal);
                double hv = pPolygon.HighValue;
                double lv = pPolygon.LowValue;
                if (hv == lv) {
                    if (pPolygon.IsClockWise) {
                        if (!pPolygon.IsHighCenter) {
                            hv = hv - 0.1;
                            lv = lv - 0.1;
                        }
                    } else {
                        if (!pPolygon.IsHighCenter) {
                            hv = hv - 0.1;
                            lv = lv - 0.1;
                        }
                    }
                } else {
                    if (!pPolygon.IsClockWise) {
                        lv = lv + 0.1;
                    } else {
                        if (pPolygon.IsHighCenter) {
                            hv = hv - 0.1;
                        }
                    }
                }
                geo.insert(0, geometry + js + properties + hv + ", \"lvalue\":" + lv + "} }" + ",");
            }
            if (geo.toString().contains(",")) {
                geo = new StringBuilder(geo.substring(0, geo.lastIndexOf(",")));
            }
            geo = new StringBuilder(head + geo + end);
        } catch (Exception e) {
            e.printStackTrace();
            return geo.toString();
        }
        return geo.toString();
    }

    private static String getPolygonGeoJson(FeatureCollection fc) {
        FeatureJSON fjson = new FeatureJSON();
        StringBuffer sb = new StringBuffer();
        try {
            sb.append("{\"type\": \"FeatureCollection\",\"features\": ");
            FeatureIterator itertor = fc.features();
            List<String> list = new ArrayList<String>();
            while (itertor.hasNext()) {
                SimpleFeature feature = (SimpleFeature) itertor.next();
                // 用流写
                StringWriter writer = new StringWriter();
                fjson.writeFeature(feature, writer);
                list.add(writer.toString());
            }
            itertor.close();
            sb.append(list.toString());
            sb.append("}");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return sb.toString();
    }

    // 裁剪等值面
    private static FeatureSource clipFeatureCollection(FeatureCollection fc, SimpleFeatureCollection gs) {
        FeatureSource cs = null;
        try {
            List<Map<String, Object>> values = new ArrayList<Map<String, Object>>();
            // 多边形
            FeatureIterator contourFeatureIterator = gs.features();
            // 数据
            FeatureIterator dataFeatureIterator = fc.features();
            // 迭代读取数据记录
            while (dataFeatureIterator.hasNext()) {
                // 数据下一条数据
                Feature dataFeature = dataFeatureIterator.next();
                // 数据"the_geom"属性的值
                Geometry dataGeometry = (Geometry) dataFeature.getProperty("the_geom").getValue();

                while (contourFeatureIterator.hasNext()) {
                    Feature contourFeature = contourFeatureIterator.next();
                    Geometry contourGeometry = (Geometry) contourFeature.getProperty("geometry").getValue();
                    double lv = (Double) contourFeature.getProperty("lvalue").getValue();
                    double hv = (Double) contourFeature.getProperty("hvalue").getValue();
                    if (dataGeometry.intersects(contourGeometry)) {
                        Geometry geo = dataGeometry.intersection(contourGeometry);
                        Map<String, Object> map = new HashMap<String, Object>();
                        map.put("the_geom", geo);
                        map.put("lvalue", lv);
                        map.put("hvalue", hv);
                        values.add(map);
                    }
                }
            }
            contourFeatureIterator.close();
            dataFeatureIterator.close();
            SimpleFeatureCollection sc = FeaureUtil.creatSimpleFeatureByFeilds("polygons",
                    "crs:4326,the_geom:MultiPolygon,lvalue:double,hvalue:double", values);
            cs = FeaureUtil.creatFeatureSourceByCollection(sc);

        } catch (Exception e) {
            e.printStackTrace();
            return cs;
        }

        return cs;
    }

    public static String getPolylineGeoJson(FeatureCollection fc) {
        FeatureJSON fjson = new FeatureJSON();
        StringBuffer sb = new StringBuffer();
        try {
            sb.append("{\"type\": \"FeatureCollection\",\"features\": ");
            FeatureIterator itertor = fc.features();
            List<String> list = new ArrayList<String>();
            while (itertor.hasNext()) {
                SimpleFeature feature = (SimpleFeature) itertor.next();
                StringWriter writer = new StringWriter();
                fjson.writeFeature(feature, writer);
                list.add(writer.toString());
            }
            itertor.close();
            sb.append(list.toString());
            sb.append("}");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return sb.toString();
    }

    public static String getPolylineGeoJson(List<PolyLine> cPolylineList) {
        String geo = null;
        String geometry = " { \"type\":\"Feature\",\"geometry\":";
        String properties = ",\"properties\":{ \"value\":";

        String head = "{\"type\": \"FeatureCollection\"," + "\"features\": [";
        String end = "  ] }";
        if (cPolylineList == null || cPolylineList.size() == 0) {
            return null;
        }
        try {
            for (PolyLine pPolyline : cPolylineList) {
                List<Object> ptsTotal = new ArrayList<Object>();

                for (PointD ptD : pPolyline.PointList) {
                    List<Double> pt = new ArrayList<Double>();
                    pt.add(ptD.X);
                    pt.add(ptD.Y);
                    ptsTotal.add(pt);
                }

                JSONObject js = new JSONObject();
                js.put("type", "LineString");
                js.put("coordinates", ptsTotal);

                geo = geometry + js.toString() + properties + pPolyline.Value + "} }" + "," + geo;
            }
            if (geo.contains(",")) {
                geo = geo.substring(0, geo.lastIndexOf(","));
            }

            geo = head + geo + end;
        } catch (Exception e) {
            e.printStackTrace();
            return geo;
        }
        return geo;
    }

    public static FeatureSource clipFeatureLineCollection(FeatureCollection fc,
                                                          SimpleFeatureCollection gs) {
        FeatureSource cs = null;
        try {
            List<Map<String, Object>> values = new ArrayList<Map<String, Object>>();
            FeatureIterator contourFeatureIterator = gs.features();
            FeatureIterator dataFeatureIterator = fc.features();
            while (dataFeatureIterator.hasNext()) {
                Feature dataFeature = dataFeatureIterator.next();
                Geometry dataGeometry = (Geometry) dataFeature.getProperty(
                        "the_geom").getValue();
                while (contourFeatureIterator.hasNext()) {
                    Feature contourFeature = contourFeatureIterator.next();
                    Geometry contourGeometry = (Geometry) contourFeature
                            .getProperty("geometry").getValue();
                    double v = (Double) contourFeature.getProperty("value")
                            .getValue();
                    if (dataGeometry.intersects(contourGeometry)) {
                        Geometry geo = dataGeometry
                                .intersection(contourGeometry);
                        Map<String, Object> map = new HashMap<String, Object>();
                        map.put("the_geom", geo);
                        map.put("value", v);
                        values.add(map);
                    }
                }
            }
            contourFeatureIterator.close();
            dataFeatureIterator.close();
            SimpleFeatureCollection sc = FeaureUtil
                    .creatSimpleFeatureByFeilds(
                            "polygons",
                            "crs:4326,the_geom:LineString,value:double",
                            values);
            cs = FeaureUtil.creatFeatureSourceByCollection(sc);
        } catch (Exception e) {
            e.printStackTrace();
            return cs;
        }
        return cs;
    }


    private static double[][] getKrigingData(double[][] trainData, SimpleFeatureCollection fc) {
        double[] targetValues = new double[trainData.length];
        double[] xList = new double[trainData.length];
        double[] yList = new double[trainData.length];
        for (int i = 0; i < trainData.length; i++) {
            xList[i] = trainData[i][0];
            yList[i] = trainData[i][1];
            targetValues[i] = trainData[i][2];
        }
        Kriging kriging = new Kriging(Kriging.GAUSSIAN_MODEL, 0, 100);
        kriging.train(targetValues, xList, yList);


        double bottom = fc.getBounds().getMinY();
        double left = fc.getBounds().getMinX();
        double top = fc.getBounds().getMaxY();
        double right = fc.getBounds().getMaxX();

        double[] bottomLeft = {left, bottom};
        double[] topLeft = {left, top};
        double[] topRight = {right, top};
        double[] bottomRight = {right, bottom};

        double[][][] polygons = {{bottomLeft, topLeft, topRight, bottomRight}};
        double xWidth = Math.abs(right - left) / 600;
        double yWidth = Math.abs(top - bottom) / 600;

        Kriging.Grid grid = kriging.grid(polygons, xWidth, yWidth);
        return grid.A;
    }


}

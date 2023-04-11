package org.softmax.ms.user.meteo;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.meteoinfo.common.Extent;
import org.meteoinfo.data.GridData;
import org.meteoinfo.data.GridDataSetting;
import org.meteoinfo.data.StationData;
import org.meteoinfo.geo.analysis.InterpolationMethods;
import org.meteoinfo.geo.analysis.InterpolationSetting;
import org.meteoinfo.geo.layer.VectorLayer;
import org.meteoinfo.geo.layout.LayoutLegend;
import org.meteoinfo.geo.layout.LegendStyles;
import org.meteoinfo.geo.layout.MapLayout;
import org.meteoinfo.geo.legend.LegendManage;
import org.meteoinfo.geo.mapdata.MapDataManage;
import org.meteoinfo.geo.mapview.MapView;
import org.meteoinfo.geo.meteodata.DrawMeteoData;
import org.meteoinfo.geo.util.GeoMathUtil;
import org.meteoinfo.geometry.legend.LegendScheme;
import org.meteoinfo.geometry.legend.LegendType;
import org.meteoinfo.geometry.legend.PolygonBreak;
import org.meteoinfo.geometry.shape.ShapeTypes;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.*;
import java.time.Duration;
import java.time.Instant;
import java.util.LinkedHashMap;
import java.util.List;


public class MeteoSample {

    public static void main(String[] args) throws Exception {
        StationData stationData = readJsonData();

//        StationData stationData = new StationData();
//        stationData.addData("O8003", 113.98, 32.93, 1.9);
//        stationData.addData("O7756", 113.42, 33.12, 2);
//        stationData.addData("O2651", 112.22, 35.13, 2.3);
        Instant now = Instant.now();
        //读取地图图层
        VectorLayer altMap = MapDataManage.readMapFile_ShapeFile("E:\\henan_geojson\\河南省(不含县区)\\410000.shp");
        //创建网格设置参数
        GridDataSetting gridDataSetting = new GridDataSetting();
        //设定数据区域
        gridDataSetting.dataExtent = altMap.getExtent();
        //设定格点数
        gridDataSetting.xNum = 200;
        gridDataSetting.yNum = 200;
        //创建插值设置
        InterpolationSetting interpolationSetting = new InterpolationSetting();
        //设定格点配置
        interpolationSetting.setGridDataSetting(gridDataSetting);
        //设定插值方法
        interpolationSetting.setInterpolationMethod(InterpolationMethods.KRIGING);
        //设定搜索半径
        interpolationSetting.setRadius(10);
        //设置最小点数
        interpolationSetting.setMinPointNum(1);

        //插值到格点
        GridData gridData = GeoMathUtil.interpolateData(stationData, interpolationSetting);
        Instant end = Instant.now();
        System.out.println("插值完成：花费" + Duration.between(now, end).toMillis());
        //GridData gridData = stationData.interpolateData(interpolationSetting);
        // LegendScheme als = LgsUtil.readFromLgs("D:\\apache-tomcat-8.0.50\\alt色阶\\color\\ECMWF_HR\\TMP.lgs");
        //设置数据、形状、颜色形式
        LegendScheme legendScheme = LegendManage.createLegendSchemeFromGridData(gridData, LegendType.UNIQUE_VALUE, ShapeTypes.POLYGON);
        //绘制图层
        VectorLayer layer = DrawMeteoData.createShadedLayer(gridData, legendScheme, "", "", true);

        //创建视图
        MapView view = new MapView();
        PolygonBreak pb = (PolygonBreak) altMap.getLegendScheme().getLegendBreak(0);
        pb.setDrawFill(false);
        pb.setOutlineColor(Color.GRAY);

        layer = layer.clip(altMap);

        //叠加图层
        view.addLayer(layer);
        view.addLayer(altMap);

        /**
         * 通用方法,可以抽成工具类
         */
        MapLayout layout = new MapLayout();
        //去除图形边框
        layout.getActiveMapFrame().setDrawNeatLine(false);
        //抗锯齿
        layout.getActiveMapFrame().setMapView(view);
        view.setAntiAlias(true);
        layout.setAntiAlias(true);

        //区域边界
        Extent extent = view.getExtent();
        //设置矩形的宽和高
        Rectangle bounds = new Rectangle(800, (int) (800 * 1D / extent.getWidth() * extent.getHeight()));
        //设置地图边框
        layout.setPageBounds(new Rectangle(0, 0, bounds.width, bounds.height));
        //设置页面边框
        layout.getActiveMapFrame().setLayoutBounds(new Rectangle(0, 0, bounds.width, bounds.height));
        layout.getActiveMapFrame().setMapView(view);
        //设置网格间隔值
        layout.getActiveMapFrame().setGridXDelt(0.5);
        layout.getActiveMapFrame().setGridYDelt(0.5);
        //设置图例
        LayoutLegend legend = layout.addLegend(bounds.x + bounds.width + 15, 0);
        legend.setLegendStyle(LegendStyles.NORMAL);
        legend.setTop(bounds.y + (bounds.height - legend.getHeight()) / 2);
        legend.setLegendLayer(layer);

        String imgPath = "D:\\out.png";
        layout.exportToPicture(imgPath);

//        transparent(imgPath);

    }

    public static StationData readJsonData() throws IOException {
        String filePath = "C:\\Users\\Jarvis\\Desktop\\result.json";
        Reader reader = new InputStreamReader(new FileInputStream(filePath));
        int ch = 0;
        StringBuffer strBuff = new StringBuffer();
        while ((ch = reader.read()) != -1) {
            strBuff.append((char) ch);
        }
        reader.close();
        String jsonStr = strBuff.toString();
        ObjectMapper mapper = new ObjectMapper();
        List<LinkedHashMap> dataBeans = mapper.readValue(jsonStr, List.class);
        StationData stationData = new StationData();
        for (LinkedHashMap map : dataBeans) {
            stationData.addData(map.get("stid").toString(), Double.parseDouble(map.get("lon").toString()),
                    Double.parseDouble(map.get("lat").toString()), Double.parseDouble(map.get("val").toString()));
        }
        return stationData;
    }

    /**
     * //透明处理
     * //读取图片
     */
    public static void transparent(String imgPath) throws IOException {

        BufferedImage bi = ImageIO.read(new File(imgPath));
        //类型转换
        BufferedImage img = new BufferedImage(bi.getWidth(), bi.getHeight(), BufferedImage.TYPE_INT_ARGB);
        Graphics2D g = (Graphics2D) img.getGraphics();
        g.drawImage(bi, null, 0, 0);
        //透明处理
        int alpha = 0;
        for (int i = img.getMinY(); i < img.getHeight(); i++) {
            for (int j = img.getMinX(); j < img.getWidth(); j++) {
                int rgb = img.getRGB(j, i);
                //透明部分不需要处理
                if (rgb < 0) {
                    int R = (rgb & 0xff0000) >> 16;
                    int G = (rgb & 0xff00) >> 8;
                    int B = (rgb & 0xff);
                    //将白色剔除
                    Color color = Color.white;
                    if (color.getRed() == R && color.getGreen() == G && color.getBlue() == B) {
                        alpha = 0;
                    } else {
                        alpha = 255;
                    }
                    rgb = (alpha << 24) | (rgb & 0x00ffffff);
                    img.setRGB(j, i, rgb);
                }
            }
        }
        //释放资源
        g.dispose();
        ImageIO.write(img, "png", new File(imgPath));
    }
}

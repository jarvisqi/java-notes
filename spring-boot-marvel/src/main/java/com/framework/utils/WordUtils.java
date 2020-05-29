package com.framework.utils;

import org.apache.commons.lang3.StringUtils;
import org.apache.poi.hwpf.HWPFDocument;
import org.apache.poi.hwpf.usermodel.*;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.jeecgframework.poi.word.WordExportUtil;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

/**
 * 根据word模板生成wrod文件 支持docx、doc
 *
 * @author Jarvis
 */
public class WordUtils {

    private static final String DOCX_SUFFIX = "docx";
    private static final String DOC_SUFFIX = "doc";

    /**
     * 向word模板中填充数据，生成新word
     * templePath 是word模板的路径，outPutPath是输出路径
     * 注意模板是doc导出的就是doc，docx导出的是docx，否则会错误，
     *
     * @param mapData    填充的数据
     * @param templePath 模板路径
     * @throws Exception
     */
    public static void generateWord(HashMap mapData, String templePath, String outPutPath) throws Exception {
        //因为空格无法输出，过滤一下空格 用-代替
        Iterator<Map.Entry<String, String>> iter = mapData.entrySet().iterator();
        while (iter.hasNext()) {
            Map.Entry entry = iter.next();
            Object key = entry.getKey();
            Object val = entry.getValue();
            if ("".equals(val)) {
                //空格无法输出
                mapData.put(key, "-");
            }
        }

        //先创建文件
        File file = new File(outPutPath);
        if (file.exists()) {
            file.delete();
        }
        file.getParentFile().mkdirs();
        file.createNewFile();

        try {
            //获取文件后缀
            String suffix = getSuffix(templePath);
            if (suffix.equalsIgnoreCase(DOCX_SUFFIX)) {
                XWPFDocument doc = WordExportUtil.exportWord07(templePath, mapData);
                FileOutputStream fos = new FileOutputStream(file);
                doc.write(fos);
                fos.close();
            } else if (suffix.equalsIgnoreCase(DOC_SUFFIX)) {
                HWPFDocument hwpfDocument = new HWPFDocument(new FileInputStream(templePath));
                Range range = hwpfDocument.getRange();
                getRange(range, mapData);
                FileOutputStream stream = new FileOutputStream(file);
                hwpfDocument.write(stream);
                stream.flush();
                stream.close();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static void getRange(Range range, Map<String, Object> map) {
        TableIterator tableIter = new TableIterator(range);
        Table table;
        TableRow row;
        TableCell cell;
        while (tableIter.hasNext()) {
            table = tableIter.next();
            int rowNum = table.numRows();
            for (int j = 0; j < rowNum; j++) {
                row = table.getRow(j);
                int cellNum = row.numCells();
                for (int k = 0; k < cellNum; k++) {
                    cell = row.getCell(k);
                    String container = cell.text().trim();
                    if (container.contains("{{") && container.contains("}}")) {
                        String s = parseString(container, map);
                        cell.replaceText(container, s);
                    }
                }
            }
        }
    }

    /**
     * 填充数据
     *
     * @param container
     * @param map
     * @return
     */
    private static String parseString(String container, Map<String, Object> map) {
        if (container.contains("{{") && container.contains("}}")) {
            String code = container.substring(container.indexOf("{{") + 2, container.indexOf("}}"));
            if (map.containsKey(code)) {
                String s = StringUtils.replace(container, "{{" + code + "}}", map.get(code).toString());
                return parseString(s, map);
            }
        }
        return container;
    }


    /**
     * 获取文件后缀名
     *
     * @param path
     * @return
     */
    public static String getSuffix(String path) {
        return path.substring(path.lastIndexOf(".") + 1);
    }
}

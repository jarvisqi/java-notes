package com.framework.common;


import com.fasterxml.jackson.databind.ObjectMapper;

import com.fasterxml.jackson.core.type.TypeReference;

import java.io.*;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

/**
 * @author Jarvis
 */
public class IOUtil {

    /**
     * 将输入流InputStream转换为String
     *
     * @param is
     * @return
     */
    public static String streamToString(InputStream is) throws UnsupportedEncodingException {
        BufferedReader reader = new BufferedReader(new InputStreamReader(is, "UTF-8"));
        StringBuilder sb = new StringBuilder();
        String line;
        try {
            while ((line = reader.readLine()) != null) {
                sb.append(line + "\n");
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                is.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        return sb.toString();
    }

    /**
     * json 转换Map
     *
     * @param strJson
     * @param <K>
     * @param <V>
     * @return
     */
    public static <K, V> Map<K, V> jsonToMap(String strJson, boolean trim) {
        Map<K, V> map = null;
        ObjectMapper mapper = new ObjectMapper();
        try {
            map = (Map<K, V>) mapper.readValue(strJson, new TypeReference<HashMap<String, String>>() {
            });
            if (trim) {
                map = mapTrim(map);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return map;
    }

    private static <K, V> Map<K, V> mapTrim(Map<K, V> map) {
        Map<K, V> mapResult = new HashMap<>(8);
        for (Map.Entry<K, V> entry : map.entrySet()) {
            if (map.get(entry.getKey()) != null) {
                V v = map.get(entry.getKey());
                if (v instanceof String) {
                    V trim = (V) v.toString().trim();
                    mapResult.put(entry.getKey(), trim);
                } else {
                    mapResult.put(entry.getKey(), v);
                }
            }
        }
        return mapResult;
    }
}

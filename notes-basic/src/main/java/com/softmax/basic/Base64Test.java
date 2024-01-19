package com.softmax.basic;

import cn.hutool.json.JSONUtil;
import com.alibaba.fastjson2.JSON;

import java.io.UnsupportedEncodingException;
import java.nio.charset.StandardCharsets;
import java.time.Duration;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;

/**
 * @author : Jarvis
 * @date : Created in 2018/5/26
 */
public class Base64Test {

    public static void main(String[] args) throws UnsupportedEncodingException {
//        base64Test();

        JsonInfo info = new JsonInfo(1, 2, "title", "name", "desc");
        List<String> list = new ArrayList<>();
        for (int i = 0; i < 1000; i++) {
            list.add("AAAAAAAA");
        }
        info.setObjs(list);

        String jsonStr = JSONUtil.toJsonStr(info);
        System.out.println("len:" + jsonStr.length());

        Instant now = Instant.now();
        for (int i = 0; i < 10000; i++) {
            testJson(jsonStr);
        }
        Instant end = Instant.now();
        long timeElapsed = Duration.between(now, end).toMillis();
        System.out.println(timeElapsed);

        Instant now1 = Instant.now();
        for (int i = 0; i < 10000; i++) {
            testFastJson(jsonStr);
        }
        Instant end1 = Instant.now();
        long timeElapsed1 = Duration.between(now1, end1).toMillis();
        System.out.println(timeElapsed1);
    }

    private static void base64Test() throws UnsupportedEncodingException {
        String base64encodedString = Base64.getEncoder().encodeToString("Java Test".getBytes(StandardCharsets.UTF_8));
        System.out.println("基本编码：" + base64encodedString);

        byte[] base64decodedBytes = Base64.getDecoder().decode(base64encodedString);
        System.out.println("原始字符串：" + new String(base64decodedBytes, StandardCharsets.UTF_8));

        String base64UrlEncoded = Base64.getUrlEncoder().encodeToString("Java?test&time=20192023928".getBytes(StandardCharsets.UTF_8));
        System.out.println("URL编码：" + base64UrlEncoded);

        byte[] base64UrlBytes = Base64.getUrlDecoder().decode(base64UrlEncoded);
        System.out.println("URL解码：" + new String(base64UrlBytes, StandardCharsets.UTF_8));
    }

    private static void testJson(String jsonStr) {
        JSONUtil.toBean(jsonStr, JsonInfo.class);
    }

    private static void testFastJson(String jsonStr) {
        JSON.parseObject(jsonStr, JsonInfo.class);
    }


    static class JsonInfo {
        private Integer price;
        private Integer rate;
        private String title;
        private String name;
        private String desc;

        public List<String> getObjs() {
            return objs;
        }

        public void setObjs(List<String> objs) {
            this.objs = objs;
        }

        private List<String> objs;

        public JsonInfo(Integer price, Integer rate, String title, String name, String desc) {
            this.price = price;
            this.rate = rate;
            this.title = title;
            this.name = name;
            this.desc = desc;
        }

        public Integer getPrice() {
            return price;
        }

        public void setPrice(Integer price) {
            this.price = price;
        }

        public Integer getRate() {
            return rate;
        }

        public void setRate(Integer rate) {
            this.rate = rate;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getDesc() {
            return desc;
        }

        public void setDesc(String desc) {
            this.desc = desc;
        }
    }

}

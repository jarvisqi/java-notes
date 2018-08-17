package com.basecode;

import java.io.UnsupportedEncodingException;
import java.time.LocalDateTime;
import java.util.Base64;

/**
 * @author : Jarvis
 * @date : Created in 2018/5/26
 */
public class Base64Test {

    public static void main(String[] args) throws UnsupportedEncodingException {
        base64Test();
    }

    private static void base64Test() throws UnsupportedEncodingException {
        String base64encodedString = Base64.getEncoder().encodeToString("Java Test".getBytes("utf-8"));
        System.out.println("基本编码：" + base64encodedString);

        byte[] base64decodedBytes = Base64.getDecoder().decode(base64encodedString);
        System.out.println("原始字符串：" + new String(base64decodedBytes, "utf-8"));

        String base64UrlEncoded = Base64.getUrlEncoder().encodeToString("Java?test&time=20192023928".getBytes("utf-8"));
        System.out.println("URL编码：" + base64UrlEncoded);

        byte[] base64UrlBytes = Base64.getUrlDecoder().decode(base64UrlEncoded);
        System.out.println("URL解码：" + new String(base64UrlBytes, "utf-8"));
    }


}

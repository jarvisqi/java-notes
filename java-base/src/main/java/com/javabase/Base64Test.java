package com.javabase;

import java.io.UnsupportedEncodingException;
import java.time.LocalDateTime;
import java.util.Base64;
import java.util.UUID;

/**
 * @author : Jarvis
 * @date : Created in 2018/5/26
 */
public class Base64Test {

    public static void main(String[] args) {
        stringTest();

    }

    private void base64Test() throws UnsupportedEncodingException {
        String base64encodedString = Base64.getEncoder().encodeToString("Java Test".getBytes("utf-8"));
        System.out.println("基本编码：" + base64encodedString);

        byte[] base64decodedBytes = Base64.getDecoder().decode(base64encodedString);
        System.out.println("原始字符串：" + new String(base64decodedBytes, "utf-8"));

        String base64UrlEncoded = Base64.getUrlEncoder().encodeToString("Java?test&time=20192023928".getBytes("utf-8"));
        System.out.println("URL编码：" + base64UrlEncoded);

        byte[] base64UrlBytes = Base64.getUrlDecoder().decode(base64UrlEncoded);
        System.out.println("URL解码：" + new String(base64UrlBytes, "utf-8"));
    }

    private static void stringTest() {
        String str0 = "123";
        String str1 = "123";

        System.out.println(str0 == str1);

        String str2 = new String("234");
        String str3 = new String("234");
        //Java虚拟机的解释器每遇到一个new关键字，都会在堆内存中开辟一块内存来存放一个String对象,不同的引用 false
        System.out.println(str2 == str3);
        //
        System.out.println(str2.equals(str3));
        //昨天的当前时刻
        LocalDateTime today = LocalDateTime.now();
        var yesterday = today.minusDays(1);
        System.out.println(yesterday);

        var originStr = "ABCD";
        var newStr=reverse(originStr);
        System.out.println(newStr);

    }

    /**
     * 字符串反转
     *
     * @param originStr
     */
    private static String reverse(String originStr) {
        if (originStr == null || originStr.length() <= 1) {
            return originStr;
        }
        return reverse(originStr.substring(1)) + originStr.charAt(0);
    }

}

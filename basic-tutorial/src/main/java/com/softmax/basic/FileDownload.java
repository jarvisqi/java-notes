package com.softmax.basic;

import cn.hutool.core.io.FileUtil;
import cn.hutool.http.HttpUtil;

import java.io.*;

public class FileDownload {


    public static void main(String[] args) throws IOException {
//        download();
        url();
    }

    public static void download() throws IOException {
        String fineName = "D:/100001.pdf";
        String fullUrl = "https://pdf.dfcfw.com/pdf/H3_AP202205071564055035_1.pdf?1651920741000.pdf";
        HttpUtil.downloadFile(fullUrl, FileUtil.file(fineName));
        System.out.println("finished");

    }

    public static void url(){
        //匹配ip的正则
        String reg = "http?://(\\d+\\.\\d+\\.\\d+\\.\\d+):(\\d+)";
        String str = "http://127.0.0.1:10616/cus/assemble/openapi/v1/signlepoint/getsingleAuthUrl?v=%7B%22clientId%22%3A%22econtract%22%2C%22userAccount%22%3A%2202068755f0624a82%22%2C%22page%22%3A%22singleAuth%22%7D";
        String baidu = "https://ca-minlist.minmetals.com.cn/ecapi";
        String endStr = str.replaceAll(reg, baidu);
        System.out.println("替换前：" + str);
        System.out.println("替换后：" + endStr);

    }
}

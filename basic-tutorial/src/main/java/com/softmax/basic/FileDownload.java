package com.softmax.basic;

import cn.hutool.core.io.FileUtil;
import cn.hutool.http.HttpUtil;

import java.io.*;

public class FileDownload {


    public static void main(String[] args) throws IOException {
        download();
    }

    public static void download() throws IOException {
        String fineName = "D:/100001.pdf";
        String fullUrl = "https://pdf.dfcfw.com/pdf/H3_AP202205071564055035_1.pdf?1651920741000.pdf";
        HttpUtil.downloadFile(fullUrl, FileUtil.file(fineName));
        System.out.println("finished");

    }
}

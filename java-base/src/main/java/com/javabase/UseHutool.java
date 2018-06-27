package com.javabase;

import cn.hutool.core.date.DateUtil;

import java.util.Date;

/**
 * Hutool 工具类库使用
 *
 * @author : Jarvis
 * @date : Created in 2018/5/25
 */
public class UseHutool {

    /**
     * hutool 时间格式化
     */
    public static void dateFormat() {

        String dateStr = "2017-03-01";
        Date date = DateUtil.parse(dateStr);
        //结果 2017/03/01
        String format = DateUtil.format(date, "yyyy/MM/dd");
        //常用格式的格式化，结果：2017-03-01
        String formatDate = DateUtil.formatDate(date);
        //结果：2017-03-01 00:00:00
        String formatDateTime = DateUtil.formatDateTime(date);
        //结果：00:00:00
        String formatTime = DateUtil.formatTime(date);
        System.out.println(format);
        System.out.println(formatDate);
        System.out.println(formatDateTime);
        System.out.println(formatTime);
    }
}

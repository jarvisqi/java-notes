package com.basecode;

import cn.hutool.core.date.DateUtil;

import java.time.Clock;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;

/**
 * Hutool 工具类库使用
 *
 * @author : Jarvis
 * @date : Created in 2018/5/25
 */
public class UseHutool {

//    public static void main(String[] args) {
//        dateTest();
//    }

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

    /**
     * 时间日期操作
     */
    private static void dateTest() {
        LocalDateTime localDateTime = LocalDateTime.now();
        System.out.println(String.format("当前年月：%d-%d-%d", localDateTime.getYear(),
                localDateTime.getMonthValue(), localDateTime.getDayOfMonth()));

        System.out.println(String.format("当前时间：%d:%d:%d:%s", localDateTime.getHour(),
                localDateTime.getSecond(), localDateTime.getMinute(), Clock.systemDefaultZone().millis()));

        // 时间日期格式化
        DateTimeFormatter dtFormatter = DateTimeFormatter.ofPattern("yyyy/MM/dd");
        LocalDate localDate = LocalDate.now();
        System.out.println(localDate.format(dtFormatter));

        DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");
        LocalDateTime ldTime = LocalDateTime.now();
        System.out.println(ldTime.format(timeFormatter));
    }

    /**
     * 1,Caught Annoyance
     * 2,Caught Sneeze
     * 3,Hello World!
     *
     * @param args
     * @throws Exception
     */
    public static void main(String[] args) throws Exception {

        try {
            try {
                throw new Sneeze();
            } catch (Annoyance a) {
                System.out.println("Caught Annoyance");
                throw a;
            }
        } catch (Sneeze s) {
            System.out.println("Caught Sneeze");
            return;
        } finally {
            System.out.println("Hello World!");
        }
    }
}

class Annoyance extends Exception {
}

class Sneeze extends Annoyance {
}


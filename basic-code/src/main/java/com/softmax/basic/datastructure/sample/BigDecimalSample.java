package com.softmax.basic.datastructure.sample;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.NumberFormat;

/**
 * @author Jarvis
 */
public class BigDecimalSample {

    public static void main(String[] args) {
        //建立货币格式化引用
        NumberFormat currencyInstance = NumberFormat.getCurrencyInstance();
        //建立百分比格式化引用
        NumberFormat percentInstance = NumberFormat.getPercentInstance();
        //百分比小数点最多3位
        percentInstance.setMaximumFractionDigits(3);

        //贷款金额
        BigDecimal loanAmount = new BigDecimal("15000.48");
        //利率
        BigDecimal interestRate = new BigDecimal("0.008");
        BigDecimal multiply = loanAmount.multiply(interestRate);

        System.out.println("贷款金额:\t" + currencyInstance.format(loanAmount));
        System.out.println("利率:\t" + percentInstance.format(interestRate));
        System.out.println("利息:\t" + currencyInstance.format(interestRate));

        System.out.println(formatToNumber(new BigDecimal("3.435")));
        System.out.println(formatToNumber(new BigDecimal(0)));
        System.out.println(formatToNumber(new BigDecimal("0.00")));
        System.out.println(formatToNumber(new BigDecimal("0.001")));
        System.out.println(formatToNumber(new BigDecimal("0.006")));
        System.out.println(formatToNumber(new BigDecimal("0.206")));

    }

    /**
     * 1.0~1之间的BigDecimal小数，格式化后失去前面的0,则前面直接加上0。
     * 2.传入的参数等于0，则直接返回字符串"0.00"
     * 3.大于1的小数，直接格式化返回字符串
     */
    public static String formatToNumber(BigDecimal obj) {
        DecimalFormat df = new DecimalFormat("#.00");
        if (obj.compareTo(BigDecimal.ZERO) == 0) {
            return "0.00";
        } else if (obj.compareTo(BigDecimal.ZERO) > 0 && obj.compareTo(BigDecimal.ZERO) < 0) {
            return "0" + df.format(obj);
        } else {
            return df.format(obj);
        }
    }
}

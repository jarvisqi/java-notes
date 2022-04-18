package com.softmax.design.visit;

import java.time.LocalDate;

/**
 * @author Jarvis
 */
public class Client {
    public static void main(String[] args){
        //小黑兔奶糖，生产日期：2018-10-1，原价：￥20.00
        Candy candy = new Candy("小黑兔奶糖", LocalDate.of(2018, 10, 1), 20.00f);
        Visitor discountVisitor = new DiscountVisitor(LocalDate.of(2019, 1, 1));
        discountVisitor.visit(candy);
    }
}

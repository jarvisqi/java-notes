package com.softmax.design.visit;

import java.time.LocalDate;

/**
 * 糖果类
 */
public class Candy extends Product {
    public Candy(String name, LocalDate producedDate, float price) {
        super(name, producedDate, price);
    }
}

package com.softmax.design.visit;

import java.time.LocalDate;

/**
 * 酒类
 * @author Jarvis
 */
public class Wine extends Product {
    public Wine(String name, LocalDate producedDate, float price) {
        super(name, producedDate, price);
    }
}

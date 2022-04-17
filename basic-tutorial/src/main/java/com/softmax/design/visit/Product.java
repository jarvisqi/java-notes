package com.softmax.design.visit;

import java.time.LocalDate;

public class Product {

    /**
     * 品名
     */
    protected String name;
    /**
     * 生产日期
     */
    protected LocalDate producedDate;
    /**
     * 价格
     */
    protected float price;

    public Product(String name, LocalDate producedDate, float price) {
        this.name = name;
        this.producedDate = producedDate;
        this.price = price;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public LocalDate getProducedDate() {
        return producedDate;
    }

    public void setProducedDate(LocalDate producedDate) {
        this.producedDate = producedDate;
    }

    public float getPrice() {
        return price;
    }

    public void setPrice(float price) {
        this.price = price;
    }
}

package com.softmax.design.visit;

import java.time.LocalDate;

/**
 * 酒类
 * @author Jarvis
 */
public class Fruit extends Product {

    private float weight;

    public Fruit(String name, LocalDate producedDate, float price, float weight) {
        super(name, producedDate, price);
        this.weight = weight;
    }

    public float getWeight() {
        return weight;
    }

    public void setWeight(float weight) {
        this.weight = weight;
    }

}

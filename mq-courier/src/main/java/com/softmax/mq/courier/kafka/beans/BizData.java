package com.softmax.mq.courier.kafka.beans;

import org.springframework.stereotype.Component;

/**
 * 修改数据示例
 */
public class BizData {

    private String orderId;
    private Float price;
    private String productName;

    public BizData(String orderId, Float price, String productName) {
        this.orderId = orderId;
        this.price = price;
        this.productName = productName;
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public Float getPrice() {
        return price;
    }

    public void setPrice(Float price) {
        this.price = price;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }
}

package com.softmax.marvel.entity.enums;

import com.framework.common.BaseEnum;

public enum OrderType implements BaseEnum {

    NORMAL(1, "普通订单"),

    GROUP(2, "团购订单"),

    PROMOTION(3, "促销订单");

    Integer value;
    String description;

    OrderType(Integer value, String description) {
        this.value = value;
        this.description = description;
    }
}

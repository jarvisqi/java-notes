package com.softmax.marvel.entity.dto;

import lombok.Data;

import java.util.Date;

@Data
public class OrderDTO {

    private String orderId;

    private Float price;

    /**
     * 订单类型：（1：普通订单；2：团购订单；3：组小订单）
     */
    private Integer orderType;

    private Date orderDate;

}

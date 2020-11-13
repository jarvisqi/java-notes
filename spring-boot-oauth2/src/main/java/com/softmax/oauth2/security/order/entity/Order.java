package com.softmax.oauth2.security.order.entity;

import java.io.Serializable;
import java.math.BigDecimal;

import lombok.Data;

/**
 * t_order0
 *
 * @author
 */
@Data
public class Order implements Serializable {
    private Long sysno;

    private Long orderId;

    private BigDecimal orderAmount;

    private static final long serialVersionUID = 1L;
}
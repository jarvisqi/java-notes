package com.softmax.oauth2.security.order.service;

import com.softmax.oauth2.security.order.entity.Order;

import java.util.List;

public interface OrderService {

    void insert(Order order);

    Order getOrder(Long sysno);

    List<Order> queryOrder();
}

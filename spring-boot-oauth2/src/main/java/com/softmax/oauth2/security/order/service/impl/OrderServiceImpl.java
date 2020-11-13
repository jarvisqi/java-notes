package com.softmax.oauth2.security.order.service.impl;

import com.softmax.oauth2.security.order.dao.OrderMapper;
import com.softmax.oauth2.security.order.entity.Order;
import com.softmax.oauth2.security.order.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OrderServiceImpl implements OrderService {

    @Autowired
    private OrderMapper orderMapper;

    @Override
    public void insert(Order order) {
        orderMapper.insert(order);
    }

    @Override
    public Order getOrder(Long sysno) {
        return orderMapper.selectByPrimaryKey(sysno);
    }

    @Override
    public List<Order> queryOrder() {
        List<Order> orders = orderMapper.queryOrder();
        return orders;
    }
}

package com.softmax.marvel.service.impl;

import com.softmax.marvel.entity.dto.OrderDTO;
import com.softmax.marvel.handler.order.AbstractOrderHandler;
import com.softmax.marvel.handler.order.OrderHandlerContext;
import com.softmax.marvel.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class OrderServiceImpl implements OrderService {

    @Autowired
    private OrderHandlerContext handlerContext;

    @Override
    public String handleOrder(OrderDTO orderDTO) throws IllegalAccessException {
        AbstractOrderHandler handler = handlerContext.getInstance(orderDTO.getOrderType());
        return handler.handle(orderDTO);
    }
}
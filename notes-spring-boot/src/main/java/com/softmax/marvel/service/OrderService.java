package com.softmax.marvel.service;

import com.softmax.marvel.entity.dto.OrderDTO;

public interface OrderService {
    /**
     * 处理订单业务
     *
     * @param orderDTO
     * @return
     */
    String handleOrder(OrderDTO orderDTO) throws IllegalAccessException;
}

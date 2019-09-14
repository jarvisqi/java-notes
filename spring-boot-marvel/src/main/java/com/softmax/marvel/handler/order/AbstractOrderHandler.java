package com.softmax.marvel.handler.order;

import com.softmax.marvel.entity.dto.OrderDTO;

/**
 * 抽象处理器
 *
 * @author Jarvis
 * @date 2019/9/14
 */
public abstract class AbstractOrderHandler {
    /**
     * 订单处理器
     *
     * @param orderDTO
     * @return
     */
    abstract public String handle(OrderDTO orderDTO);
}

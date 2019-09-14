package com.softmax.marvel.handler.order;

import com.softmax.marvel.entity.dto.OrderDTO;
import org.springframework.stereotype.Component;

/**
 * 团购订单处理器
 *
 * @author Jarvis
 * @date 2019/9/14
 */
@Component
@OrderHandlerType(2)
public class GroupHandler extends AbstractOrderHandler {

    @Override
    public String handle(OrderDTO orderDTO) {
        return "开始处理团购订单";
    }
}

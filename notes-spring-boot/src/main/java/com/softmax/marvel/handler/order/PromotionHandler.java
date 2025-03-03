package com.softmax.marvel.handler.order;

import com.softmax.marvel.entity.dto.OrderDTO;
import com.softmax.marvel.handler.HandlerType;
import org.springframework.stereotype.Component;

/**
 * 促销订单处理器
 *
 * @author Jarvis
 * @date 2019/9/14
 */
@Component
@HandlerType(3)
public class PromotionHandler extends AbstractOrderHandler {
    @Override
    public String handle(OrderDTO orderDTO) {
        return "开始处理促销订单";
    }
}

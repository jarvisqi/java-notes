package com.softmax.marvel.handler.order;

import com.softmax.marvel.handler.BeanTool;
import com.softmax.marvel.handler.HandlerType;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

/**
 * 处理器上下文，根据类型获取相应的处理器
 *
 * @author Jarvis
 * @date 2019/9/14
 */
@Component
public class OrderHandlerContext {

    /**
     * 存放所有策略类Bean的map
     */
    public static Map<Integer, Class<HandlerType>> hanlderTypeBeanMap = new HashMap<>(3);

    /**
     * 从容器中获取对应的策略Bean
     *
     * @param orderType
     * @return
     * @throws IllegalAccessException
     */
    public AbstractOrderHandler getInstance(Integer orderType) throws IllegalAccessException {
        Class<HandlerType> clazz = hanlderTypeBeanMap.get(orderType);
        if (clazz == null) {
            throw new IllegalAccessException("not found handler for type " + orderType);
        }
        return (AbstractOrderHandler) BeanTool.getBean(clazz);
    }
}

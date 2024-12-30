package com.softmax.marvel.handler.order;

import com.softmax.marvel.handler.HandlerType;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

import java.util.Map;

/**
 * 处理器注册到spring容器
 * 获取所有策略注解的类型,并将对应的class初始化到HandlerOrderContext中
 *
 * @author Jarvis
 * @date 2019/9/14
 */
@Component
public class HandlerProcessor implements ApplicationContextAware {

    /**
     * 获取所有的策略Beanclass 加入HandlerOrderContext属性中
     *
     * @param applicationContext
     * @throws BeansException
     */
    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        //获取所有策略注解的Bean
        Map<String, Object> orderHandlerMap = applicationContext.getBeansWithAnnotation(HandlerType.class);
        orderHandlerMap.forEach((k, v) -> {
            Class<HandlerType> orderHandlerTypeClass = (Class<HandlerType>) v.getClass();
            Integer type = orderHandlerTypeClass.getAnnotation(HandlerType.class).value();
            //将class加入map中,type作为key
            OrderHandlerContext.hanlderTypeBeanMap.put(type, orderHandlerTypeClass);
        });
    }
}

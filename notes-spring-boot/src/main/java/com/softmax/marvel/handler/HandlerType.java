package com.softmax.marvel.handler;

import java.lang.annotation.*;

/**
 * 自定义注解 处理订单类型
 *
 * @author Jarvis
 * @date 2019/9/14
 */
//作用在类上
@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
//子类可以继承此注解
@Inherited
public @interface HandlerType {
    /**
     * 策略类型
     *
     * @return
     */
    int value();
}

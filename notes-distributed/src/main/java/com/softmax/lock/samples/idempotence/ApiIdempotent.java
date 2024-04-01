package com.softmax.lock.samples.idempotence;


import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 在需要保证 接口幂等性 的Controller的方法上使用此注解
 *
 * @author Jarvis
 * @date 2020/03/23
 */
@Target({ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
public @interface ApiIdempotent {

}

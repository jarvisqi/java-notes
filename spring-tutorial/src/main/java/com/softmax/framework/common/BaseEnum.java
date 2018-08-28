package com.softmax.framework.common;

import com.fasterxml.jackson.annotation.JsonValue;
import org.springframework.util.ReflectionUtils;

import java.lang.reflect.Field;

/**
 * 抽象枚举 Enum 接口
 *
 * @author : Jarvis
 * @date : 2018/5/29
 */
public interface BaseEnum {

    String DEFAULT_VALUE_NAME = "value";
    String DEFAULT_DESC_NAME = "description";

    /**
     * 取值
     *
     * @return Integer
     */
    default Integer getValue() {
        Field field = ReflectionUtils.findField(this.getClass(), DEFAULT_VALUE_NAME);
        if (field == null) {
            return null;
        }
        try {
            field.setAccessible(true);
            return Integer.parseInt(field.get(this).toString());
        } catch (IllegalAccessException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * 取描述信息
     *
     * @return
     */
    @JsonValue
    default String getDescription() {
        Field field = ReflectionUtils.findField(this.getClass(), DEFAULT_DESC_NAME);
        if (field == null) {
            return null;
        }
        try {
            field.setAccessible(true);
            return field.get(this).toString();
        } catch (IllegalAccessException e) {
            throw new RuntimeException(e);
        }
    }

}

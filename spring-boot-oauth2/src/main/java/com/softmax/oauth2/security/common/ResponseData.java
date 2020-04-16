package com.softmax.oauth2.security.common;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.Data;

/**
 * @author Jarvis
 */
@Data
public class ResponseData<T> {
    /**
     * 统一返回码
     */
    public Integer code;

    /**
     * 统一错误消息
     */
    public String message;

    /**
     * 结果对象
     */
    public T data;


    public String toJson() throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();
        return objectMapper.writeValueAsString(this);
    }
}

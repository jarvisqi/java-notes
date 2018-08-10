package com.framework.common;


import org.springframework.http.HttpStatus;

import java.io.Serializable;

/**
 * api 数据返回对象
 *
 * @author : Jarvis
 * @date : 2018/6/7
 */
public class AjaxResult<T> implements Serializable {

    private boolean success;
    private T data;
    private String message;
    private int code;

    public AjaxResult() {

    }

    public AjaxResult(T data) {
        this.data = data;
        this.success = true;
        this.code = HttpStatus.OK.value();
        this.message = null;
    }

    public AjaxResult(String message) {
        this.data = null;
        this.success = true;
        this.code = HttpStatus.OK.value();
        this.message = message;
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }
}

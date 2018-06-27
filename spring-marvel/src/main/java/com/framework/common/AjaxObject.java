package com.framework.common;


import cn.hutool.http.HttpStatus;

import java.util.HashMap;
import java.util.Map;

/**
 * api 数据返回对象
 *
 * @author : Jarvis
 * @date : 2018/6/7
 */
public class AjaxObject extends HashMap<String, Object> {

    private static final long serialVersionUID = 5707162329051330256L;

    public AjaxObject() {
        put("code", 0);
    }

    public static AjaxObject error() {
        return error(HttpStatus.HTTP_INTERNAL_ERROR, "系统异常，请联系管理员");
    }

    public static AjaxObject error(String message) {
        return error(HttpStatus.HTTP_INTERNAL_ERROR, message);
    }

    public static AjaxObject error(int code, String msg) {
        AjaxObject object = new AjaxObject();
        object.put("code", code);
        object.put("message", msg);
        return object;
    }

    public static AjaxObject ok(String message) {
        AjaxObject object = new AjaxObject();
        object.put("message", message);
        return object;
    }

    public static AjaxObject ok(Map<String, Object> map) {
        AjaxObject object = new AjaxObject();
        object.putAll(map);
        return object;
    }

    @Override
    public AjaxObject put(String key, Object value) {
        super.put(key, value);
        return this;
    }

    public AjaxObject data(Object value) {
        super.put("data", value);
        return this;
    }

}

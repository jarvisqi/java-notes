package com.softmax.framework.common;


/**
 * 自定义异常处理类
 *
 * @author : Jarvis
 * @date : 2018/6/7
 */
public class BizException extends RuntimeException {

    private static final long serialVersionUID = 2876339022770799166L;
    private String msg;
    private int code;

    public BizException(String msg) {
        super(msg);
        this.msg = msg;
    }

    public BizException(String msg, Throwable e) {
        super(msg, e);
        this.msg = msg;
    }

    public BizException(int code, String msg) {
        super(msg);
        this.code = code;
        this.msg = msg;
    }

    public BizException(String msg, int code, Throwable e) {
        super(msg, e);
        this.msg = msg;
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }
}

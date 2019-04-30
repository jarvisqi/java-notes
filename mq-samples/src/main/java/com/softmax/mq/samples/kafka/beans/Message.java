package com.softmax.mq.samples.kafka.beans;

import java.util.Date;

/**
 * 消息体
 *
 * @author Jarvis
 * @date 2018/8/3
 */
public class Message {
    private Long id;

    private String msg;

    private Date sendTime;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public Date getSendTime() {
        return sendTime;
    }

    public void setSendTime(Date sendTime) {
        this.sendTime = sendTime;
    }
}

package com.softmax.mq.courier.kafka.beans;

import org.springframework.stereotype.Component;

import java.util.Date;

/**
 * 消息体
 *
 * @author Jarvis
 * @date 2018/8/3
 */
@Component
public class Message {
    private Long id;

    private String message;

    private Date sendTime;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Date getSendTime() {
        return sendTime;
    }

    public void setSendTime(Date sendTime) {
        this.sendTime = sendTime;
    }
}

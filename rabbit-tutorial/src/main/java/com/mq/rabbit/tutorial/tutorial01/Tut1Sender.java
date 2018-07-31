package com.mq.rabbit.tutorial.tutorial01;

import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.amqp.core.Queue;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;

import java.util.Date;

/**
 * rabbitMQ 生产者
 *
 * @author Jarvis
 * @date 2018/7/31
 */
public class Tut1Sender {

    @Autowired
    private AmqpTemplate amqpTemplate;
    @Autowired
    private Queue queue;

    /**
     * 用定时任务来模拟生产者定时发送消息
     */
    @Scheduled(fixedDelay = 1000, initialDelay = 500)
    public void sendMsg() {
        String msg = "Hello World!" + new Date();
        amqpTemplate.convertAndSend(queue.getName(), msg);
        System.out.println(" [x] Sent '" + msg + "'");
    }
}

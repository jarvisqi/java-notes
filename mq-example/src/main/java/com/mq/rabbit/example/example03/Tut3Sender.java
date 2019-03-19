package com.mq.rabbit.example.example03;

import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.amqp.core.FanoutExchange;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;

/**
 * 发布订阅 - 生产者
 *
 * @author Jarvis
 * @date 2018/7/31
 */
public class Tut3Sender {
    @Autowired
    private AmqpTemplate template;

    @Autowired
    private FanoutExchange fanoutExchange;

    private int dots = 0;
    private int count = 0;

    @Scheduled(fixedDelay = 1000, initialDelay = 500)
    public void send() {
        StringBuilder builder = new StringBuilder("Hello");
        if (dots++ == 3) {
            dots = 1;
        }
        for (int i = 0; i < dots; i++) {
            builder.append('.');
        }
        builder.append(Integer.toString(++count));
        String message = builder.toString();
        // 发送时我们需要提供一个routingKey，但是对于 fanout exchange，这个值将被忽略。
        template.convertAndSend(fanoutExchange.getName(), "", message);
        System.out.println(" [x] Sent '" + message + "'");
    }
}

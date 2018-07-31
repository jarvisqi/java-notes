package com.mq.rabbit.tutorial.tutorial06;

import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;

/**
 * @author Jarvis
 * @date 2018/7/31
 */
public class Tut5Sender {

    @Autowired
    private AmqpTemplate template;

    @Autowired
    private TopicExchange topicExchange;

    private int index;
    private int count;
    private final String[] keys = {"quick.orange.rabbit", "lazy.orange.elephant", "quick.orange.fox",
            "lazy.brown.fox", "lazy.pink.rabbit", "quick.brown.fox"};

    @Scheduled(fixedDelay = 1000, initialDelay = 500)
    public void send() {
        StringBuilder builder = new StringBuilder("Hello to ");
        if (++this.index == keys.length) {
            this.index = 0;
        }
        String key = keys[this.index];
        builder.append(key).append(' ');
        builder.append(Integer.toString(++this.count));
        String message = builder.toString();

        template.convertAndSend(topicExchange.getName(), key, message);
        System.out.println(" [x] Sent '" + message + "'");
    }
}

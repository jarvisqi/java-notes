package com.softmax.mq.samples.rabbit.example04;

import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;

/**
 * @author Jarvis
 * @date 2018/7/31
 */
public class Tut4Sender {

    @Autowired
    private AmqpTemplate template;

    @Autowired
    private DirectExchange directExchange;

    private int index;
    private int count;
    private final String[] keys = {"orange", "black", "green"};

    /**
     * 将使用颜色作为路由键，这样消费者将能通过选择想要接收（或订阅）的颜色来消费对应的消息。
     */
    @Scheduled(fixedDelay = 1000, initialDelay = 500)
    public void send() {
        StringBuilder builder = new StringBuilder("Hello to ");
        if (++index == 3) {
            index = 0;
        }
        String key = keys[this.index];
        builder.append(key).append(' ').append(Integer.toString(++count));
        String message = builder.toString();

        template.convertAndSend(directExchange.getName(), key, message);
        System.out.println(" [x] Sent '" + message + "'");
    }
}

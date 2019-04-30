package com.softmax.mq.samples.rabbit.example01;

import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;

/**
 * rabbitMQ 消费者
 *
 * @author Jarvis
 * @date 2018/7/31
 */
@RabbitListener(queues = "hello-world")
public class Tut1Receiver {

    @RabbitHandler
    public void receiver(String in) {
        System.out.println(" [x] Received '" + in + "'");
    }

}

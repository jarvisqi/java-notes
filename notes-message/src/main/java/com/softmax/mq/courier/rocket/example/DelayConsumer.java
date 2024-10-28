package com.softmax.mq.courier.rocket.example;

import org.apache.rocketmq.spring.annotation.RocketMQMessageListener;
import org.apache.rocketmq.spring.core.RocketMQListener;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;


/**
 * @author jarvis
 */
@Component
@RocketMQMessageListener(topic = "delay-topic", consumerGroup = "delay-group")
public class DelayConsumer implements RocketMQListener<String> {

    private final Logger logger = LoggerFactory.getLogger(DelayConsumer.class);

    @Override
    public void onMessage(String message) {
        logger.info(String.format("received message time is {}",
                DateTimeFormatter.ofPattern("yyyy年MM月dd日 HH:mm:ss").format(LocalDateTime.now())));
        logger.info(String.format("received message is {}", message));

    }
}

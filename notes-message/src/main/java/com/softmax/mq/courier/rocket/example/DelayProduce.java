package com.softmax.mq.courier.rocket.example;

import com.softmax.mq.courier.kafka.provider.KafkaSender;
import org.apache.rocketmq.client.producer.SendResult;
import org.apache.rocketmq.spring.core.RocketMQTemplate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * @author jarvis
 */
@Component
public class DelayProduce {

    private Logger logger = LoggerFactory.getLogger(DelayProduce.class);

    @Autowired
    private RocketMQTemplate rocketMQTemplate;


    /**
     * 延迟消息发送
     *
     * @param topic
     * @param message
     * @param delayLevel
     */
    public void sendDelayMessage(String topic, String message, int delayLevel) {
        SendResult sendResult = rocketMQTemplate.syncSend(topic,
                MessageBuilder.withPayload(message).build(),
                2000, delayLevel);

        logger.info(String.format("sendtime is {}",
                DateTimeFormatter.ofPattern("yyyy年MM月dd日 HH:mm:ss").format(LocalDateTime.now())));
        logger.info(String.format("sendResult is{}", sendResult));
    }

}

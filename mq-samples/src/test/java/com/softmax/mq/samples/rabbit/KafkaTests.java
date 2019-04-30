package com.softmax.mq.samples.rabbit;

import com.softmax.mq.samples.kafka.beans.Message;
import com.softmax.mq.samples.kafka.provider.KafkaSender;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Date;
import java.util.UUID;

/**
 * @author Jarvis
 * @date 2019/3/19
 */
public class KafkaTests {


    @Autowired
    private KafkaSender<Message> kafkaSender;

    @Test
    public void kafkaSend() throws InterruptedException {
        //发消息
        for (int i = 0; i < 5; i++) {

            Message message = new Message();
            message.setId(System.currentTimeMillis());
            message.setMsg(UUID.randomUUID().toString());
            message.setSendTime(new Date());

            kafkaSender.send(message);
            Thread.sleep(3000);

        }
    }
}

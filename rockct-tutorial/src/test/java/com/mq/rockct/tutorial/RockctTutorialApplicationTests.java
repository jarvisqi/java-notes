package com.mq.rockct.tutorial;

import com.mq.rockct.tutorial.config.ConsumerProperties;
import org.apache.rocketmq.client.exception.MQBrokerException;
import org.apache.rocketmq.client.exception.MQClientException;
import org.apache.rocketmq.client.producer.DefaultMQProducer;
import org.apache.rocketmq.client.producer.SendResult;
import org.apache.rocketmq.common.message.Message;
import org.apache.rocketmq.remoting.common.RemotingHelper;
import org.apache.rocketmq.remoting.exception.RemotingException;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.UnsupportedEncodingException;


@RunWith(SpringRunner.class)
@SpringBootTest
public class RockctTutorialApplicationTests {
//
//    @Autowired
//    private DefaultMQProducer defaultMQProducer;
//
//    @Autowired
//    private ConsumerProperties properties;

    @Test
    public void properties() {
        // properties.getTopics().forEach(topic -> System.out.println(topic.get("topic")));
    }

    @Test
    public void send() throws InterruptedException, RemotingException, MQClientException, MQBrokerException {
//        String msg = "sen user msg";
//        Message message = new Message("user", "usr", msg.getBytes());
//        System.out.println("======================= 开始发消息");
//        SendResult result = defaultMQProducer.send(message);
//        System.out.println("======================= 消息响应信息" + result.toString());
    }

    @Test
    public void produce() throws MQClientException, UnsupportedEncodingException, RemotingException, InterruptedException, MQBrokerException {
        DefaultMQProducer producer = new DefaultMQProducer("ProducerGroupName");
        producer.setNamesrvAddr("192.168.10.103:9876");
        producer.setInstanceName("Producer1");
        producer.start();
        for (int i = 0; i < 1; i++)
            try {
                {
                    Message msg = new Message("TopicTest",// topic
                            "TagA",// tag
                            "OrderID188",// key
                            ("Hello MetaQ").getBytes());// body
                    SendResult sendResult = producer.send(msg);
                    System.out.println(sendResult);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        producer.shutdown();

    }

}

package com.mq.rockct.tutorial.demo;

import org.apache.rocketmq.client.consumer.DefaultMQPushConsumer;
import org.apache.rocketmq.client.consumer.listener.*;
import org.apache.rocketmq.client.producer.DefaultMQProducer;
import org.apache.rocketmq.common.consumer.ConsumeFromWhere;
import org.apache.rocketmq.common.message.Message;
import org.apache.rocketmq.common.message.MessageExt;
import org.apache.rocketmq.remoting.common.RemotingHelper;

import java.io.UnsupportedEncodingException;

import static com.mq.rockct.tutorial.demo.SyncProducer.NAMESRVADDR;

/**
 * 单向传输用于需要中等可靠性的情况，只管发送，不需要返回结果，如日志收集。
 *
 * @author Jarvis
 * @date 2018/9/7
 */
public class OnewayProducer {

    public static void main(String[] args) throws Exception {
        //Instantiate with a producer group name.
        DefaultMQProducer producer = new DefaultMQProducer("ONEWAY_MQ_GROUP");
        // Specify name server addresses.
        producer.setNamesrvAddr(NAMESRVADDR);
        //Launch the instance.
        producer.start();
        for (int i = 0; i < 100; i++) {
            //Create a message instance, specifying topic, tag and message body.
            Message msg = new Message("Oneway_Topic_Test",
                    "Oneway_TagA",
                    ("Hello RocketMQ Oneway Message " + i).getBytes(RemotingHelper.DEFAULT_CHARSET)
            );
            //Call send message to deliver message to one of brokers.
            producer.sendOneway(msg);

        }
        //Shut down once the producer instance is not longer in use.
        producer.shutdown();
    }
}

class OnewayConsumer {

    public static void main(String[] args) throws Exception {
        DefaultMQPushConsumer consumer = new DefaultMQPushConsumer("ONEWAY_CONSUMER");
        consumer.setNamesrvAddr(NAMESRVADDR);
        consumer.setConsumeFromWhere(ConsumeFromWhere.CONSUME_FROM_FIRST_OFFSET);
        //设置consumer所订阅的Topic和Tag，*代表全部的Tag
        consumer.subscribe("Oneway_Topic_Test", "*");

        consumer.registerMessageListener((MessageListenerConcurrently) (list, consumeOrderlyContext) -> {
            MessageExt messge = list.get(0);
            try {
                String topic = messge.getTopic();
                String tags = messge.getTags();
                String msgBody = new String(messge.getBody(), "utf-8");
                System.out.println("收到消息:topic:" + topic + ",tags:" + tags + ",msg:" + messge);
                System.out.println("MsgBody:" + msgBody);
                System.out.println("------------------------------------------------------------------------------");
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }

            return ConsumeConcurrentlyStatus.CONSUME_SUCCESS;
        });
        consumer.start();
        System.out.println("consumer start");

    }
}
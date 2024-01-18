package com.softmax.mq.courier.rocket.example;

import org.apache.rocketmq.client.consumer.DefaultMQPushConsumer;
import org.apache.rocketmq.client.consumer.listener.ConsumeConcurrentlyStatus;
import org.apache.rocketmq.client.consumer.listener.MessageListenerConcurrently;
import org.apache.rocketmq.common.consumer.ConsumeFromWhere;
import org.apache.rocketmq.common.message.MessageExt;

import java.io.UnsupportedEncodingException;

class OnewayConsumer {

    public static void main(String[] args) throws Exception {
        DefaultMQPushConsumer consumer = new DefaultMQPushConsumer("ONEWAY_CONSUMER");
        consumer.setNamesrvAddr(SyncProducer.NAMESRVADDR);
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

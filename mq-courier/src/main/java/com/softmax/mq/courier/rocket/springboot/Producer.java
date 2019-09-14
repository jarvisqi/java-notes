package com.softmax.mq.courier.rocket.springboot;

import org.apache.rocketmq.client.producer.SendCallback;
import org.apache.rocketmq.client.producer.SendResult;
import org.apache.rocketmq.client.producer.TransactionSendResult;
import org.apache.rocketmq.spring.core.RocketMQTemplate;
import org.apache.rocketmq.spring.support.RocketMQHeaders;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.messaging.Message;
import org.springframework.messaging.support.MessageBuilder;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

/**
 * RocketMQTemplate 发送消息
 *
 * @author Jarvis
 * @date 2019/04/30
 */
public class Producer implements CommandLineRunner {

    public static final String TX_PGROUP_NAME = "TestTxProducerGroup";

    @Value("${demo.rocketmq.topic}")
    private String springTopic;
    @Value("${demo.rocketmq.orderTopic}")
    private String orderPaidTopic;
    @Value("${demo.rocketmq.transTopic}")
    private String springTransTopic;
    @Value("${demo.rocketmq.msgExtTopic}")
    private String msgExtTopic;

    @Autowired
    private RocketMQTemplate rocketMQTemplate;

    @Override
    public void run(String... args) throws Exception {

        SendResult result = rocketMQTemplate.syncSend(springTopic, "RocketMQ 同步发送字符串");
        System.out.printf(String.format("syncSend topic %s,sendResult", springTopic, result));

        result = rocketMQTemplate.syncSend(springTopic, MessageBuilder.withPayload("Hello, RocketMQ! MessageBuilder发送消息").build());
        System.out.printf("syncSend2 to topic %s sendResult=%s %n", springTopic, result);

        //发送异步消息
        rocketMQTemplate.asyncSend(orderPaidTopic, new OrderPaidEvent("SO_1001", new BigDecimal("66.66")), new SendCallback() {
            @Override
            public void onSuccess(SendResult sendResult) {
                System.out.printf("异步消息发送成功 SendResult=%s %n", sendResult);
            }

            @Override
            public void onException(Throwable throwable) {
                System.out.printf("异步消息发生异常 SendResult=%s %n", throwable);
            }
        });

        rocketMQTemplate.convertAndSend(msgExtTopic + ":tag0", "I'm from tag0");
        System.out.printf("syncSend topic %s tag %s %n", msgExtTopic, "tag0");

        rocketMQTemplate.convertAndSend(msgExtTopic + ":tag1", "I'm from tag1");
        System.out.printf("syncSend topic %s tag %s %n", msgExtTopic, "tag1");

        sendBatchMessage();

        sendTransaction();
    }

    /**
     * 批量发送消息
     */
    private void sendBatchMessage() {
        List<Message> messageList = new ArrayList<>(10);
        for (int i = 0; i < 10; i++) {
            messageList.add(MessageBuilder.withPayload("batch messages" + i)
                    .setHeader(RocketMQHeaders.KEYS, "KEY_" + i).build());
        }
        SendResult result = rocketMQTemplate.syncSend(springTopic, messageList);
        System.out.printf("--- Batch messages send result :" + result);
    }

    /**
     * 事务消息
     */
    private void sendTransaction() throws InterruptedException {
        String[] tags = new String[]{"TagA", "TagB", "TagC", "TagD", "TagE"};
        for (int i = 0; i < 10; i++) {
            try {
                Message message = MessageBuilder.withPayload("Hello RocketMQ " + i)
                        .setHeader(RocketMQHeaders.KEYS, "KEY_" + i).build();
                TransactionSendResult sendResult = rocketMQTemplate.sendMessageInTransaction(TX_PGROUP_NAME,
                        springTransTopic + ":" + tags[i % tags.length], message, null);
                System.out.printf("------send transaction message body =%s,sendResult = %s %n",
                        message.getPayload(), sendResult.getSendStatus());
                Thread.sleep(100);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }


    public class OrderPaidEvent implements Serializable {
        private String orderId;
        private BigDecimal paidMoney;

        public OrderPaidEvent(String orderId, BigDecimal paidMoney) {
            this.orderId = orderId;
            this.paidMoney = paidMoney;
        }

        public OrderPaidEvent() {
        }

        public String getOrderId() {
            return orderId;
        }

        public void setOrderId(String orderId) {
            this.orderId = orderId;
        }

        public BigDecimal getPaidMoney() {
            return paidMoney;
        }

        public void setPaidMoney(BigDecimal paidMoney) {
            this.paidMoney = paidMoney;
        }
    }
}

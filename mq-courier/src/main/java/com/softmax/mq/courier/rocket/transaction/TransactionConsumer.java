package com.softmax.mq.courier.rocket.transaction;

import org.apache.rocketmq.client.consumer.DefaultMQPushConsumer;
import org.apache.rocketmq.client.consumer.listener.*;
import org.apache.rocketmq.client.exception.MQClientException;
import org.apache.rocketmq.common.consumer.ConsumeFromWhere;
import org.apache.rocketmq.common.message.MessageExt;

import java.util.List;
import java.util.concurrent.TimeUnit;

import static com.softmax.mq.courier.rocket.transaction.TransactionProducer.NAMESRVADDR;

/**
 * 事务消费者
 *
 * @author Jarvis
 * @date 2018/11/14
 */
public class TransactionConsumer {

    public static void main(String[] args) throws MQClientException {
        DefaultMQPushConsumer consumer = new DefaultMQPushConsumer("transaction_Consumer");
        consumer.setNamesrvAddr(NAMESRVADDR);
        /**
         *  设置Consumer第一次启动是从队列头部开始消费还是队列尾部开始消费
         *  如果非第一次启动，那么按照上次消费的位置继续消费
         */
        consumer.setConsumeFromWhere(ConsumeFromWhere.CONSUME_FROM_FIRST_OFFSET);
        consumer.subscribe("TopicTransactionTest", "*");
        consumer.registerMessageListener(new MessageListenerConcurrently() {
            @Override
            public ConsumeConcurrentlyStatus consumeMessage(List<MessageExt> list,
                                                            ConsumeConcurrentlyContext concurrentlyContext) {
                for (MessageExt msg : list) {
                    System.out.println(msg + ",内容：" + new String(msg.getBody()));
                }
                try {
                    TimeUnit.SECONDS.sleep(5L);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                    //重试
                    return ConsumeConcurrentlyStatus.RECONSUME_LATER;
                }
                //成功
                return ConsumeConcurrentlyStatus.CONSUME_SUCCESS;
            }
        });
        consumer.start();
        System.out.println("consumer Started.");
    }
}

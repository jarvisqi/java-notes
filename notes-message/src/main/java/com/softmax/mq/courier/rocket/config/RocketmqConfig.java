package com.softmax.mq.courier.rocket.config;

import org.apache.rocketmq.client.consumer.DefaultMQPushConsumer;
import org.apache.rocketmq.client.consumer.listener.*;
import org.apache.rocketmq.client.exception.MQClientException;
import org.apache.rocketmq.client.producer.DefaultMQProducer;
import org.apache.rocketmq.common.message.MessageExt;
import org.apache.rocketmq.common.protocol.heartbeat.MessageModel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;
import java.util.Map;
import java.util.concurrent.*;
import java.util.stream.Collectors;

/**
 * RocketMQ 配置
 *
 * @author Jarvis
 * @date 2018/8/6
 */
@Configuration
public class RocketmqConfig {
    private static final Logger logger = LoggerFactory.getLogger(RocketmqConfig.class);

    @Autowired
    private ProducerProperties producerParma;

    @Autowired
    private ConsumerProperties consumerParma;

    private String groupName;

    private static boolean isFirstSub = true;

    private static long startTime = System.currentTimeMillis();

    /**
     * 初始化向rocketmq发送普通消息的生产者
     *
     * @return
     * @throws MQClientException
     */
    @Bean
    public DefaultMQProducer defaultMQProducer() throws MQClientException {
        DefaultMQProducer producer = new DefaultMQProducer("rock-test");
        producer.setNamesrvAddr(producerParma.getNamesrvAddr());
        producer.setMaxMessageSize(producerParma.getMaxMessageSize());
        producer.setSendMsgTimeout(producerParma.getSendMsgTimeout());
        //mq server版本小于3.5.8，请设置不使用
        /// producer.setVipChannelEnabled(true);
        producer.setRetryTimesWhenSendFailed(producerParma.getRetryTimesWhenSendFailed());

        //Producer对象start初始化
        producer.start();

        logger.info("RocketMQ defaultProducer Started.");
        return producer;
    }

    /**
     * 初始化rocketmq消息监听方式的消费者
     *
     * @return
     */
    @Bean
    public DefaultMQPushConsumer defaultMQPushConsumer() throws MQClientException {
        DefaultMQPushConsumer consumer = new DefaultMQPushConsumer("rock-test");
        consumer.setNamesrvAddr(consumerParma.getNamesrvAddr());
        if (consumerParma.isConsumerBroadcasting()) {
            consumer.setMessageModel(MessageModel.BROADCASTING);
        }
        consumer.setConsumeMessageBatchMaxSize(consumerParma.getConsumerBatchMaxSize());
        consumer.setConsumeThreadMax(consumerParma.getConsumeThreadMax());
        consumer.setConsumeThreadMin(consumerParma.getConsumeThreadMin());
        /**
         * 订阅指定topic下tags
         */
        List<Map<String, String>> subscribeList = consumerParma.getTopics();
        for (Map<String, String> item : subscribeList) {
            String topic = item.get("topic");
            String tag = item.get("tag");
            consumer.subscribe(topic, tag);
        }
        //顺序消费
        if (consumerParma.isEnableOrderConsumer()) {
            consumer.registerMessageListener((MessageListenerOrderly) (list, consumeOrderlyContext) -> {
                consumeOrderlyContext.setAutoCommit(true);
                list = filter(list);
                try {
                    if (list.size() == 0) {
                        return ConsumeOrderlyStatus.SUCCESS;
                        //TODO
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                    return ConsumeOrderlyStatus.SUSPEND_CURRENT_QUEUE_A_MOMENT;
                }

                // 如果没有return success，consumer会重复消费此信息，直到success。
                return ConsumeOrderlyStatus.SUSPEND_CURRENT_QUEUE_A_MOMENT;
            });

        } else {
            consumer.registerMessageListener((MessageListenerConcurrently) (list, consumeConcurrentlyContext) -> {
                list = filter(list);
                try {
                    if (list.size() == 0) {
                        return ConsumeConcurrentlyStatus.CONSUME_SUCCESS;
                        //TODO
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                    //稍后再试
                    return ConsumeConcurrentlyStatus.RECONSUME_LATER;
                }
                // 消费成功
                return ConsumeConcurrentlyStatus.CONSUME_SUCCESS;
            });
        }

        // 构造一个线程池
        ThreadPoolExecutor singleThreadPool = new ThreadPoolExecutor(1, 1, 0L,
                TimeUnit.MILLISECONDS, new LinkedBlockingQueue<>(1024), new ThreadPoolExecutor.AbortPolicy());
        singleThreadPool.execute(() -> {
                    try {
                        // 延迟5秒再启动，主要是等待spring事件监听相关程序初始化完成，
                        // 否则，回出现对RocketMQ的消息进行消费后立即发布消息到达的事件，然而此事件的监听程序还未初始化，从而造成消息的丢失
                        Thread.sleep(5000);
                        try {
                            //Consumer对象start初始化
                            consumer.start();
                        } catch (Exception e) {
                            //启动失败
                            logger.info("RocketMq pushConsumer Start failure.");
                            logger.error(e.getMessage(), e);
                        }
                        logger.info("RocketMq pushConsumer Started.");
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
        );
        singleThreadPool.shutdown();

        return consumer;
    }

    /**
     * 过滤消息
     *
     * @param messageExts 消息
     * @return
     */
    private List<MessageExt> filter(List<MessageExt> messageExts) {
        if (isFirstSub && !consumerParma.isEnableHisConsumer()) {
            messageExts = messageExts.stream()
                    .filter(item -> startTime - item.getBornTimestamp() < 0)
                    .collect(Collectors.toList());
        }
        if (isFirstSub && messageExts.size() > 0) {
            isFirstSub = false;
        }
        return messageExts;
    }


}

package com.softmax.mq.courier.rocket.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @author Jarvis
 * @date 2018/8/6
 */
@Component
@ConfigurationProperties(prefix = ConsumerProperties.PREFIX)
public class ConsumerProperties {

    public static final String PREFIX = "rocketmq.consumer";

    private String namesrvAddr;
    private String consumerGroupName;
    private String consumerInstanceName;
    private int consumerBatchMaxSize;
    private int consumeThreadMin;
    private int consumeThreadMax;
    private boolean consumerBroadcasting;

    private boolean enableHisConsumer;
    private boolean enableOrderConsumer;

    private List<Map<String, String>> topics = new ArrayList<>();

    public String getNamesrvAddr() {
        return namesrvAddr;
    }

    public void setNamesrvAddr(String namesrvAddr) {
        this.namesrvAddr = namesrvAddr;
    }

    public String getConsumerGroupName() {
        return consumerGroupName;
    }

    public void setConsumerGroupName(String consumerGroupName) {
        this.consumerGroupName = consumerGroupName;
    }

    public String getConsumerInstanceName() {
        return consumerInstanceName;
    }

    public void setConsumerInstanceName(String consumerInstanceName) {
        this.consumerInstanceName = consumerInstanceName;
    }

    public int getConsumerBatchMaxSize() {
        return consumerBatchMaxSize;
    }

    public void setConsumerBatchMaxSize(int consumerBatchMaxSize) {
        this.consumerBatchMaxSize = consumerBatchMaxSize;
    }

    public boolean isConsumerBroadcasting() {
        return consumerBroadcasting;
    }

    public void setConsumerBroadcasting(boolean consumerBroadcasting) {
        this.consumerBroadcasting = consumerBroadcasting;
    }

    public boolean isEnableHisConsumer() {
        return enableHisConsumer;
    }

    public void setEnableHisConsumer(boolean enableHisConsumer) {
        this.enableHisConsumer = enableHisConsumer;
    }

    public boolean isEnableOrderConsumer() {
        return enableOrderConsumer;
    }

    public void setEnableOrderConsumer(boolean enableOrderConsumer) {
        this.enableOrderConsumer = enableOrderConsumer;
    }

    public void setConsumerBatchMaxSize(Integer consumerBatchMaxSize) {
        this.consumerBatchMaxSize = consumerBatchMaxSize;
    }

    public int getConsumeThreadMin() {
        return consumeThreadMin;
    }

    public void setConsumeThreadMin(int consumeThreadMin) {
        this.consumeThreadMin = consumeThreadMin;
    }

    public int getConsumeThreadMax() {
        return consumeThreadMax;
    }

    public void setConsumeThreadMax(int consumeThreadMax) {
        this.consumeThreadMax = consumeThreadMax;
    }

    public List<Map<String, String>> getTopics() {
        return topics;
    }

    public void setTopics(List<Map<String, String>> topics) {
        this.topics = topics;
    }
}


package com.softmax.mq.courier.rocket.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * 加载yaml配置文件参数
 *
 * @author Jarvis
 * @date 2018/8/6
 */
@Component
@ConfigurationProperties(prefix = ProducerProperties.PREFIX)
public class ProducerProperties {
    public static final String PREFIX = "rocketmq.producer";
    private String namesrvAddr;
    private String producerGroupName;
    private String producerInstanceName;
    private String producerTranInstanceName;
    private int maxMessageSize;
    private int sendMsgTimeout;
    private int retryTimesWhenSendFailed;

    public String getNamesrvAddr() {
        return namesrvAddr;
    }

    public void setNamesrvAddr(String namesrvAddr) {
        this.namesrvAddr = namesrvAddr;
    }

    public String getProducerGroupName() {
        return producerGroupName;
    }

    public void setProducerGroupName(String producerGroupName) {
        this.producerGroupName = producerGroupName;
    }

    public String getProducerInstanceName() {
        return producerInstanceName;
    }

    public void setProducerInstanceName(String producerInstanceName) {
        this.producerInstanceName = producerInstanceName;
    }

    public String getProducerTranInstanceName() {
        return producerTranInstanceName;
    }

    public void setProducerTranInstanceName(String producerTranInstanceName) {
        this.producerTranInstanceName = producerTranInstanceName;
    }

    public int getMaxMessageSize() {
        return maxMessageSize;
    }

    public void setMaxMessageSize(int maxMessageSize) {
        this.maxMessageSize = maxMessageSize;
    }

    public int getSendMsgTimeout() {
        return sendMsgTimeout;
    }

    public void setSendMsgTimeout(int sendMsgTimeout) {
        this.sendMsgTimeout = sendMsgTimeout;
    }

    public int getRetryTimesWhenSendFailed() {
        return retryTimesWhenSendFailed;
    }

    public void setRetryTimesWhenSendFailed(Integer retryTimesWhenSendFailed) {
        this.retryTimesWhenSendFailed = retryTimesWhenSendFailed;
    }

}

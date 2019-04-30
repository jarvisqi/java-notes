package com.softmax.mq.samples.rocket.example;

import org.apache.rocketmq.client.exception.MQClientException;
import org.apache.rocketmq.client.producer.DefaultMQProducer;
import org.apache.rocketmq.client.producer.SendResult;
import org.apache.rocketmq.common.message.Message;
import org.apache.rocketmq.remoting.common.RemotingHelper;

/**
 * 同步消息: 同步传输广泛应用于重要通知消息，短信通知，短信营销系统等
 *
 * @author Jarvis
 * @date 2018/9/6
 */
public class SyncProducer {

    final static String NAMESRVADDR = "192.168.10.100:9876";

    public static void main(String[] args) throws MQClientException {
        //声明并初始化一个producer
        DefaultMQProducer producer = new DefaultMQProducer("SYNC_MQ_GROUP");
        //设置NameServer地址,此处应改为实际NameServer地址，多个地址之间用；分隔
        producer.setNamesrvAddr(NAMESRVADDR);
        producer.setInstanceName("SYNC_PRODUCER");
        // 设置重试次数,默认2
        producer.setRetryTimesWhenSendFailed(3);
        //大于3.5.3版本要设置为false 否则取不到topic
        producer.setVipChannelEnabled(false);
        //设置发送超时时间，默认是3000
        producer.setSendMsgTimeout(10000);
        //调用start()方法启动一个producer实例
        producer.start();
        producer.setRetryTimesWhenSendAsyncFailed(0);

        System.out.println("producer start");
        for (int i = 0; i < 10; i++) {
            try {
                Message msg = new Message("Sync_TopicA_Test",
                        "Sync_TagA",
                        ("RocketMQ message " + i).getBytes(RemotingHelper.DEFAULT_CHARSET)
                );
                //发送消息
                SendResult sendResult = producer.send(msg);
                System.out.printf("%s%n", sendResult);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        //发送完消息之后，调用shutdown()方法关闭producer
        producer.shutdown();

        System.out.println("producer shutdown");
    }
}

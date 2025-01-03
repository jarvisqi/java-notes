package com.softmax.mq.courier.rocket.example;


import org.apache.rocketmq.client.producer.DefaultMQProducer;
import org.apache.rocketmq.common.message.Message;
import org.apache.rocketmq.remoting.common.RemotingHelper;


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
        producer.setNamesrvAddr(SyncProducer.NAMESRVADDR);
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


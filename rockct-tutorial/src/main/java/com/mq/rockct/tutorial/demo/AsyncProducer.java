package com.mq.rockct.tutorial.demo;

import org.apache.rocketmq.client.producer.DefaultMQProducer;
import org.apache.rocketmq.client.producer.SendCallback;
import org.apache.rocketmq.client.producer.SendResult;
import org.apache.rocketmq.common.message.Message;
import org.apache.rocketmq.remoting.common.RemotingHelper;

import static com.mq.rockct.tutorial.demo.SyncProducer.NAMESRVADDR;

/**
 * 异步消息: 异步传输一般用于响应时间敏感的业务场景
 *
 * @author Jarvis
 * @date 2018/9/6
 */
public class AsyncProducer {

    public static void main(String[] args) throws Exception {
        //Instantiate with a producer group name.
        DefaultMQProducer producer = new DefaultMQProducer("ASYNC_MQ_GROUP");
        // Specify name server addresses.
        producer.setNamesrvAddr(NAMESRVADDR);
        producer.setInstanceName("ASYNC_PRODUCER");
        producer.setVipChannelEnabled(false);
        // 设置重试次数,默认2
        producer.setRetryTimesWhenSendFailed(3);
        //设置发送超时时间，默认是3000
        producer.setSendMsgTimeout(10000);

        //Launch the instance.
        producer.start();
        producer.setRetryTimesWhenSendAsyncFailed(0);

        for (int i = 0; i < 10; i++) {
            final int index = i;
            Message msg = new Message("Async_Topic_Test",
                    "Async_TagA",
                    "OrderID188",
                    "Hello world".getBytes(RemotingHelper.DEFAULT_CHARSET));
            producer.send(msg, new SendCallback() {
                @Override
                public void onSuccess(SendResult sendResult) {
                    System.out.printf("%-10d OK %s %n", index, sendResult.getMsgId());
                }

                @Override
                public void onException(Throwable e) {
                    System.out.printf("%-10d Exception %s %n", index, e);
                    e.printStackTrace();
                }
            }, 10000);
        }
        //Shut down once the producer instance is not longer in use.
        producer.shutdown();
    }
}

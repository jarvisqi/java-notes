package com.softmax.mq.courier.rabbit.example01;

import com.rabbitmq.client.*;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

/**
 * 测试rabbitMQ 连接
 *
 * @author Jarvis
 * @date 2018/7/31
 */
public class SendTest {
    public final static String EXCHANGE_NAME = "test-exchange";

    public static void main(String[] args) throws IOException, TimeoutException {
        /**
         * 配置amqp broker 连接信息
         */
        ConnectionFactory facotry = new ConnectionFactory();
        facotry.setHost("192.168.10.100");
        facotry.setPort(5672);
        facotry.setUsername("samples");
        facotry.setPassword("12345");
        facotry.setVirtualHost("vhtutl");

        //获取一个链接
        Connection conn = facotry.newConnection();
        //通过Channel进行通信
        Channel channel = conn.createChannel();

        // channel.exchangeDeclare(Send.EXCHANGE_NAME, "direct", true); //如果消费者已创建，这里可不声明
        channel.confirmSelect(); //Enables publisher acknowledgements on this channel
        channel.addConfirmListener(new ConfirmListener() {

            @Override
            public void handleNack(long deliveryTag, boolean multiple) {
                System.out.println("[handleNack] :" + deliveryTag + "," + multiple);
            }

            @Override
            public void handleAck(long deliveryTag, boolean multiple) {
                System.out.println("[handleAck] :" + deliveryTag + "," + multiple);
            }
        });

        String message = "lkl-";
        //消息持久化 MessageProperties.PERSISTENT_TEXT_PLAIN
        //发送一条消息，每条消息对应routekey都不一致
        channel.basicPublish(EXCHANGE_NAME, message, MessageProperties.PERSISTENT_TEXT_PLAIN, message.getBytes());
        System.out.println("[send] msg " + message + " of routingKey is " + message);

    }
}

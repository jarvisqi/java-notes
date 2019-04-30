package com.softmax.mq.samples.rabbit.example03;

import org.springframework.amqp.core.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;


/**
 * 发布/订阅 模式
 *
 * @author Jarvis
 * @date 2018/7/31
 */
@Profile({"tut3", "pub-sub"})
@Configuration
public class Tut3Config {

    /**
     * 定义一个 Exchange
     * FanoutExchange 把消息发送给它所知道的所有队列
     *
     * @return FanoutExchange
     */
    @Bean
    public FanoutExchange exchange() {
        return new FanoutExchange("tut3.fanout");
    }

    /**
     * 消费者的一端的配置：queues、bindings
     * AnonymousQueue 作为临时队列
     * 它是一个非持久化的、独占的、可自动删除的队列。
     * 当连接到 RabbitMQ，服务器为我们选择一个随机的队列名。
     * 当与消费者（consumer）断开连接的时候，这个队列被立即删除
     */
    @Profile("receiver03")
    private static class ReceiverConfig {

        /**
         * 临时队列 队列名随机
         *
         * @return
         */
        @Bean
        public Queue autoQueue1() {
            return new AnonymousQueue();
        }

        /**
         * 临时队列 队列名随机
         *
         * @return
         */
        @Bean
        public Queue autoQueue2() {
            return new AnonymousQueue();
        }

        /**
         * 绑定（Bindings）
         * 通知交换器将消息发送到相应的队列
         *
         * @param exchange
         * @param autoQueue1
         * @return
         */
        @Bean
        public Binding binding1(FanoutExchange exchange, Queue autoQueue1) {
            // 通知交换器将消息发送到相应的队列
            return BindingBuilder.bind(autoQueue1).to(exchange);
        }

        /**
         * 绑定（Bindings）
         * 通知交换器将消息发送到相应的队列
         *
         * @param exchange
         * @param autoQueue2
         * @return
         */
        @Bean
        public Binding binding2(FanoutExchange exchange, Queue autoQueue2) {
            // 通知交换器将消息发送到相应的队列
            return BindingBuilder.bind(autoQueue2).to(exchange);
        }

        @Bean
        public Tut3Receiver receiver() {
            return new Tut3Receiver();
        }
    }

    @Profile("sender03")
    @Bean
    public Tut3Sender sender() {
        return new Tut3Sender();
    }


}

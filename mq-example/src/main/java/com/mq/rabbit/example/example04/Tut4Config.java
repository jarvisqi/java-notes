package com.mq.rabbit.example.example04;

import org.springframework.amqp.core.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

/**
 * @author Jarvis
 * @date 2018/7/31
 */
@Profile({"tut4", "routing"})
@Configuration
public class Tut4Config {

    /**
     * 定义一个交换器
     *
     * @return
     */
    @Bean
    public DirectExchange directExchange() {
        return new DirectExchange("tut4.direct");
    }

    /**
     * 消费端 queue ,binding
     * 为每个感兴趣的颜色创建一个新的绑定
     */
    @Profile("receiver04")
    private static class ReceiverConfig {

        @Bean
        public Queue autoQueue1() {
            return new AnonymousQueue();
        }

        @Bean
        public Queue autoQueue2() {
            return new AnonymousQueue();
        }

        /**
         * 使用路由 key 绑定到 orange
         *
         * @param directExchange
         * @param autoQueue1
         * @return
         */
        @Bean
        public Binding binding1a(DirectExchange directExchange, Queue autoQueue1) {
            return BindingBuilder.bind(autoQueue1).to(directExchange).with("orange");
        }

        /**
         * 使用路由 key 绑定到 black
         *
         * @param directExchange
         * @param autoQueue1
         * @return
         */
        @Bean
        public Binding bindin1b(DirectExchange directExchange, Queue autoQueue1) {
            return BindingBuilder.bind(autoQueue1).to(directExchange).with("black");
        }

        @Bean
        public Binding bindin2a(DirectExchange directExchange, Queue autoQueue2) {
            return BindingBuilder.bind(autoQueue2).to(directExchange).with("green");
        }

        @Bean
        public Binding binding2b(DirectExchange directExchange, Queue autoQueue2) {
            return BindingBuilder.bind(autoQueue2).to(directExchange).with("black");
        }

        @Bean
        public Tut4Receiver receiver() {
            return new Tut4Receiver();
        }
    }

    @Profile("sender04")
    @Bean
    public Tut4Sender sender() {
        return new Tut4Sender();
    }
}

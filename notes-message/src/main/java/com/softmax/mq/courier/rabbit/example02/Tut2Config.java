package com.softmax.mq.courier.rabbit.example02;

import org.springframework.amqp.core.Queue;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

/**
 * @author Jarvis
 * @date 2018/7/31
 */
@Profile({"tut2", "works-queues"})
@Configuration
public class Tut2Config {

    @Bean
    public Queue queue() {
        return new Queue("works-queues");
    }

    /**
     * 定义两个消费者，并且给了他们不同的标识
     */
    @Profile("receiver02")
    private class ReceiverConfig {
        @Bean
        public Tut2Receiver receiver1() {
            return new Tut2Receiver(1);
        }

        @Bean
        public Tut2Receiver receiver2() {
            return new Tut2Receiver(2);
        }
    }

    @Profile("sender02")
    @Bean
    public Tut2Sender sender() {
        return new Tut2Sender();
    }

//    /**
//     * 更改 特定消费者
//     * 然后在特定的消费者上指定containerFactory
//     *
//     * @param rabbitConnectionFactory
//     * @return
//     */
//    @Bean
//    public RabbitListenerContainerFactory<SimpleMessageListenerContainer> prefetchOneRabbitListenerContainerFactory(ConnectionFactory rabbitConnectionFactory) {
//        SimpleRabbitListenerContainerFactory factory = new SimpleRabbitListenerContainerFactory();
//        factory.setConnectionFactory(rabbitConnectionFactory);
//        factory.setPrefetchCount(1);
//        return factory;
//    }
}

package com.mq.rabbit.example.example06;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

/**
 * @author Jarvis
 * @date 2018/8/1
 */
@Profile({"tut6", "rpc"})
@Configuration
public class Tut6Config {

    /**
     * rpc 服务端配置
     */
    @Profile("server")
    private static class ServerConfig {

        @Bean
        public Queue queue() {
            return new Queue("tut6.rpc.requests");
        }

        @Bean
        public DirectExchange directExchange() {
            return new DirectExchange("tut6.rpc");
        }

        @Bean
        public Binding binding(DirectExchange exchange, Queue queue) {
            return BindingBuilder.bind(queue).to(exchange).with("rpc");
        }

        @Bean
        public Tut6Server server() {
            return new Tut6Server();
        }
    }

    /**
     * rpc 客户端配置
     */
    @Profile("client")
    private static class ClientConfig {
        @Bean
        public DirectExchange directExchange() {
            return new DirectExchange("tut6.rpc");
        }

        @Bean
        public Tut6Client client() {
            return new Tut6Client();
        }
    }


}

package com.softmax.mq.courier.rocket.example;

import org.springframework.boot.CommandLineRunner;

public class DelayProduceTest implements CommandLineRunner {

    private static DelayProduce delayProduce = new DelayProduce();

    /**
     * 注意：RocketMQ不支持任意时间的延时，只支持以下几个固定的延时等级
     */
    private String messageDelayLevel = "1s 5s 10s 30s 1m 2m 3m 4m 5m 6m 7m 8m 9m 10m 20m 30m 1h 2h";

    @Override
    public void run(String... args) throws Exception {
        delayProduce.sendDelayMessage("delay-topic", "Hello RocketMQ", 5);
    }
}

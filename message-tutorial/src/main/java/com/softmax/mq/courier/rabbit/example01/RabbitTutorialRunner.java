package com.softmax.mq.courier.rabbit.example01;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.ConfigurableApplicationContext;


/**
 * 阻止主线程退出
 *
 * @author Jarvis
 * @date 2018/7/31
 */
public class RabbitTutorialRunner implements CommandLineRunner {

    @Value("${samples.client.duration:0}")
    private int duration;

    @Autowired
    private ConfigurableApplicationContext catx;

    @Override
    public void run(String... args) throws Exception {
        System.out.println("Ready ... running for " + duration + "ms");
        Thread.sleep(duration);
        catx.close();
    }
}

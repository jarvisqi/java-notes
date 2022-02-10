package com.softmax.mq.courier;

import com.softmax.mq.courier.rabbit.example01.RabbitTutorialRunner;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.WebApplicationType;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Profile;
import org.springframework.scheduling.annotation.EnableScheduling;

/**
 *
 */
@SpringBootApplication
@EnableScheduling
public class MQApplication {

    public static void main(String[] args) {
        // SpringApplication.run(RabbitApplication.class, args);
        new SpringApplicationBuilder()
                .sources(MQApplication.class)
                // 设置成非 web 环境
                .web(WebApplicationType.NONE)
                .run(args);
    }

    @Profile("usage_msg")
    @Bean
    public CommandLineRunner useage() {
        return args -> {
            System.out.println("This app uses Spring Profiles to control its behavior.\n");
            System.out.println("Sample usage: java -jar target/rabbitmq-samples-0.0.1-SNAPSHOT.jar --springboot.profiles.active=hello-world,sender");
        };
    }

    @Profile("!usage_msg")
    @Bean
    public CommandLineRunner runner() {
        return new RabbitTutorialRunner();
    }
}

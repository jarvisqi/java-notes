package com.softmax.oauth2resource;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;

/**
 * oauth2 resource Server 资源服务
 *
 * @author Jarvis
 * @date 2018/09/04
 */
@SpringBootApplication
@EnableResourceServer
public class Oauth2ResourceApplication {

    public static void main(String[] args) {
        SpringApplication.run(Oauth2ResourceApplication.class, args);
    }
}

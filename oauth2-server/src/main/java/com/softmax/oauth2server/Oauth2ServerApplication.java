package com.softmax.oauth2server;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;


/**
 * oauth2 server
 *
 * @author Jarvis
 * @date 2018/09/04
 */
@SpringBootApplication
@EnableAuthorizationServer
public class Oauth2ServerApplication {

    public static void main(String[] args) {
        SpringApplication.run(Oauth2ServerApplication.class, args);
    }
}

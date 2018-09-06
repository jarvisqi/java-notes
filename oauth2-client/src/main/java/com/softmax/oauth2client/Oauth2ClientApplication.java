package com.softmax.oauth2client;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.oauth2.client.EnableOAuth2Sso;

/**
 * oauth client
 *
 * @author Jarvis
 * @date 2018/09/04
 */
@SpringBootApplication
@EnableOAuth2Sso
public class Oauth2ClientApplication {

    public static void main(String[] args) {
        SpringApplication.run(Oauth2ClientApplication.class, args);
    }
}

package com.example.oauth.resource;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;

/**
 * OAuth2.0 Resource
 *
 * @author Jarvis
 * @date 2018/08/01
 */
@EnableResourceServer
@SpringBootApplication
public class OauthResourceApplication {

    public static void main(String[] args) {
        SpringApplication.run(OauthResourceApplication.class, args);
    }
}

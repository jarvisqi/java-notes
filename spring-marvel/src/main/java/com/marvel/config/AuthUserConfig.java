package com.marvel.config;

import com.marvel.security.AuthUserServiceImpl;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Bean 配置类
 *
 * @author : Jarvis
 * @date : 2018/6/13
 */
@Configuration
public class AuthUserConfig {

    @Bean
    public AuthUserServiceImpl authUserService() {
        return new AuthUserServiceImpl();
    }
}

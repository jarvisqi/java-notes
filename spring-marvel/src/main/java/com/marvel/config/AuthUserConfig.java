package com.marvel.config;

import com.marvel.mapper.AuthMapper;
import com.marvel.security.AuthUserServiceImpl;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;

/**
 * 配置类
 *
 * @author : Jarvis
 * @date : 2018/6/13
 */
@Configuration
public class AuthUserConfig {

    @Bean
    public AuthUserServiceImpl authUserService(AuthMapper authUserMapper, AuthenticationManager authenticationManager) {
        return new AuthUserServiceImpl(authUserMapper, authenticationManager);
    }
}

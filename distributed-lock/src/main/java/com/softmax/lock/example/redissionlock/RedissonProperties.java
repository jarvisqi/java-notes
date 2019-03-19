package com.softmax.lock.example.redissionlock;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

/**
 * Redisson 配置文件
 *
 * @author Jarvis
 * @date 2018/10/9
 */
@Configuration
@ConfigurationProperties(prefix = "redisson")
public class RedissonProperties {

    private String address;
    private String password;

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}

package com.softmax.tutorial.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * redis 配置信息
 *
 * @author Jarvis
 * @date 2018/8/29
 */
@Component
public class RedisProperties {

    private String host;

    @Value("${spring.redis.host}")
    public void setHost(String host) {
        this.host = host;
    }

    private String port;

    @Value("${spring.redis.port}")
    public void setPort(String port) {
        this.port = port;
    }

    private String password;

    @Value("${spring.redis.password}")
    public void setPassword(String password) {
        this.password = password;
    }

    private String timeout;

    @Value("${spring.redis.timeout}")
    public void setTimeout(String timeout) {
        this.timeout = timeout;
    }

    private String database;

    @Value("${spring.redis.database}")
    public void setDatabase(String database) {
        this.database = database;
    }

    private String maxActive;

    @Value("${spring.redis.jedis.pool.max-active}")
    public void setMaxActive(String maxActive) {
        this.maxActive = maxActive;
    }

    private String MaxWait;

    @Value("${spring.redis.jedis.pool.max-wait}")
    public void setMaxWait(String maxWait) {
        MaxWait = maxWait;
    }

    private String maxIdle;

    @Value("${spring.redis.jedis.pool.max-idle}")
    public void setMaxIdle(String maxIdle) {
        this.maxIdle = maxIdle;
    }

    private String minIdle;

    @Value("${spring.redis.jedis.pool.min-idle}")
    public void setMinIdle(String minIdle) {
        this.minIdle = minIdle;
    }

    public String getHost() {
        return host;
    }

    public String getPort() {
        return port;
    }

    public String getPassword() {
        return password;
    }

    public String getTimeout() {
        return timeout;
    }

    public String getDatabase() {
        return database;
    }

    public String getMaxActive() {
        return maxActive;
    }

    public String getMaxWait() {
        return MaxWait;
    }

    public String getMaxIdle() {
        return maxIdle;
    }

    public String getMinIdle() {
        return minIdle;
    }
}

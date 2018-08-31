package com.softmax.tutorial.properties;

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

    public static String host;

    @Value("${spring.redis.host}")
    public void setHost(String host) {
        RedisProperties.host = host;
    }

    public static String port;

    @Value("${spring.redis.port}")
    public void setPort(String port) {
        RedisProperties.port = port;
    }

    public static String password;

    @Value("${spring.redis.password}")
    public void setPassword(String password) {
        RedisProperties.password = password;
    }

    public static String timeout;

    @Value("${spring.redis.timeout}")
    public void setTimeout(String timeout) {
        RedisProperties.timeout = timeout;
    }

    public static String database;

    @Value("${spring.redis.database}")
    public void setDatabase(String database) {
        RedisProperties.database = database;
    }

    public static String maxActive;

    @Value("${spring.redis.jedis.pool.max-active}")
    public void setMaxActive(String maxActive) {
        RedisProperties.maxActive = maxActive;
    }

    public static String maxWait;

    @Value("${spring.redis.jedis.pool.max-wait}")
    public void setMaxWait(String maxWait) {
        RedisProperties.maxWait = maxWait;
    }

    public static String maxIdle;

    @Value("${spring.redis.jedis.pool.max-idle}")
    public void setMaxIdle(String maxIdle) {
        RedisProperties.maxIdle = maxIdle;
    }

    public static String minIdle;

    @Value("${spring.redis.jedis.pool.min-idle}")
    public void setMinIdle(String minIdle) {
        RedisProperties.minIdle = minIdle;
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
        return maxWait;
    }

    public String getMaxIdle() {
        return maxIdle;
    }

    public String getMinIdle() {
        return minIdle;
    }
}

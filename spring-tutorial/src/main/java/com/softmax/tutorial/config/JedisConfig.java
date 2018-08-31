package com.softmax.tutorial.config;

import com.softmax.tutorial.properties.RedisProperties;
import org.springframework.stereotype.Component;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;


/**
 * Redis  配置
 *
 * @author Jarvis
 * @date 2018/8/29
 */
@Component
public class JedisConfig {
    private static volatile JedisPool jedisPool = null;
    private static Lock lock = new ReentrantLock();

    private JedisConfig() {

    }

    public static JedisPool getJedisPoolInstance() {
        int portIntger = Integer.parseInt(RedisProperties.port);
        int timeoutIntger = Integer.parseInt(RedisProperties.timeout);
        int databaseIntger = Integer.parseInt(RedisProperties.database);

        JedisPoolConfig config = new JedisPoolConfig();
        // 控制一个pool可分配多少个jedis实例，通过pool.getResource()来获取；
        // 如果赋值为-1，则表示不限制；如果pool已经分配了maxActive个jedis实例，则此时pool的状态为exhausted(耗尽)。
        config.setMaxTotal(Integer.parseInt(RedisProperties.maxActive));
        // 控制一个pool最多有多少个状态为idle(空闲的)的jedis实例。
        config.setMaxIdle(Integer.parseInt(RedisProperties.maxIdle));
        // 表示当borrow(引入)一个jedis实例时，最大的等待时间，如果超过等待时间，则直接抛出JedisConnectionException；
        config.setMaxWaitMillis(Integer.parseInt(RedisProperties.maxWait));
        config.setMinIdle(Integer.parseInt(RedisProperties.minIdle));

        try {
            if (jedisPool == null) {
                lock.lock();
                if (null == jedisPool) {
                    jedisPool = new JedisPool(config, RedisProperties.host, portIntger, timeoutIntger, RedisProperties.password, databaseIntger);
                }
                lock.unlock();
            }
        } catch (Exception e) {
            e.printStackTrace();
            lock.unlock();
        } finally {

        }

        return jedisPool;
    }

    /**
     * 释放资源
     */
    public static synchronized void release() {
        if (jedisPool != null) {
            jedisPool.close();
        }
    }
}

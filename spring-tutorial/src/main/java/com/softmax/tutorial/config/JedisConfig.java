package com.softmax.tutorial.config;

import org.springframework.stereotype.Component;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;


/**
 * @author Jarvis
 * @date 2018/8/29
 */
@Component
public class JedisConfig {
    private static volatile JedisPool jedisPool = null;

    private JedisConfig() {

    }

    public static JedisPool getJedisPoolInstance() {

        if (jedisPool == null) {
            synchronized (JedisConfig.class) {
                if (null == jedisPool) {

                    RedisProperties properties = new RedisProperties();

                    int portIntger = Integer.parseInt(properties.getPort());
                    int timeoutIntger = Integer.parseInt(properties.getTimeout());
                    int databaseIntger = Integer.parseInt(properties.getDatabase());

                    JedisPoolConfig config = new JedisPoolConfig();
                    // 控制一个pool可分配多少个jedis实例，通过pool.getResource()来获取；
                    // 如果赋值为-1，则表示不限制；如果pool已经分配了maxActive个jedis实例，则此时pool的状态为exhausted(耗尽)。
                    config.setMaxTotal(Integer.parseInt(properties.getMaxActive()));
                    // 控制一个pool最多有多少个状态为idle(空闲的)的jedis实例。
                    config.setMaxIdle(Integer.parseInt(properties.getMaxIdle()));
                    // 表示当borrow(引入)一个jedis实例时，最大的等待时间，如果超过等待时间，则直接抛出JedisConnectionException；
                    config.setMaxWaitMillis(Integer.parseInt(properties.getMaxWait()));
                    config.setMinIdle(Integer.parseInt(properties.getMinIdle()));

                    jedisPool = new JedisPool(config, properties.getHost(), portIntger, timeoutIntger, properties.getPassword(), databaseIntger);
                }
            }
        }
        return jedisPool;
    }

    /**
     * 释放
     *
     * @param jedisPool 释放哪个池中
     * @param jedis     的哪个对象
     */
    public static void release(JedisPool jedisPool, Jedis jedis) {
        if (jedis != null) {
            if (jedisPool != null) {
                jedisPool.close();
            }
        }
    }
}

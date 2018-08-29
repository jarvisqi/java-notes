package com.softmax.tutorial.util;

import com.softmax.tutorial.config.JedisConfig;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

/**
 * @author Jarvis
 * @date 2018/8/29
 */
public class JedisUtil {
    protected final static Logger logger = LoggerFactory.getLogger(JedisUtil.class);

    /**
     * 对某个键的值自增wocai
     *
     * @param key
     * @param expireDate
     * @return
     */
    public static long setIncr(String key, int expireDate) {
        JedisPool jedisPool = JedisConfig.getJedisPoolInstance();
        long result = 0;
        Jedis jedis = null;
        try {
            jedis = jedisPool.getResource();
            result = jedis.incr(key);
            if (expireDate != 0) {
                jedis.expire(key, expireDate);
            }
        } catch (Exception e) {
            logger.warn("set " + key + " = " + result);
        } finally {
            JedisConfig.release(jedisPool,jedis);
        }
        return result;
    }
}

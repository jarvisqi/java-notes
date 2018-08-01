package com.example.distributedlock.redislock;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DataAccessException;
import org.springframework.data.redis.connection.RedisConnection;
import org.springframework.data.redis.core.RedisCallback;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.StringRedisSerializer;

/**
 * redis 分布式锁
 *
 * @author Jarvis
 * @date 2018/8/1
 */
public class RedisLock {

    private static Logger logger = LoggerFactory.getLogger(RedisLock.class);

    private RedisTemplate<String, Object> redisTemplate;

    private static final int DEFAULT_ACQUIRY_RESOLUTION_MILLIS = 100;

    private String lockKey;

    /**
     * 锁超时时间，防止线程在入锁以后，无限的执行等待
     */
    private int expireMsecs = 60 * 1000;
    /**
     * 锁等待时间，防止线程饥饿
     */
    private int timeoutMsecs = 10 * 1000;

    private volatile boolean locked = false;

    public RedisLock(RedisTemplate<String, Object> redisTemplate, String lockKey) {
        this.redisTemplate = redisTemplate;
        this.lockKey = lockKey + "_lock";
    }

    public RedisLock(RedisTemplate<String, Object> redisTemplate, String lockKey, int timeoutMsecs) {
        this(redisTemplate, lockKey);
        this.timeoutMsecs = timeoutMsecs;
    }

    public RedisLock(RedisTemplate<String, Object> redisTemplate, String lockKey, int timeoutMsecs, int expireMsecs) {
        this(redisTemplate, lockKey, timeoutMsecs);
        this.expireMsecs = expireMsecs;
    }

    public String getLockKey() {
        return lockKey;
    }

    /**
     * get 取值
     *
     * @param key
     * @return
     */
    public String get(final String key) {
        Object object = null;
        try {
            object = redisTemplate.execute(new RedisCallback<Object>() {
                @Override
                public Object doInRedis(RedisConnection redisConnection) throws DataAccessException {
                    StringRedisSerializer redisSerializer = new StringRedisSerializer();
                    byte[] data = redisConnection.get(redisSerializer.serialize(key));
                    redisConnection.close();
                    if (data == null) {
                        return null;
                    }
                    return redisSerializer.deserialize(data);
                }
            });
        } catch (Exception ex) {
            logger.error("get redis error, key : {}", key);
        }

        return object != null ? object.toString() : null;
    }

    /**
     * set 设值
     *
     * @param key
     * @param value
     * @return
     */
    public String set(final String key, final String value) {
        Object object = null;
        try {
            object = redisTemplate.execute(new RedisCallback<Object>() {
                @Override
                public Object doInRedis(RedisConnection redisConnection) throws DataAccessException {
                    StringRedisSerializer redisSerializer = new StringRedisSerializer();
                    redisConnection.set(redisSerializer.serialize(key), redisSerializer.serialize(value));
                    return redisSerializer;
                }
            });
        } catch (Exception e) {
            logger.error("get redis error, key : {}", key);
        }
        return object != null ? object.toString() : null;
    }

    /**
     * setNX
     *
     * @param key
     * @param value
     * @return
     */
    public boolean setNX(final String key, final String value) {
        Object object = null;
        try {
            object = redisTemplate.execute(new RedisCallback<Object>() {
                @Override
                public Object doInRedis(RedisConnection redisConnection) throws DataAccessException {
                    StringRedisSerializer redisSerializer = new StringRedisSerializer();
                    Boolean success = redisConnection.setNX(redisSerializer.serialize(key), redisSerializer.serialize(value));
                    redisConnection.close();
                    return success;
                }
            });
        } catch (Exception e) {
            logger.error("setNX redis error, key : {}", key);
        }
        return object != null ? (Boolean) object : false;
    }

    private String getSet(final String key, final String value) {
        Object object = null;
        try {
            object = redisTemplate.execute(new RedisCallback<Object>() {
                @Override
                public Object doInRedis(RedisConnection redisConnection) throws DataAccessException {
                    StringRedisSerializer redisSerializer = new StringRedisSerializer();
                    byte[] data = redisConnection.getSet(redisSerializer.serialize(key), redisSerializer.serialize(value));
                    redisConnection.close();

                    return redisSerializer.deserialize(data);
                }
            });
        } catch (Exception e) {
            logger.error("getSet redis error, key : {}", key);
        }
        return object != null ? object.toString() : null;
    }

    /**
     * 获得 lock.
     *
     * @return
     * @throws InterruptedException
     */
    public synchronized boolean lock() throws InterruptedException {
        int timeout = timeoutMsecs;
        while (timeout >= 0) {
            long expires = System.currentTimeMillis() + expireMsecs + 1;
            // 锁到期时间
            String expiresStr = String.valueOf(expires);
            if (this.setNX(this.lockKey, expiresStr)) {
                locked = true;
                return true;
            }
            // redis里的时间
            String currentValueStr = this.get(this.lockKey);
            if (currentValueStr != null && Long.parseLong(currentValueStr) < System.currentTimeMillis()) {
                String oldValueStr = this.getSet(this.lockKey, expiresStr);
                // 获取上一个锁到期时间，并设置现在的锁到期时间，
                // 只有一个线程才能获取上一个线上的设置时间，因为jedis.getSet是同步的
                if (oldValueStr != null && oldValueStr.equals(currentValueStr)) {
                    // 防止误删（覆盖，因为key是相同的）了他人的锁——这里达不到效果，这里值会被覆盖，但是因为相差了很少的时间，所以可以接受
                    // [分布式的情况下]:如果这个时候，多个线程恰好都到了这里，但是只有一个线程的设置值和当前值相同，才有权利获取锁
                    locked = true;
                    return true;
                }
                timeout -= DEFAULT_ACQUIRY_RESOLUTION_MILLIS;
                /*
                 * 延迟100 毫秒, 这里使用随机时间可能会好一点,可以防止饥饿进程的出现,即,当同时到达多个进程,
                 * 只会有一个进程获得锁,其他的都用同样的频率进行尝试,后面有来了一些进行,也以同样的频率申请锁,这将可能导致前面来的锁得不到满足.
                 * 使用随机的等待时间可以一定程度上保证公平性
                 */
                Thread.sleep(DEFAULT_ACQUIRY_RESOLUTION_MILLIS);

            }
        }
        return false;
    }

    /**
     * 释放锁
     */
    public synchronized void unlock() {
        if (locked) {
            redisTemplate.delete(this.lockKey);
            locked = false;
        }
    }


}

package com.example.distributedlock.redislock;


import org.springframework.data.redis.core.RedisTemplate;

/**
 * @author Jarvis
 * @date 2018/8/2
 */
public class MsThread extends Thread {

    private MsService service;
    private String key;
    private RedisTemplate<String, Object> redisTemplate;

    public MsThread(MsService service, RedisTemplate<String, Object> redisTemplate, String key) {
        this.service = service;
        this.key = key;
        this.redisTemplate = redisTemplate;
    }

    @Override
    public void run() {
        service.seckill(redisTemplate, key);
    }

}

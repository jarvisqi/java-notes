package com.softmax.lock.samples;

import com.softmax.lock.samples.redissionlock.RedisUtils;
import com.softmax.lock.samples.redissionlock.RedissonProperties;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.redisson.api.RBucket;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.redisson.config.Config;
import org.redisson.config.SingleServerConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.concurrent.TimeUnit;

/**
 * @author Jarvis
 * @date 2018/10/9
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class RedissonTest {

    @Autowired
    private RedissonProperties redssionProperties;

    @Test
    public void test() throws InterruptedException {
        Config config = new Config();
        SingleServerConfig singleServerConfig = config.useSingleServer();
        singleServerConfig.setAddress(redssionProperties.getAddress());
        singleServerConfig.setPassword(redssionProperties.getPassword());

        RedissonClient redissonClient = RedisUtils.getInstance().getRedisson(config);
        RBucket<Object> rBucket = RedisUtils.getInstance().getRBucket(redissonClient, "key");

        System.out.println(rBucket.get());
        while (true) {
            RLock lock = redissonClient.getLock("com.lock");
            lock.tryLock(0, 1, TimeUnit.SECONDS);
            try {
                System.out.println("执行");
            } finally {
                lock.unlock();
            }
        }


    }

}

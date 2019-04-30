package com.softmax.lock.samples;

import com.softmax.lock.samples.redislock.Info;
import com.softmax.lock.samples.redislock.MsService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@RunWith(SpringRunner.class)
@SpringBootTest
public class DistributedlockApplicationTests {

    @Autowired
    private RedisTemplate<String, Object> redisTemplate;

    @Autowired
    private MsService service;

    @Test
    public void set() {
        redisTemplate.opsForValue().set("MSKey", 10013);
    }


    @Test
    public void get() {
        Object val = redisTemplate.opsForValue().get("MSKey");
        if (val != null) {
            System.out.println(val.toString());
        }
        Integer rev = Integer.valueOf(val.toString());
        System.out.println("Redis GET: " + rev);
    }

    @Test
    public void delete() {
        boolean val = redisTemplate.delete("MSKey");
        System.out.println("Redis deleted: " + val);
    }


    @Test
    public void hash() throws InterruptedException {
        HashOperations hashOperations = redisTemplate.opsForHash();
        Info info1 = new Info(1001, "Hong");
        Info info2 = new Info(1002, "Kong");
        //is exist
        if (hashOperations.getOperations().hasKey("info_1001")) {
            //delete
            hashOperations.delete("info_1001", "1001");
            hashOperations.delete("info_1002", "1002");
            Thread.sleep(3000);
        }
        //put
        hashOperations.put("info_1001", "1001", info1);
        hashOperations.put("info_1002", "1002", info2);
        //get
        Info info = (Info) hashOperations.get("info_1001", "1001");

        System.out.println();
        System.out.println(info);


    }

    @Test
    public void seckill() {
        System.out.println("开始");
        //创建线程池
        ExecutorService executorService = Executors.newFixedThreadPool(1);
        executorService.execute(() -> service.seckill(redisTemplate, "MSKEY"));
    }


}


package com.softmax.distributedlock;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * 分布式锁
 *
 * @author Jarvis
 * @date 2018/08/01
 */
@SpringBootApplication
public class DistributedlockApplication {

    public static void main(String[] args) {
        SpringApplication.run(DistributedlockApplication.class, args);
    }
}

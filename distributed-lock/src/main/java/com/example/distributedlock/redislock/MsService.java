package com.example.distributedlock.redislock;

import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

/**
 * 模拟秒杀服务
 *
 * @author Jarvis
 * @date 2018/8/2
 */
@Service
public class MsService {

    /***
     * 抢购代码
     * @param key pronum 首先用客户端设置数量
     * @return
     */
    public boolean seckill(RedisTemplate<String, Object> redisTemplate, String key) {
        //定义锁
        RedisLock redisLock = new RedisLock(redisTemplate, key, 10000, 20000);
        try {
            if (redisLock.lock()) {
                // 需要加锁的代码
                String inventory_num = redisLock.get("inventory_num");
                //修改库存
                if (Integer.parseInt(inventory_num) - 1 >= 0) {
                    redisLock.set("inventory_num", String.valueOf(Integer.parseInt(inventory_num) - 1));
                    System.out.println("库存数量:" + inventory_num + "  成功!!!" + Thread.currentThread().getName());
                } else {
                    System.out.println("手慢了，被抢光了 ┭┮﹏┭┮");
                }
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            // 为了让分布式锁的算法更稳键些，持有锁的客户端在解锁之前应该再检查一次自己的锁是否已经超时，再去做DEL操作，因为可能客户端因为某个耗时的操作而挂起，
            // 操作完的时候锁因为超时已经被别人获得，这时就不必解锁了(没实现)。

            redisLock.unlock();
        }
        return true;
    }
}

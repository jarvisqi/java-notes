package com.softmax.marvel.service.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.retry.annotation.Backoff;
import org.springframework.retry.annotation.Recover;
import org.springframework.retry.annotation.Retryable;
import org.springframework.stereotype.Service;

import java.time.LocalTime;

/**
 * @author Jarvis
 */
@Service
public class GoodsService {

    private Logger logger = LoggerFactory.getLogger(getClass());
    private final int totalNum = 100000;

    /**
     * Retryable 的方法在本类中使用没有效果，只有在其他类中使用@Autowired注入或者@Bean才能生效
     * <p>
     * value:指定发生的异常进行重试
     * include:和value一样，默认空，当exclude也为空时，所有异常都重试
     * exclude:指定异常不重试，默认空，当include也为空时，所有异常都重试
     * maxAttemps:最大重试次数，默认3次
     * backoff:重试补偿机制，默认没有,value默认为1000L，我们设置为2000L；multiplier（指定延迟倍数）默认为0，表示固定暂停1秒后进行重试，
     * 如果把multiplier设置为1.5，则第一次重试为2秒，第二次为3秒，第三次为4.5秒。
     * recover - 兜底方法，即多次重试后还是失败就会执行这个方法
     *
     * @param num
     * @return
     */
    @Retryable(value = Exception.class, maxAttempts = 5, backoff = @Backoff(delay = 2000L, multiplier = 1.5))
    public int minGoodsnum(int num) {
        logger.info("减库存开始" + LocalTime.now());
        try {
            int i = 1 / 0;
        } catch (Exception e) {
            logger.error("illegal");
        }
        if (num <= 0) {
            throw new IllegalArgumentException("数量不对");
        }
        logger.info("减库存执行结束" + LocalTime.now());
        return totalNum - num;
    }


    @Recover
    public int recoverMinGoodsnum(Exception e) {
        logger.warn("减库存失败！！！" + LocalTime.now());
        //记日志到数据库
        return totalNum;
    }
}

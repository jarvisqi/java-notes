package com.softmax.tutorial.service;

import com.softmax.tutorial.util.JedisUtil;

/**
 * 限流服务
 *
 * @author Jarvis
 * @date 2018/8/29
 */
public class LimitService {

    /**
     * api 限流
     *
     * @param apiKey     api名字（全名）
     * @param count      次数(指定时间内的，和下面时间相关)
     * @param expireDate 时间(秒)
     * @return
     */
    public boolean limitApiRequest(String apiKey, int count, int expireDate) {
        String key = "limit_request_" + apiKey;
        long total = JedisUtil.setIncr(key, expireDate);
        boolean refuse = total > count;
        if (refuse) {
            JedisUtil.release();
        }
        return refuse;
    }
}

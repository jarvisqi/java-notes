package com.softmax.lock.samples.idempotence.impl;

import com.softmax.lock.samples.idempotence.TokenService;
import com.softmax.lock.samples.redissionlock.RedisUtils;
import com.softmax.lock.samples.redissionlock.RedissonProperties;
import org.redisson.api.RSet;
import org.redisson.api.RedissonClient;
import org.redisson.config.Config;
import org.redisson.config.SingleServerConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.util.StringUtils;
import org.springframework.web.client.HttpClientErrorException;

import javax.servlet.http.HttpServletRequest;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

/**
 * 此类有问题，不想写了 好困(o-ωｑ)).oO 困
 */
public class TokenServiceImpl implements TokenService {

    private RedissonClient redissonClient;

    private static final String TOKEN_KEY = "idempotent_token";

    @Autowired
    private RedissonProperties redssionProperties;

    public TokenServiceImpl() {
        Config config = new Config();
        SingleServerConfig singleServerConfig = config.useSingleServer();
        singleServerConfig.setAddress(redssionProperties.getAddress());
        singleServerConfig.setPassword(redssionProperties.getPassword());

        redissonClient = RedisUtils.getInstance().getRedisson(config);
    }

    @Override
    public String createToken() {
        String uuid = UUID.randomUUID().toString().replace("-", "");
        StringBuffer token = new StringBuffer();
        token.append(TOKEN_KEY).append(uuid);
        redissonClient.getSet(uuid).expire(200, TimeUnit.SECONDS);
        return token.toString();
    }

    @Override
    public void checkToken(HttpServletRequest request) {
        String token = request.getHeader(TOKEN_KEY);
        // header中不存在token
        if (StringUtils.isEmpty(token)) {
            token = request.getParameter(TOKEN_KEY);
            // parameter中也不存在token
            if (StringUtils.isEmpty(token)) {
                throw new HttpClientErrorException(HttpStatus.BAD_REQUEST, "token不存在");
            }
        }
        RSet<String> tokenSet = redissonClient.getSet(TOKEN_KEY);
        if (!tokenSet.isExists()) {
            throw new HttpClientErrorException(HttpStatus.BAD_REQUEST, "请勿重复请求");
        }

        if (tokenSet.remove(TOKEN_KEY)) {
            throw new HttpClientErrorException(HttpStatus.BAD_REQUEST, "请勿重复请求");
        }
    }
}

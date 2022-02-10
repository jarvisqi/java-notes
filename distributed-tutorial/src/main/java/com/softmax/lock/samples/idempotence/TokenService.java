package com.softmax.lock.samples.idempotence;

import org.springframework.web.servlet.function.ServerResponse;

import javax.servlet.http.HttpServletRequest;

public interface TokenService {

    /**
     * 创建token
     *
     * @return
     */
    String createToken();

    /**
     * 检查token
     *
     * @param request
     */
    void checkToken(HttpServletRequest request);
}

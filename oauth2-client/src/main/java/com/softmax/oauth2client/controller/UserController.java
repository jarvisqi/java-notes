package com.softmax.oauth2client.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author Jarvis
 * @date 2018/9/4
 */
@RestController
public class UserController {
    private static final Logger log = LoggerFactory.getLogger(UserController.class);

    /**
     * sso测试接口
     *
     * @param authentication
     * @return
     */
    @RequestMapping("/user")
    public Authentication getUser(Authentication authentication) {
        log.info("auth : {}", authentication);
        return authentication;

    }
}

package com.softmax.marvel.event;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Service;

/**
 * 用户注册，执行创建邮箱逻辑
 *
 * @author Jarvis
 */
@Service
public class EmailService implements ApplicationListener<UserRegisterEvent> {

    private Logger logger = LoggerFactory.getLogger(ApplicationListener.class);

    @Override
    public void onApplicationEvent(UserRegisterEvent userRegisterEvent) {
        // 执行创建邮箱逻辑
        logger.info("[EmailService][给用户({}) 创建邮箱]", userRegisterEvent.getUsername());
    }
}

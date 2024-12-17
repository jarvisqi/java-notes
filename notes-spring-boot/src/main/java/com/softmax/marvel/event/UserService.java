package com.softmax.marvel.event;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.ApplicationEventPublisherAware;
import org.springframework.stereotype.Service;

/**
 * 用户注册服务类，实现 ApplicationEventPublisherAware 接口，从而将 ApplicationEventPublisher 注入进来
 *
 * @author Jarvis
 */
@Service
public class UserService implements ApplicationEventPublisherAware {
    private Logger logger = LoggerFactory.getLogger(getClass());

    private ApplicationEventPublisher applicationEventPublisher;

    @Override
    public void setApplicationEventPublisher(ApplicationEventPublisher applicationEventPublisher) {
        this.applicationEventPublisher = applicationEventPublisher;
    }

    public void register(String username) {
        // <1>... 执行注册逻辑
        logger.info("[register][执行用户({}) 的注册逻辑]", username);
        // <2> ... 发布事件
        applicationEventPublisher.publishEvent(new UserRegisterEvent(this, username));

    }
}

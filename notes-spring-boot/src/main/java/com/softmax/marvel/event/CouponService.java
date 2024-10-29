package com.softmax.marvel.event;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Service;

/**
 * 用户注册，发放优惠券
 */
@Service
public class CouponService {

    private Logger logger = LoggerFactory.getLogger(CouponService.class);

    @EventListener
    public void addCoupon(UserRegisterEvent event) {
        logger.info("[addCoupon][给用户({}) 发放优惠劵]", event.getUsername());
    }
}

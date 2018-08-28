package com.softmax.tutorial.entity.enums;

import com.softmax.framework.common.BaseEnum;

/**
 * 用户级别
 *
 * @author : Jarvis
 * @date : 2018/5/31
 */
public enum UserLevel implements BaseEnum {

    ORDINARY(0, "普通用户"),

    MEMBER(1, "会员"),

    VIP(2, "vip用户"),

    SVIP(3, "超级vip用户");

    Integer value;
    String description;

    UserLevel(Integer value, String description) {
        this.value = value;
        this.description = description;
    }

}

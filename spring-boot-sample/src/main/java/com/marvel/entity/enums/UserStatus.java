package com.marvel.entity.enums;

import com.framework.common.BaseEnum;

/**
 * 用户状态
 *
 * @author : Jarvis
 * @date : 2018/5/12
 */
public enum UserStatus implements BaseEnum {

    NORMAL(0, "正常"),

    DISABLED(1, "禁用"),

    BANNEDPOST(2, "禁言");

    Integer value;
    String description;

    UserStatus(Integer value, String description) {
        this.value = value;
        this.description = description;
    }
}
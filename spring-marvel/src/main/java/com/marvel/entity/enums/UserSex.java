package com.marvel.entity.enums;

import com.framework.common.BaseEnum;


/**
 * 性别
 *
 * @author : Jarvis
 * @date : 2018/5/12
 */
public enum UserSex implements BaseEnum {

    MAN(0, "男"),
    WOMAN(1, "女");

    Integer value;
    String description;

    UserSex(Integer value, String description) {
        this.value = value;
        this.description = description;
    }
}


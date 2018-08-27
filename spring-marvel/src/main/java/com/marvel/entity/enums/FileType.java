package com.marvel.entity.enums;

import com.framework.common.BaseEnum;

/**
 * 存储的文件格式
 *
 * @author Jarvis
 * @date 2018/8/24
 */
public enum FileType implements BaseEnum {

    STRINGFormat(0, "字符"),

    JSONFormat(1, "json字符串"),

    XMLFormat(2, "xml字符串");

    Integer value;
    String description;

    FileType(Integer value, String description) {
        this.value = value;
        this.description = description;
    }
}

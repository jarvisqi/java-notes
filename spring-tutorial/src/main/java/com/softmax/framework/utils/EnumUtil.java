package com.softmax.framework.utils;


import com.softmax.framework.common.BaseEnum;

/**
 * 枚举转换
 *
 * @author : Jarvis
 * @date : 2018/5/30
 */
public class EnumUtil {

    public static <T extends Enum<?> & BaseEnum> T valueOf(Class<T> enumClass, int value) {
        T[] enumConstants = enumClass.getEnumConstants();
        for (T e : enumConstants) {
            if (e.getValue() == value) {
                return e;
            }
        }
        return null;
    }
}

package com.test.impl;

import com.test.PropertiesCopier;
import org.springframework.beans.BeanUtils;

/**
 * Spring BeanUtils 实现
 */
public class SpringBeanUtilsPropertiesCopier implements PropertiesCopier {
    @Override
    public void copyProperties(Object source, Object target) throws Exception {
        BeanUtils.copyProperties(source, target);
    }
}

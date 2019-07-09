package com.test.impl;

import com.test.PropertiesCopier;
import org.apache.commons.beanutils.BeanUtils;

/**
 * Apache BeanUtils й╣ож
 */
public class CommonsBeanUtilsPropertiesCopier implements PropertiesCopier {
    @Override
    public void copyProperties(Object source, Object target) throws Exception {
        BeanUtils.copyProperties(target, source);
    }
}

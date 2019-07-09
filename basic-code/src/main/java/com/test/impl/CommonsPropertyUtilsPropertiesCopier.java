package com.test.impl;

import com.test.PropertiesCopier;
import org.apache.commons.beanutils.PropertyUtils;

/**
 * Apache PropertyUtils й╣ож
 */
public class CommonsPropertyUtilsPropertiesCopier implements PropertiesCopier {

    @Override
    public void copyProperties(Object source, Object target) throws Exception {
        PropertyUtils.copyProperties(target, source);
    }
}

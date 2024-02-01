package com.test.impl;

import com.test.PropertiesCopier;
import org.springframework.cglib.beans.BeanCopier;

/**
 * ???copy???
 */
public class CglibBeanCopierProperties implements PropertiesCopier {
    /**
     * Cglib???
     *
     * @param source
     * @param target
     * @throws Exception
     */
    @Override
    public void copyProperties(Object source, Object target) throws Exception {

        BeanCopier beanCopier = BeanCopier.create(source.getClass(), target.getClass(), false);
        beanCopier.copy(source, target, null);
    }
}

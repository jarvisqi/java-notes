package com.test.impl;

import com.test.PropertiesCopier;
import org.springframework.cglib.beans.BeanCopier;

/**
 * ʵ��copy�ӿ�
 */
public class CglibBeanCopierProperties implements PropertiesCopier {
    /**
     * Cglibʵ��
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

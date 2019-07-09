package com.test.impl;

import com.test.Account;
import com.test.PropertiesCopier;
import org.springframework.cglib.beans.BeanCopier;

/**
 * 全局静态 BeanCopier，避免每次都生成新的对象
 */
public class StaticCglibBeanCopierProperties implements PropertiesCopier {

    private static BeanCopier copier = BeanCopier.create(Account.class, Account.class, false);

    @Override
    public void copyProperties(Object source, Object target) throws Exception {
        copier.copy(source, target, null);
    }
}

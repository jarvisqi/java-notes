package com.test.impl;

import com.test.Account;
import com.test.PropertiesCopier;
import org.springframework.cglib.beans.BeanCopier;

/**
 * ȫ�־�̬ BeanCopier������ÿ�ζ������µĶ���
 */
public class StaticCglibBeanCopierProperties implements PropertiesCopier {

    private static final BeanCopier copier = BeanCopier.create(Account.class, Account.class, false);

    @Override
    public void copyProperties(Object source, Object target) throws Exception {
        copier.copy(source, target, null);
    }
}

package com.softmax.spring.service.impl;

import com.softmax.spring.dao.AccountDao;
import com.softmax.spring.service.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author Jarvis
 * @date 2018/7/5
 */
public class AccountServiceImpl implements AccountService {
    /**
     * 需要注入的对象
     */
    @Autowired
    private AccountDao accountDao;

    /**
     * Setter注入方式
     *
     * @param -accountDao
     */
//    public void setAccountDao(AccountDao accountDao) {
//        this.accountDao = accountDao;
//    }
    @Override
    public void doSomething() {
        System.out.println("AccountServiceImpl#doSomething......");
        accountDao.addAccount();
    }

    @Override
    @Transactional(rollbackFor = RuntimeException.class)
    public void saveSomething() {
        System.out.println("进入A方法");
        deleSomething();
        System.out.println("完成");
    }


    private void deleSomething() {
        System.out.println("进入B方法");
        throw new RuntimeException("error");
    }
}

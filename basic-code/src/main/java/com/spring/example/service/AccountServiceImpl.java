package com.spring.example.service;

import com.spring.example.dao.AccountDao;
import org.springframework.beans.factory.annotation.Autowired;

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
}

package com.softmax.spring.config;

import com.softmax.spring.dao.AccountDaoImpl;
import com.softmax.spring.dao.UserDao;
import com.softmax.spring.dao.AccountDao;
import com.softmax.spring.dao.UserDaoImpl;
import com.softmax.spring.service.AccountService;
import com.softmax.spring.service.impl.AccountServiceImpl;
import com.softmax.spring.service.UserService;
import com.softmax.spring.service.impl.UserServiceImpl;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author Jarvis
 * @date 2018/7/5
 */
@Configuration
public class BeanConfiguration {

    @Bean
    public AccountDao accountDao() {
        return new AccountDaoImpl();
    }

    @Bean
    public AccountService accountService() {
        AccountServiceImpl bean = new AccountServiceImpl();
//        bean.setAccountDao(accountDao());
        return bean;
    }


    @Bean
    public UserDao userDao() {
        return new UserDaoImpl();
    }

    @Bean
    public UserService userService() {
        //使用构造函数注入
//        UserServiceImpl bean = new UserServiceImpl(userDao());
//        //set注入
//        bean.setUserDao(userDao());
        UserServiceImpl bean = new UserServiceImpl();
        return bean;

    }

}

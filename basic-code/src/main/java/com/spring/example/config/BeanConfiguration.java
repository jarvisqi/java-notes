package com.spring.example.config;

import com.spring.example.dao.AccountDao;
import com.spring.example.dao.AccountDaoImpl;
import com.spring.example.dao.UserDao;
import com.spring.example.dao.UserDaoImpl;
import com.spring.example.service.AccountService;
import com.spring.example.service.AccountServiceImpl;
import com.spring.example.service.UserService;
import com.spring.example.service.UserServiceImpl;
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

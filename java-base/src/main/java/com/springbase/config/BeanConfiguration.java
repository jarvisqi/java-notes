package com.springbase.config;

import com.springbase.dao.AccountDao;
import com.springbase.dao.AccountDaoImpl;
import com.springbase.dao.UserDao;
import com.springbase.dao.UserDaoImpl;
import com.springbase.service.AccountService;
import com.springbase.service.AccountServiceImpl;
import com.springbase.service.UserService;
import com.springbase.service.UserServiceImpl;
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

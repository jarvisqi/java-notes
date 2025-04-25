package com.softmax.mvcframe.config;

import com.softmax.mvcframe.dao.AccountDaoImpl;
import com.softmax.mvcframe.dao.UserDao;
import com.softmax.mvcframe.dao.AccountDao;
import com.softmax.mvcframe.dao.UserDaoImpl;
import com.softmax.mvcframe.service.AccountService;
import com.softmax.mvcframe.service.impl.AccountServiceImpl;
import com.softmax.mvcframe.service.UserService;
import com.softmax.mvcframe.service.impl.UserServiceImpl;
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
        //        bean.setAccountDao(accountDao());
        return new AccountServiceImpl();
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
        return new UserServiceImpl();

    }

}

package com.springbase.config;

import com.springbase.dao.AccountDao;
import com.springbase.dao.AccountDaoImpl;
import com.springbase.service.AccountService;
import com.springbase.service.AccountServiceImpl;
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

}

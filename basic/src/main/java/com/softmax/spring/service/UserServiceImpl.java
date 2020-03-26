package com.softmax.spring.service;

import com.softmax.spring.dao.UserDao;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * @author Jarvis
 * @date 2018/7/5
 */
public class UserServiceImpl implements UserService {
    /**
     * 标注成员变量
     */
    @Autowired
    private UserDao userDao;

    /**
     * 标注构造方法
     *
     * @param -userDao
     */
//    @Autowired
//    public UserServiceImpl(UserDao userDao) {
//        this.userDao = userDao;
//    }

//    /**
//     * 标注set方法
//     *
//     * @param userDao
//     */
//    @Autowired
//    public void setUserDao(UserDao userDao) {
//        this.userDao = userDao;
//    }
    @Override
    public void done() {
        System.out.println("UserServiceImpl:done()......");
        userDao.done();
    }
}

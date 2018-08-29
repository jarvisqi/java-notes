package com.softmax.tutorial.controller;

import com.softmax.tutorial.mapper.master.UserMapper;
import com.softmax.tutorial.mapper.slave.SystemConfigMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author Jarvis
 * @date 2018/8/29
 */
@RestController
public class UserController {

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private SystemConfigMapper configMapper;


    @RequestMapping(value = "/users", method = RequestMethod.GET)
    public List<?> getAllUser() {
        return userMapper.getAll();
    }

    @RequestMapping(value = "/config", method = RequestMethod.GET)
    public List<?> getAllConfig() {
        return configMapper.getAll();
    }


}

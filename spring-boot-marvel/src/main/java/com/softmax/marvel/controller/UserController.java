package com.softmax.marvel.controller;

import com.framework.common.AjaxResult;
import com.softmax.marvel.entity.UserInfo;
import com.softmax.marvel.service.impl.UserServiceImpl;
import com.framework.common.ResultData;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;


/**
 * UserController
 *
 * @author ： Jarvis
 * @date :
 */

@RestController
@RequestMapping(value = "/user")
@Api(value = "UserController", description = "用户信息Api")
public class UserController {
    private Logger logger = LoggerFactory.getLogger(this.getClass());
    @Autowired
    private UserServiceImpl userService;

    /**
     * 查询所有用户信息
     *
     * @param pageNum  pageNum
     * @param pageSize pageSize
     * @return
     */
    @ApiOperation(value = "查询所有用户信息")
    @RequestMapping(value = "/getUsers", method = RequestMethod.GET)
    public AjaxResult<?> getUsers(int pageNum, int pageSize) {
        logger.info("查询所有用户");
        ResultData<?> users = userService.getUsers(pageNum, pageSize);
        AjaxResult<ResultData<?>> result = new AjaxResult<>(users);
        return result;
    }

    /**
     * add
     *
     * @param entity user
     */
    @RequestMapping(value = "/addUser", method = RequestMethod.POST)
    public void addUser(@RequestBody UserInfo entity) {
        userService.addUser(entity);
    }

    /**
     * update
     *
     * @param entity user
     */
    @RequestMapping(value = "/updateUser", method = RequestMethod.PUT)
    public void updateUser(@RequestBody UserInfo entity) {
        userService.updateUser(entity);
    }

}

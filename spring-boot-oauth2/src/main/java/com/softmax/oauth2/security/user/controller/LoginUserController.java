package com.softmax.oauth2.security.user.controller;

import com.softmax.oauth2.security.user.entity.LoginUser;
import com.softmax.oauth2.security.user.entity.RegisterUserDTO;
import com.softmax.oauth2.security.user.entity.RegisterUserReq;
import com.softmax.oauth2.security.user.service.LoginUserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @author Jarvis
 */
@Api(tags = "用户管理相关接口")
@RestController
@RequestMapping("v1/loginUser")
public class LoginUserController {

    @Autowired
    private LoginUserService userService;

    @ApiOperation("用户注册接口")
    @RequestMapping(value = "/register", method = RequestMethod.POST)
    public void register(RegisterUserReq registerUserReq) {
        RegisterUserDTO userDTO = new RegisterUserDTO();
        BeanUtils.copyProperties(registerUserReq, userDTO);

        userService.userRegister(userDTO);
    }

    @ApiOperation("用户查询接口")
    @RequestMapping(value = "/getLoginUser", method = RequestMethod.GET)
    public LoginUser getLoginUser(@RequestParam long sysno) {
        return userService.userRegister(sysno);
    }
}

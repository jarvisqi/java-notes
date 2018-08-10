package com.marvel.controller;

import com.marvel.entity.AuthUser;
import com.marvel.security.AuthUserServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.annotations.ApiIgnore;

/**
 * auth
 *
 * @author : Jarvis
 * @date :  2018/5/13
 */
@RestController
@RequestMapping(value = "/auth")
@ApiIgnore
public class AuthController {
    @Autowired
    private AuthUserServiceImpl authUserService;

    /**
     * 用户登录获取token
     *
     * @return 操作结果
     * @throws AuthenticationException 错误信息
     */
    @RequestMapping(value = "/login", method = RequestMethod.POST)
    public String getToken(@RequestBody AuthUser user) throws AuthenticationException {
        return authUserService.login(user.getUsername(), user.getPassword());
    }

    /**
     * 注册 AuthUser 信息
     *
     * @param user 用户信息
     * @return 操作结果
     * @throws AuthenticationException 错误信息
     */
    @RequestMapping(value = "/register", method = RequestMethod.POST)
    public String register(@RequestBody AuthUser user) throws AuthenticationException {
        return authUserService.register(user);
    }

    /**
     * 刷新token
     *
     * @param authorization 原密钥
     * @return 新密钥
     * @throws AuthenticationException 错误信息
     */
    @RequestMapping(value = "/refreshToken", method = RequestMethod.GET)
    public String refreshToken(@RequestHeader String authorization) throws AuthenticationException {
        return authUserService.refreshToken(authorization);
    }

}

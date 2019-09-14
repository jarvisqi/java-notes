package com.softmax.marvel.security;

import com.framework.common.BizException;
import com.softmax.marvel.entity.AuthUser;
import com.softmax.marvel.mapper.AuthMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;


import java.util.ArrayList;
import java.util.List;

import static java.util.Collections.emptyList;


/**
 * @author : Jarvis
 * @date :  2018/5/12
 */
@Service
public class AuthUserService implements UserDetailsService {
    @Autowired
    private AuthMapper authMapper;
    @Autowired
    private AuthenticationManager authenticationManager;

    private JwtTokenUtil jwtTokenUtil;

    public AuthUserService() {
        this.jwtTokenUtil = new JwtTokenUtil();
    }


    /**
     * 加载 security user
     *
     * @param username name
     * @return org.springframework.security.core.userdetails.UserDetails
     * @throws UsernameNotFoundException NotFoundException
     */
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        UserDetails user = findByUserName(username);
        if (user == null) {
            throw new BizException("用户名不存在");
        }
        return new org.springframework.security.core.userdetails.User(user.getUsername(), user.getPassword(),
                emptyList());
    }

    /**
     * 用户名查找用户信息
     *
     * @param username name
     * @return AuthUser
     */
    public AuthUser findByUserName(String username) {
        return authMapper.findByUserName(username);
    }

    /**
     * 注册auth User
     *
     * @param user AuthUser
     * @return string
     */
    public String register(AuthUser user) {
        String username = user.getUsername();
        if (this.findByUserName(username) != null) {
            return "用户已存在";
        }
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        String rawPassword = user.getPassword();
        user.setPassword(encoder.encode(rawPassword));
        List<String> roles = new ArrayList<>();
        roles.add("ROLE_USER");
        authMapper.register(user);
        return "注册成功";
    }

    /**
     * 登录获取token
     *
     * @param username
     * @param password
     * @return
     */
    public String login(String username, String password) {
        UsernamePasswordAuthenticationToken upToken = new UsernamePasswordAuthenticationToken(username, password);
        Authentication authentication = authenticationManager.authenticate(upToken);
        SecurityContextHolder.getContext().setAuthentication(authentication);
        UserDetails userDetails = loadUserByUsername(username);
        return jwtTokenUtil.generateToken(userDetails);
    }

    /**
     * 刷新token
     *
     * @param oldToken
     * @return
     */
    public String refreshToken(String oldToken) {
        String token = oldToken.substring("Bearer ".length());
        if (!jwtTokenUtil.isTokenExpired(token)) {
            return jwtTokenUtil.refreshToken(token);
        }
        return "error";
    }

}

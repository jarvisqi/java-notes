package com.softmax.oauth2server.service.impl;

import com.softmax.oauth2server.entity.AuthUser;
import com.softmax.oauth2server.entity.AuthUserDetails;
import com.softmax.oauth2server.service.AuthUserService;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

/**
 * @author Jarvis
 * @date 2018/9/7
 */
@Service
public class AuthUserServiceImpl implements AuthUserService {
    @Override
    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {
        AuthUser user = new AuthUser();
        user.setUsername("admin");
        user.setPassword("123456");
        return new AuthUserDetails(user);
    }
}

package com.softmax.ms.oauth2.service;

import com.alibaba.cloud.commons.lang.StringUtils;
import com.softmax.ms.oauth2.models.LoginUser;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

public class UserService implements UserDetailsService {
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        var user = new LoginUser();
        String name = "maxwell";
        if (!StringUtils.isBlank(username)) {
            if (!username.equals(name)) {
                throw new UsernameNotFoundException("用户名或密码错误，请重新输入");
            }
            user.setUid(100001L);
            user.setUserName(name);
            user.setEmail("max@mac.com");
        }

        return (UserDetails) user;
    }
}

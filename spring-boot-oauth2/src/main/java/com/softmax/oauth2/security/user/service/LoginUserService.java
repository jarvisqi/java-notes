package com.softmax.oauth2.security.user.service;

import com.softmax.oauth2.security.user.entity.LoginUser;
import com.softmax.oauth2.security.user.dao.LoginUserDAO;
import com.softmax.oauth2.security.user.entity.RegisterUserDTO;
import com.softmax.oauth2.security.user.entity.RegisterUserReq;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

/**
 * 用户服务
 *
 * @author Jarvis
 */
@Service
@Slf4j
public class LoginUserService implements UserDetailsService {

    @Autowired
    private LoginUserDAO userDAO;

    private BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();

    @Override
    public UserDetails loadUserByUsername(String name) throws UsernameNotFoundException {
        LoginUser loginUser = null;
        if (!StringUtils.isEmpty(name)) {
            loginUser = userDAO.getLoginUserByName(name);
        }
        return loginUser;
    }


    public void userRegister(RegisterUserDTO registerUserDTO) {
        //加密
        String encodePassword = bCryptPasswordEncoder.encode(registerUserDTO.getPassword());
        registerUserDTO.setPassword(encodePassword);
        registerUserDTO.setUserId("2020040815441633");

        userDAO.register(registerUserDTO);
    }

    public LoginUser userRegister(long sysno) {
        return userDAO.getLoginUser(sysno);
    }

}

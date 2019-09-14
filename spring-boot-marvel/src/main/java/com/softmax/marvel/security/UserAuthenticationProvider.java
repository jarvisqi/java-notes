package com.softmax.marvel.security;


import com.framework.common.BizException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import static java.util.Collections.emptyList;

/**
 * 自定义AuthenticationProvider 实现认证逻辑
 *
 * @author : Jarvis
 * @date : 2018/5/14
 */
public class UserAuthenticationProvider implements AuthenticationProvider {

    private UserDetailsService authUserService;
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    public UserAuthenticationProvider(UserDetailsService authUserService) {
        this.authUserService = authUserService;
        this.bCryptPasswordEncoder = new BCryptPasswordEncoder();
    }

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        // 获取认证的用户名 & 密码
        String name = authentication.getName();
        String password = authentication.getCredentials().toString();
        UserDetails userDetails = authUserService.loadUserByUsername(name);
        //认证逻辑
        if (null != userDetails) {
            //检查密码是否匹配
            if (bCryptPasswordEncoder.matches(password, userDetails.getPassword())) {
                // 这里设置权限和角色

                // 生成令牌 这里令牌里面存入了:name,password,authorities, 当然你也可以放其他内容
                Authentication auth = new UsernamePasswordAuthenticationToken(name, password, emptyList());
                return auth;
            } else {
                throw new BizException("密码错误");
            }
        } else {
            throw new BizException("用户不存在");
        }
    }

    /**
     * 是否可以提供输入类型的认证服务
     *
     * @param authentication
     * @return
     */
    @Override
    public boolean supports(Class<?> authentication) {
        return authentication.equals(UsernamePasswordAuthenticationToken.class);
    }
}

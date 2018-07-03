package com.marvel.mapper;

import com.marvel.entity.AuthUser;
import org.springframework.stereotype.Component;

/**
 * @author : Jarvis
 * @date :  2018/5/12
 */
public interface AuthMapper extends BaseMapper<AuthUser> {

    /**
     * 使用name查找User
     *
     * @param username
     * @return
     */
    AuthUser findByUserName(String username);

    /**
     * 注册用户
     *
     * @param user
     * @return
     */
    String register(AuthUser user);


}

package com.softmax.oauth2.security.user.dao;

import com.softmax.oauth2.security.user.entity.LoginUser;
import com.softmax.oauth2.security.user.entity.RegisterUserDTO;
import org.apache.ibatis.annotations.Mapper;

/**
 * @author Jarvis
 */
@Mapper
public interface LoginUserDAO {

    /**
     * 查询用户信息
     *
     * @param sysno
     * @return
     */
    LoginUser getLoginUser(long sysno);

    /**
     * 根据用户名查询用户信息
     *
     * @param name
     * @return
     */
    LoginUser getLoginUserByName(String name);


    /**
     * 注册
     *
     * @param userDTO
     * @return
     */
    void register(RegisterUserDTO userDTO);
}

package com.marvel.mapper.master;

import com.marvel.entity.UserInfo;
import com.marvel.mapper.BaseMapper;

import java.util.List;

/**
 * @author : Jarvis
 * @date : 2018/5/13
 */
public interface UserMapper extends BaseMapper<UserInfo> {

    /***
     * 获取所有数据
     * @return List
     */
    List<UserInfo> getAll();

    /***
     * 添加User
     * @param entity user
     */
    void addUser(UserInfo entity);

    /***
     * 更新User
     * @param entity user
     */
    void updateUser(UserInfo entity);
}

package com.marvel.service.impl;

import com.marvel.entity.UserInfo;
import com.marvel.mapper.UserMapper;
import com.framework.common.ResultData;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.marvel.service.BaseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author : Jarvis
 * @date : 2018/6/1
 */
@Service
public class UserServiceImpl extends BaseService<UserInfo> {

    @Autowired
    private UserMapper userMapper;

    /**
     * 查询所有用户信息
     *
     * @param pageNum  页码
     * @param pageSize 每页显示的条数
     * @return List<users>
     */
    public ResultData<?> getUsers(int pageNum, int pageSize) {
        //设置分页
        PageHelper.startPage(pageNum, pageSize);
        List<UserInfo> list = userMapper.getAll();
        PageInfo<UserInfo> pageInfo = new PageInfo<>(list);
        ResultData<?> result = new ResultData<>(pageInfo);

        return result;
    }

    /**
     * 添加用户信息
     *
     * @param entity UserInfo
     */
    public void addUser(UserInfo entity) {
        userMapper.addUser(entity);
    }

    /**
     * 更新用户信息
     *
     * @param entity UserInfo
     */
    public void updateUser(UserInfo entity) {
        userMapper.updateUser(entity);
    }

}

package com.softmax.marvel.mapper;

import com.softmax.marvel.entity.TUser;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface TUserMapper {
    int deleteByPrimaryKey(Integer sysno);

    int insert(TUser record);

    int insertSelective(TUser record);

    TUser selectByPrimaryKey(Integer sysno);

    int updateByPrimaryKeySelective(TUser record);

    int updateByPrimaryKey(TUser record);
}
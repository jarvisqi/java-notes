package com.softmax.marvel.mapper;

import com.softmax.marvel.entity.TOrder;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface TOrderMapper {
    int deleteByPrimaryKey(Integer sysno);

    int insert(TOrder record);

    int insertSelective(TOrder record);

    TOrder selectByPrimaryKey(Integer sysno);

    int updateByPrimaryKeySelective(TOrder record);

    int updateByPrimaryKey(TOrder record);
}
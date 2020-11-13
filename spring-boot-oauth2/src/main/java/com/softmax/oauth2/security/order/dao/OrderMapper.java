package com.softmax.oauth2.security.order.dao;

import com.softmax.oauth2.security.order.entity.Order;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface OrderMapper {

    int insert(Order record);

    int insertSelective(Order record);

    Order selectByPrimaryKey(Long sysno);

    List<Order> queryOrder();

    int updateByPrimaryKeySelective(Order record);

}
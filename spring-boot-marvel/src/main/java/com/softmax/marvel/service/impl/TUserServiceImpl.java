package com.softmax.marvel.service.impl;

import com.softmax.marvel.entity.TOrder;
import com.softmax.marvel.entity.TUser;
import com.softmax.marvel.mapper.TOrderMapper;
import com.softmax.marvel.mapper.TUserMapper;
import com.softmax.marvel.service.TUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class TUserServiceImpl implements TUserService {

    @Autowired
    private TUserMapper userMapper;

    @Autowired
    private TOrderMapper orderMapper;

    @Override
    public void saveUser(TUser tUser) {
        userMapper.insert(tUser);
    }

    @Override
    @Transactional(rollbackFor = RuntimeException.class)
    public void saveUser() {
        TUser user = new TUser();
        user.setName("test");
        user.setSysno(10005);

        saveUser(user);
        if (user.getSysno() > 100) {
            throw new RuntimeException("失败了");
        }
        TOrder tOrder = new TOrder();
        tOrder.setSysno(15000);
        tOrder.setOrderId(9001111L);
        orderMapper.insert(tOrder);
    }
}

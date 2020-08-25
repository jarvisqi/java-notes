package com.softmax.marvel.service.impl;

import com.softmax.marvel.entity.TOrder;
import com.softmax.marvel.entity.TUser;
import com.softmax.marvel.mapper.TOrderMapper;
import com.softmax.marvel.service.TOrderService;
import com.softmax.marvel.service.TUserService;
import org.apache.poi.ss.formula.functions.T;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;

@Service
public class TOrderServiceImpl implements TOrderService {

    @Autowired
    private TOrderMapper orderMapper;
    @Autowired
    private TUserService userService;

    @Autowired
    private TOrderServiceImpl tOrderService;


    /**
     * SUPPORTS :  如果当前存在事务，则加入该事务；如果当前不存在事务，则以非事务的方式继续运行
     * <p>
     * A方法（无事务）调B方法（有事务）,B方法的事务不生效,可以通过注入自身，再来用注入的bean来调用解决
     * A方法（有事务）调B方法（无事务）,A和B方法的事务生效
     * A方法（有事务）调B方法（无事务）,A和B方法的事务都生效
     * A类中的a方法（有事务）调用外部B类中的b方法（无事务），事务生效(对象注入的方式)
     * A类中的a方法（无事务）调用外部B类中的b方法（有事务），外部B类通过@Autowired注入到A类的bean里面，这样即使方法A没用事务，B方法事务也生效。
     *
     * @param tOrder
     */
    @Override
//    @Transactional(rollbackFor = RuntimeException.class)
    public void saveOrder(TOrder tOrder) {
//        orderMapper.insert(tOrder);
//        userService.saveUser();
        tOrderService.saveUser();
    }

    @Transactional(rollbackFor = RuntimeException.class)
    public void saveUser() {
        TUser user = new TUser();
        user.setName("test");
        user.setSysno(10007);
        userService.saveUser(user);
        if (user.getSysno() > 100) {
            throw new RuntimeException("失败了");
        }
        TOrder tOrder = new TOrder();
        tOrder.setSysno(15000);
        tOrder.setOrderId(9001111L);
        orderMapper.insert(tOrder);
    }
}

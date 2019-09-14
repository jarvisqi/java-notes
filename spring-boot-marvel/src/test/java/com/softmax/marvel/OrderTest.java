package com.softmax.marvel;

import cn.hutool.core.date.DateUnit;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.NumberUtil;
import cn.hutool.core.util.RandomUtil;
import com.softmax.marvel.entity.dto.OrderDTO;
import com.softmax.marvel.service.OrderService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.util.StringUtils;

import java.math.BigDecimal;
import java.util.Date;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = MarvelApplication.class)
public class OrderTest {

    @Autowired
    private OrderService orderService;

    @Test
    public void TestOrderType() throws IllegalAccessException {
        OrderDTO order = new OrderDTO();
        order.setOrderId(RandomUtil.randomNumbers(8));
        order.setOrderType(2);
        order.setPrice(127300.46f);
        order.setOrderDate(DateUtil.parseDate("2019-08-08 12:12"));

        String result = orderService.handleOrder(order);
        System.out.println(order.toString());
        System.out.println(result);

        System.out.println("\n===========================================================================\n");

        order.setOrderId(RandomUtil.randomNumbers(8));
        order.setOrderType(1);
        order.setPrice(145500.46f);
        order.setOrderDate(DateUtil.parseDate("2019-09-09 12:12"));
        
        result = orderService.handleOrder(order);
        System.out.println(order.toString());
        System.out.println(result);

    }
}

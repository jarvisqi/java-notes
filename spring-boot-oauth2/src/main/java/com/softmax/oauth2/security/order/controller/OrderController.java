package com.softmax.oauth2.security.order.controller;

import com.softmax.oauth2.security.order.entity.Order;
import com.softmax.oauth2.security.order.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/order")
public class OrderController {

    @Autowired
    private OrderService orderService;

    /**
     * 查询所有数据
     *
     * @return
     */
    @RequestMapping(value = "/query", method = RequestMethod.GET)
    @ResponseBody
    public List<Order> queryList() {
        return orderService.queryOrder();
    }

    /**
     * 查询订单数据
     *
     * @return
     */
    @RequestMapping(value = "/getOrder", method = RequestMethod.GET)
    @ResponseBody
    public Order getOrder(@RequestParam Long sysno) {
        return orderService.getOrder(sysno);
    }

    /**
     * 新增数据
     *
     * @param order
     */
    @RequestMapping(value = "/insert", method = RequestMethod.POST)
    public void insertRecord(@RequestBody Order order) {
        orderService.insert(order);
    }

}

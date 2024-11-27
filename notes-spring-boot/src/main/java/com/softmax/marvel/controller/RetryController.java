package com.softmax.marvel.controller;

import com.softmax.marvel.service.impl.GoodsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/retry")
public class RetryController {

    @Autowired
    private GoodsService goodsService;

    /**
     * 扣减库存
     */
    @RequestMapping("/deduction")
    @ResponseBody
    public int minGoodsnum() {
        return goodsService.minGoodsnum(-1);
    }

}

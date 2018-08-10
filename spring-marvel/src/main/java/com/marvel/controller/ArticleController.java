package com.marvel.controller;

import cn.hutool.http.HttpStatus;
import com.marvel.service.impl.ArticleServiceImpl;
import com.framework.common.BusinessException;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

/**
 * @author : Jarvis
 * @date : 2018/6/2
 */
@Controller
@RequestMapping(value = "/article")
@Api(value = "ArticleController", description = "文章信息Api")
public class ArticleController {
    @Autowired
    public ArticleServiceImpl articleService;

    /**
     * 查询所有的信息
     *
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/articleList")
    public List<?> getAll() {
        return articleService.getMapper().selectAll();
    }

    /**
     * 异常处理
     */
    @RequestMapping("/test")
    @ResponseBody
    public int testError() {
        // 除 0异常
        return 9 / 0;
    }

    /**
     * 自定义异常处理
     */
    @RequestMapping("/exTest")
    @ResponseBody
    public int exTest(int param) {
        if (param == 0) {
            throw new BusinessException(HttpStatus.HTTP_OK, "参数不正确");
        }
        // 除 0异常
        return 0;
    }
}

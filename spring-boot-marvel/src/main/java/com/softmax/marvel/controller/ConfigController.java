package com.softmax.marvel.controller;

import com.softmax.marvel.entity.Article;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;


/**
 * 配置config
 */
@RestController
@RequestMapping(value = "/config")
public class ConfigController {

    @Value("${jwell.oauth2.resource.oauthServer}")
    private String config;

    @RequestMapping("/list")
    public String configList() {
        String result = "error";
        if (!StringUtils.isEmpty(config)) {
            result = config;
        }
        return result;
    }

    @RequestMapping(value = "/save", method = RequestMethod.POST)
    public Article saveConfig(@RequestBody Article article) {
        return article;
    }
}

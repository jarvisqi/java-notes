package com.marvel.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


/**
 * 配置config
 */
@RestController
@RequestMapping(value = "/config")
public class ConfigController {

    @Value("${jwell.cloud.config}")
    private String config;

    @RequestMapping("/list")
    public String configList() {
        String result = "error";
        if (!StringUtils.isEmpty(config)) {
            result = config;
        }
        return result;
    }
}

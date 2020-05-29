package com.softmax.marvel.controller;

import com.framework.utils.WordUtils;
import com.softmax.marvel.entity.Article;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;
import java.net.URL;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;


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

    @RequestMapping(value = "/gen", method = RequestMethod.GET)
    public void wordGen(HttpServletResponse response) throws Exception {

        HashMap map = new HashMap(16);
        map.put("bankName", "天朝皇家金融银行");
        map.put("proxyCompanyName", "成都积微物联皇家管理公司");
        map.put("businessName", "银行开户存储充值业务");
        map.put("activityName", "氪金");
        map.put("courseName", "充值提现氪金交易");
        map.put("proxyName", "成都皇家螺纹管公司");
        map.put("identity", "20002002020202020");
        map.put("signDate", LocalDate.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd")));

        //注意打成jar ddocker以后无法这样获取路径，路径可以从阿里云获取，也可以本地
        URL resource = this.getClass().getResource("/docs/AuthorizationTemplate.docx");
        WordUtils.generateWord(map, resource.getPath(), "D:\\10001.docx");
        //TODO 下载文件


    }
}

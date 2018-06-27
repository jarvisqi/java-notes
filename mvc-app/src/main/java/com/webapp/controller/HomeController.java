package com.webapp.controller;

import com.webapp.model.UserInfo;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.*;

/**
 * @author : Jarvis
 * @date : 2018/6/1
 */
@Controller
public class HomeController {

    @RequestMapping(value = "/", method = RequestMethod.GET)
    public String index() {
        return "index";
    }

    /**
     * @return
     */
    @RequestMapping(value = "/home/user", method = RequestMethod.GET)
    public String index(ModelMap modelMap) {
        List<UserInfo> userList = new ArrayList<>();
        userList.add(new UserInfo(1001, "龙傲天", 23, "成都省"));
        userList.add(new UserInfo(1002, "曹孟德", 33, "河北市"));
        userList.add(new UserInfo(1003, "Chris", 32, "高新市"));
        userList.add(new UserInfo(1003, "Evans", 17, "绵阳市"));

        // 将所有记录传递给要返回的jsp页面，放在userList当中
        modelMap.addAttribute("userList", userList);

        // 返回views目录下的hello.jsp页面
        return "home/user";
    }

    @ResponseBody
    @RequestMapping(value = "/home/userList", method = RequestMethod.GET)
    public List<UserInfo> userList() {
        List<UserInfo> userList = new ArrayList<>();
        userList.add(new UserInfo(1001, "龙傲天", 23, "成都省"));
        userList.add(new UserInfo(1002, "曹孟德", 33, "河北市"));
        userList.add(new UserInfo(1003, "Chris", 32, "高新市"));
        userList.add(new UserInfo(1003, "Evans", 17, "绵阳市"));

        return userList;
    }
}

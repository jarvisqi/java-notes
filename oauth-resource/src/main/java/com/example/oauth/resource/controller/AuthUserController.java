package com.example.oauth.resource.controller;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author Jarvis
 * @date 2018/8/1
 */
@RestController
public class AuthUserController {

    @RequestMapping("/user")
    public String getUser() {
        return SecurityContextHolder.getContext().getAuthentication().getName();
    }
}

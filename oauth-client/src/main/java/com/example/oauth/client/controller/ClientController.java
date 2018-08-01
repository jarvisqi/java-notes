package com.example.oauth.client.controller;

import org.springframework.security.access.annotation.Secured;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author Jarvis
 * @date 2018/8/1
 */
@RequestMapping("/client")
@Secured("ROLE_ADMIN")
@RestController
public class ClientController {

    @RequestMapping("/getUser")
    public Authentication getUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return authentication;
    }

    @RequestMapping("/index")
    @Secured("ROLE_USER")
    public String index() {
        return "index";
    }
}

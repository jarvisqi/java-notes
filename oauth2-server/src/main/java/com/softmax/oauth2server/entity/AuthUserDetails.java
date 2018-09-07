package com.softmax.oauth2server.entity;

import org.springframework.security.core.userdetails.User;

import java.util.Collections;

/**
 * @author Jarvis
 * @date 2018/9/7
 */
public class AuthUserDetails extends User {

    private AuthUser user;

    public AuthUserDetails(AuthUser user) {
        super(user.getUsername(), user.getPassword(), true, true, true, true, Collections.EMPTY_LIST);
        this.user = user;
    }

    public AuthUser getUser() {
        return user;
    }

    public void setUser(AuthUser user) {
        this.user = user;
    }
}

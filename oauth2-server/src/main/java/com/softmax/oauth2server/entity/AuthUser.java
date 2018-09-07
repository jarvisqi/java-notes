package com.softmax.oauth2server.entity;

import java.io.Serializable;

/**
 * @author Jarvis
 * @date 2018/9/7
 */
public class AuthUser implements Serializable {

    private static final long serialVersionUID = 5000853558893922910L;
    
    private String username;
    private String password;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}

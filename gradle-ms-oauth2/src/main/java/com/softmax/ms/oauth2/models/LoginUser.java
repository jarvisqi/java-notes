package com.softmax.ms.oauth2.models;

import lombok.Data;

@Data
public class LoginUser {

    private Long uid;
    private String userName;

    private String email;
}

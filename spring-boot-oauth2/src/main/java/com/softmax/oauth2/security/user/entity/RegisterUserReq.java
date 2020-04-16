package com.softmax.oauth2.security.user.entity;

import io.swagger.annotations.ApiModel;
import lombok.Data;

import java.io.Serializable;

@Data
@ApiModel
public class RegisterUserReq implements Serializable {

    private static final long serialVersionUID = -213574977940773819L;

    private String username;

    private String password;

}

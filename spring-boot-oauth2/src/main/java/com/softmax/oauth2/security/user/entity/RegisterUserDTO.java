package com.softmax.oauth2.security.user.entity;

import lombok.Data;

import java.io.Serializable;

/**
 * @author Jarvis
 */
@Data
public class RegisterUserDTO implements Serializable {

    private static final long serialVersionUID = 5722950267085991254L;

    private String userId;

    private String username;

    private String password;
}

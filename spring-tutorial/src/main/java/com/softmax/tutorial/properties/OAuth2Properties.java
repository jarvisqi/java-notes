package com.softmax.tutorial.properties;

import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * @author Jarvis
 * @date 2018/8/30
 */
@ConfigurationProperties(prefix = "softmax.security.oauth2")
public class OAuth2Properties {
    private String jwtSigningKey = "softmax";

    private OAuth2ClientProperties[] clients = {};

    public String getJwtSigningKey() {
        return jwtSigningKey;
    }

    public void setJwtSigningKey(String jwtSigningKey) {
        this.jwtSigningKey = jwtSigningKey;
    }

    public OAuth2ClientProperties[] getClients() {
        return clients;
    }

    public void setClients(OAuth2ClientProperties[] clients) {
        this.clients = clients;
    }
}

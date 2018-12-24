package com.elliot.security.core.config.bean;

public class OAuth2 {

    private String tokenStore = "jwt";

    public String getTokenStore() {
        return tokenStore;
    }

    public void setTokenStore(String tokenStore) {
        this.tokenStore = tokenStore;
    }
}

package com.elliot.security.core.config.bean;

public class AppSecurity {

    private OAuth2 oauth2 = new OAuth2();

    public OAuth2 getOauth2() {
        return oauth2;
    }

    public void setOauth2(OAuth2 oauth2) {
        this.oauth2 = oauth2;
    }
}

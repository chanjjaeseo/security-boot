package com.elliot.security.core.config.bean;

public class BrowserSecurity {

    private int tokenValiditySeconds = 0;

    public int getTokenValiditySeconds() {
        return tokenValiditySeconds;
    }

    public void setTokenValiditySeconds(int tokenValiditySeconds) {
        this.tokenValiditySeconds = tokenValiditySeconds;
    }
}

package com.elliot.security.core.config.bean;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties(prefix = "boot")
public class SecurityBootBean {

    private BrowserSecurity browser = new BrowserSecurity();

    private AppSecurity app = new AppSecurity();

    private ValidateCodeSecurity code = new ValidateCodeSecurity();

    public BrowserSecurity getBrowser() {
        return browser;
    }

    public void setBrowser(BrowserSecurity browser) {
        this.browser = browser;
    }

    public AppSecurity getApp() {
        return app;
    }

    public void setApp(AppSecurity app) {
        this.app = app;
    }

    public ValidateCodeSecurity getCode() {
        return code;
    }

    public void setCode(ValidateCodeSecurity code) {
        this.code = code;
    }
}

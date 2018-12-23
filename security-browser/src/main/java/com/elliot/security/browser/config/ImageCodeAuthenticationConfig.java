package com.elliot.security.browser.config;

import com.elliot.security.core.constant.SecurityConstant;
import com.elliot.security.core.validate.checker.DefaultValidateCodeChecker;
import com.elliot.security.core.validate.checker.ValidateCodeChecker;
import com.elliot.security.core.validate.filter.ImageValidateCodeFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.config.annotation.SecurityConfigurerAdapter;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.DefaultSecurityFilterChain;
import org.springframework.security.web.authentication.preauth.AbstractPreAuthenticatedProcessingFilter;
import org.springframework.stereotype.Component;

@Component
public class ImageCodeAuthenticationConfig extends SecurityConfigurerAdapter<DefaultSecurityFilterChain, HttpSecurity> {

    @Autowired
    private ImageValidateCodeFilter imageValidateCodeFilter;

    @Override
    public void configure(HttpSecurity http) throws Exception {
        ValidateCodeChecker imageValidateCodeChecker = new DefaultValidateCodeChecker(SecurityConstant.ValidateCode.IMAGE_VALIDATE_CODE_REQUEST_NAME, SecurityConstant.ValidateCode.IMAGE_VALIDATE_CODE_SESSION_NAME);
        imageValidateCodeFilter.setImageValidateCodeChecker(imageValidateCodeChecker);
        http.addFilterBefore(imageValidateCodeFilter, AbstractPreAuthenticatedProcessingFilter.class);
    }

}

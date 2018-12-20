package com.elliot.security.core.config;

import com.elliot.security.core.validate.filter.ImageValidateCodeFilter;
import com.elliot.security.core.validate.image.ImageValidateCode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.config.annotation.SecurityConfigurerAdapter;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.DefaultSecurityFilterChain;
import org.springframework.security.web.authentication.preauth.AbstractPreAuthenticatedProcessingFilter;
import org.springframework.stereotype.Component;

import javax.servlet.Filter;

@Component
public class ValidateCodeAuthenticationConfig extends SecurityConfigurerAdapter<DefaultSecurityFilterChain, HttpSecurity> {

    @Autowired
    private ImageValidateCodeFilter imageValidateCodeFilter;

    @Override
    public void configure(HttpSecurity http) throws Exception {
        http.addFilterBefore(imageValidateCodeFilter, AbstractPreAuthenticatedProcessingFilter.class);
    }

}

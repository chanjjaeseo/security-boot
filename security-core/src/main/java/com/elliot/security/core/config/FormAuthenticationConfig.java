package com.elliot.security.core.config;

import com.elliot.security.core.constant.SecurityConstant;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

@Component
public class FormAuthenticationConfig {

    @Autowired
    private AuthenticationSuccessHandler defaultAuthenticationSuccessHandler;

    @Autowired
    private AuthenticationFailureHandler defaultAuthenticationFailureHandler;

    public void configure(HttpSecurity httpSecurity) throws Exception {
        httpSecurity
                .formLogin()
                .loginPage(SecurityConstant.FormLogin.LOGIN_PAGE_URL)
                .loginProcessingUrl(SecurityConstant.FormLogin.LOGIN_PROCESS_URL)
                .successHandler(defaultAuthenticationSuccessHandler)
                .failureHandler(defaultAuthenticationFailureHandler);

    }

}

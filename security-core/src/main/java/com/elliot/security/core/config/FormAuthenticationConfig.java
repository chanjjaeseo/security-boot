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
                    .loginPage("/signIn.html")
                    .loginProcessingUrl("/login/process")
                    .successHandler(defaultAuthenticationSuccessHandler)
                    .failureHandler(defaultAuthenticationFailureHandler)
                .and()
                .authorizeRequests()
                    .antMatchers(
                            SecurityConstant.FormLogin.LOGIN_PAGE,
                            SecurityConstant.FormLogin.LOGIN_PROCESS_URL)
                    .permitAll()
                    .anyRequest()
                    .authenticated()
                .and()
                    .csrf().disable();

    }

}

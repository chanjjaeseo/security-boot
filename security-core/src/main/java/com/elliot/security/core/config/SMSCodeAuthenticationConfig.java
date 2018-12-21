package com.elliot.security.core.config;

import com.elliot.security.core.validate.sms.MobileAuthenticationFilter;
import com.elliot.security.core.validate.sms.MobileAuthenticationProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.SecurityConfigurerAdapter;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.DefaultSecurityFilterChain;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.stereotype.Component;

@Component
public class SMSCodeAuthenticationConfig extends SecurityConfigurerAdapter<DefaultSecurityFilterChain, HttpSecurity> {

    @Autowired
    private AuthenticationSuccessHandler authenticationSuccessHandler;

    @Autowired
    private AuthenticationFailureHandler authenticationFailureHandler;

    @Autowired
    private UserDetailsService userDetailsService;

    @Override
    public void configure(HttpSecurity builder) throws Exception {
        MobileAuthenticationFilter mobileAuthenticationFilter = new MobileAuthenticationFilter();
        mobileAuthenticationFilter.setAuthenticationManager(builder.getSharedObject(AuthenticationManager.class));
        mobileAuthenticationFilter.setAuthenticationSuccessHandler(authenticationSuccessHandler);
        mobileAuthenticationFilter.setAuthenticationFailureHandler(authenticationFailureHandler);

        MobileAuthenticationProvider mobileAuthenticationProvider = new MobileAuthenticationProvider();
        mobileAuthenticationProvider.setUserDetailsService(userDetailsService);

        builder.authenticationProvider(mobileAuthenticationProvider)
               .addFilterAfter(mobileAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);
    }
}

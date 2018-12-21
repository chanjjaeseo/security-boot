package com.elliot.security.app.config;

import com.elliot.security.core.config.FormAuthenticationConfig;
import com.elliot.security.core.config.ImageCodeAuthenticationConfig;
import com.elliot.security.core.config.SMSCodeAuthenticationConfig;
import com.elliot.security.core.constant.SecurityConstant;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;

@Configuration
@EnableResourceServer
public class ResourceServerConfig extends ResourceServerConfigurerAdapter {


    @Autowired
    private FormAuthenticationConfig formAuthenticationConfig;

    @Autowired
    private ImageCodeAuthenticationConfig imageCodeAuthenticationConfig;

    @Autowired
    private SMSCodeAuthenticationConfig smsCodeAuthenticationConfig;

    @Override
    public void configure(HttpSecurity http) throws Exception {

        // csrf
        http.csrf().disable();

        // form login
        formAuthenticationConfig.configure(http);

        // sms code authentication
        http.apply(smsCodeAuthenticationConfig);

        // url resources protected
        http.authorizeRequests()
                .antMatchers(
                        SecurityConstant.FormLogin.LOGIN_PAGE_URL,
                        SecurityConstant.FormLogin.LOGIN_PROCESS_URL,
                        SecurityConstant.MobileLogin.LOGIN_PROCESS_URL,
                        SecurityConstant.OAuth.OAUTH_URL_PREFIX + "/*",
                        SecurityConstant.ValidateCode.VALIDATE_CODE_URL_PREFIX + "/*")
                .permitAll()
                .anyRequest()
                .authenticated();

    }
}

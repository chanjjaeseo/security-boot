package com.elliot.security.browser.config;

import com.elliot.security.core.config.bean.SecurityBootBean;
import com.elliot.security.core.config.FormAuthenticationConfig;
import com.elliot.security.core.constant.SecurityConstant;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.rememberme.PersistentTokenRepository;

@Configuration
public class BrowserSecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private FormAuthenticationConfig formAuthenticationConfig;

    @Autowired
    private PersistentTokenRepository jdbcTokenRepository;

    @Autowired
    private UserDetailsService userDetailsService;

    @Autowired
    private SecurityBootBean securityBootBean;

    @Autowired
    private ImageCodeAuthenticationConfig imageCodeAuthenticationConfig;

    @Autowired
    private SMSCodeAuthenticationConfig smsCodeAuthenticationConfig;

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        formAuthenticationConfig.configure(http);

        // validate code authentication (form login)
        http.apply(imageCodeAuthenticationConfig);

        // sms code authentication
        http.apply(smsCodeAuthenticationConfig);

        // remember me
        http.rememberMe()
                .tokenRepository(jdbcTokenRepository)
                .tokenValiditySeconds(securityBootBean.getBrowser().getTokenValiditySeconds())
                .userDetailsService(userDetailsService);

        // url resources protected
        http.authorizeRequests()
            .antMatchers(
                    SecurityConstant.FormLogin.LOGIN_PAGE_URL,
                    SecurityConstant.FormLogin.LOGIN_PROCESS_URL,
                    SecurityConstant.ValidateCode.VALIDATE_CODE_URL_PREFIX + "/*")
            .permitAll()
            .anyRequest()
            .authenticated();

        // csrf
        http.csrf().disable();
    }


}

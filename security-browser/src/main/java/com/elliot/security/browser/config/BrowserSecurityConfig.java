package com.elliot.security.browser.config;

import com.elliot.security.core.config.bean.SecurityBootBean;
import com.elliot.security.core.config.FormAuthenticationConfig;
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

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        formAuthenticationConfig.configure(http);

        // remember me
        http.rememberMe()
                .tokenRepository(jdbcTokenRepository)
                .tokenValiditySeconds(securityBootBean.getBrowser().getTokenValiditySeconds())
                .userDetailsService(userDetailsService);
    }


}

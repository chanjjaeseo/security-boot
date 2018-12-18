package com.elliot.security.app.oauth;

import com.elliot.security.core.config.FormAuthenticationConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

@Configuration
@EnableResourceServer
public class ResourceServerConfig {

    //extends ResourceServerConfigurerAdapter
//    @Autowired
//    private FormAuthenticationConfig formAuthenticationConfig;

//    @Autowired
//    private AuthenticationSuccessHandler defaultAuthenticationSuccessHandler;
//
//    @Autowired
//    private AuthenticationFailureHandler defaultAuthenticationFailureHandler;

//    @Override
//    public void configure(HttpSecurity http) throws Exception {

//        http
//                .formLogin()
//                .loginPage("/signIn.html")
//                .loginProcessingUrl("/login/process")
//                .successHandler(defaultAuthenticationSuccessHandler)
//                .failureHandler(defaultAuthenticationFailureHandler);
//
//        http
//                .authorizeRequests()
//                    .antMatchers("/signIn.html","/login/process")
//                    .permitAll()
//                    .anyRequest()
//                    .authenticated()
//                .and()
//                    .csrf().disable();
//        http
//                .authorizeRequests()
//                .anyRequest().authenticated()
//                .and()
//                .formLogin()
//                .loginPage("/signIn.html")
//                .loginProcessingUrl("/login/process");
//    }
}

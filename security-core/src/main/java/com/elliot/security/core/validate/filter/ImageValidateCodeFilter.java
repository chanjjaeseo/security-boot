package com.elliot.security.core.validate.filter;

import com.elliot.security.core.constant.SecurityConstant;
import com.elliot.security.core.exception.ValidateException;
import com.elliot.security.core.validate.checker.ValidateCodeChecker;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.stereotype.Component;
import org.springframework.util.AntPathMatcher;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component("imageValidateCodeFilter")
public class ImageValidateCodeFilter extends OncePerRequestFilter {

    private static AntPathMatcher antPathMatcher = new AntPathMatcher();

    @Autowired
    private AuthenticationFailureHandler authenticationFailureHandler;

    private ValidateCodeChecker imageValidateCodeChecker;

    private static final String FORM_LOGIN_URL = SecurityConstant.FormLogin.LOGIN_PROCESS_URL;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        boolean uriMatched = antPathMatcher.match(FORM_LOGIN_URL, request.getRequestURI());
        if (uriMatched) {
            try {
                imageValidateCodeChecker.validate(request, null);
            } catch (Exception e) {
                authenticationFailureHandler.onAuthenticationFailure(request, response,
                        new ValidateException(e.getMessage()));
                return;
            }
        }
        filterChain.doFilter(request, response);
    }

    public void setImageValidateCodeChecker(ValidateCodeChecker imageValidateCodeChecker) {
        this.imageValidateCodeChecker = imageValidateCodeChecker;
    }
}

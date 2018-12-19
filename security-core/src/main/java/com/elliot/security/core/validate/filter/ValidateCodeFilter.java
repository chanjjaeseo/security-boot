package com.elliot.security.core.validate.filter;

import com.elliot.security.core.constant.SecurityConstant;
import com.elliot.security.core.constant.ValidateCodeEnum;
import com.elliot.security.core.exception.ValidateException;
import com.elliot.security.core.validate.ValidateCodeProcessor;
import com.elliot.security.core.validate.ValidateCodeProcessorHolder;
import org.springframework.beans.factory.InitializingBean;
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
import java.util.HashMap;
import java.util.Map;

@Component("validateCodeFilter")
public class ValidateCodeFilter extends OncePerRequestFilter implements InitializingBean {

    private AntPathMatcher antPathMatcher = new AntPathMatcher();

    @Autowired
    private AuthenticationFailureHandler authenticationFailureHandler;

    @Autowired
    private ValidateCodeProcessorHolder validateCodeProcessorHolder;

    private Map<String, ValidateCodeEnum> urlMapping = new HashMap<>();

    @Override
    public void afterPropertiesSet() throws ServletException {
        urlMapping.put(SecurityConstant.FormLogin.LOGIN_PROCESS_URL, ValidateCodeEnum.IMAGE);
        urlMapping.put(SecurityConstant.MobileLogin.LOGIN_PROCESS_URL, ValidateCodeEnum.SMS);
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        ValidateCodeEnum validateCodeEnum = getValidateCode(request);
        if (validateCodeEnum != null) {
            try {
                ValidateCodeProcessor processor = validateCodeProcessorHolder.getProcessorByType(validateCodeEnum);
                processor.validate(request);
            } catch (Exception e) {
                authenticationFailureHandler.onAuthenticationFailure(request, response,
                        new ValidateException(e.getMessage()));
                return;
            }
        }
        filterChain.doFilter(request, response);
    }


    private ValidateCodeEnum getValidateCode(HttpServletRequest request) {
        String requestURI = request.getRequestURI();
        for (Map.Entry<String, ValidateCodeEnum> urlEntry: urlMapping.entrySet()) {
            String validateURL = urlEntry.getKey();
            boolean match = antPathMatcher.match(validateURL, requestURI);
            if (match)
                return urlEntry.getValue();
        }
        return null;
    }


}

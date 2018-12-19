package com.elliot.security.core.validate.filter;

import com.elliot.security.core.constant.SecurityConstant;
import com.elliot.security.core.constant.ValidateCode;
import com.elliot.security.core.exception.ValidateException;
import com.elliot.security.core.validate.ValidateCodeProcessor;
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

@Component("validateCodeFilter")
public class ValidateCodeFilter extends OncePerRequestFilter {

    private AntPathMatcher antPathMatcher = new AntPathMatcher();

    @Autowired
    private AuthenticationFailureHandler authenticationFailureHandler;

    @Autowired
    private ValidateCodeProcessor imageValidateCodeProcessor;

    @Autowired
    private ValidateCodeProcessor smsValidateCodeProcessor;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        ValidateCode validateCode = getValidateCode(request);
        if (validateCode != null) {
            try {
                if (validateCode == ValidateCode.IMAGE) {
                    imageValidateCodeProcessor.validate(request);
                } else if (validateCode == ValidateCode.SMS) {
                    smsValidateCodeProcessor.validate(request);
                } else {
                    throw new ValidateException("不支持的验证方式");
                }
            } catch (Exception e) {
                authenticationFailureHandler.onAuthenticationFailure(request, response,
                        new ValidateException(e.getMessage()));
                return;
            }
        }
        filterChain.doFilter(request, response);
    }


    private ValidateCode getValidateCode(HttpServletRequest request) {
        for (ValidateCode validateCode: ValidateCode.values()) {
            String validateCodeURL = SecurityConstant.FormLogin.VALIDATE_CODE_URL_PREFIX + validateCode.getUrlSuffix();
            boolean match = antPathMatcher.match(validateCodeURL, request.getRequestURI());
            if (match)
                return validateCode;
        }
        return null;
    }


}

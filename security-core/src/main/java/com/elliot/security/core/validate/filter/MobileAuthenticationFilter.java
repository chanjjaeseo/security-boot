package com.elliot.security.core.validate.filter;

import com.elliot.security.core.constant.SecurityConstant;
import com.elliot.security.core.exception.ValidateException;
import com.elliot.security.core.validate.checker.ValidateCodeChecker;
import com.elliot.security.core.validate.provider.MobileAuthenticationToken;
import org.apache.commons.lang.StringUtils;
import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class MobileAuthenticationFilter extends AbstractAuthenticationProcessingFilter {

    private String mobileParameter = SecurityConstant.MobileLogin.MOBILE_REQUEST_PARAMETER;
    private boolean postOnly = true;
    private ValidateCodeChecker smsValidateCodeChecker;

    public MobileAuthenticationFilter() {
        super(new AntPathRequestMatcher(SecurityConstant.MobileLogin.LOGIN_PROCESS_URL, "POST"));
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException, IOException, ServletException {
        if (this.postOnly && !request.getMethod().equals("POST")) {
            throw new AuthenticationServiceException("Authentication method not supported: " + request.getMethod());
        } else {

            String mobile = this.obtainMobile(request);
            if (mobile == null) {
                mobile = "";
            }
            mobile = mobile.trim();
            preCheck(request, mobile);
            MobileAuthenticationToken authRequest = new MobileAuthenticationToken(mobile);
            this.setDetails(request, authRequest);
            return this.getAuthenticationManager().authenticate(authRequest);
        }
    }

    protected void preCheck(HttpServletRequest request, String mobile) {
        if (StringUtils.isBlank(mobile)) {
            throw new ValidateException("手机号不能为空");
        }
        smsValidateCodeChecker.validate(request, "mobile-" + mobile);
    }

    protected void setDetails(HttpServletRequest request, MobileAuthenticationToken authRequest) {
        authRequest.setDetails(this.authenticationDetailsSource.buildDetails(request));
    }

    private String obtainMobile(HttpServletRequest request) {
        return request.getParameter(mobileParameter);
    }

    public void setSmsValidateCodeChecker(ValidateCodeChecker smsValidateCodeChecker) {
        this.smsValidateCodeChecker = smsValidateCodeChecker;
    }
}

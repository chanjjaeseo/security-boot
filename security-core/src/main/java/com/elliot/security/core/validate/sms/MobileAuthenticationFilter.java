package com.elliot.security.core.validate.sms;

import com.elliot.security.core.constant.SecurityConstant;
import com.elliot.security.core.validate.checker.SMSValidateCodeChecker;
import com.elliot.security.core.validate.checker.ValidateCodeChecker;
import com.elliot.security.core.validate.processor.ValidateCodeProcessor;
import com.elliot.security.core.validate.token.MobileAuthenticationToken;
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
    private ValidateCodeChecker smsValidateCodeChecker = new SMSValidateCodeChecker();

    public MobileAuthenticationFilter() {
        super(new AntPathRequestMatcher(SecurityConstant.MobileLogin.LOGIN_PROCESS_URL, "POST"));
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException, IOException, ServletException {
        if (this.postOnly && !request.getMethod().equals("POST")) {
            throw new AuthenticationServiceException("Authentication method not supported: " + request.getMethod());
        } else {
            preCheck(request);
            String mobile = this.obtainMobile(request);
            if (mobile == null) {
                mobile = "";
            }
            mobile = mobile.trim();
            MobileAuthenticationToken authRequest = new MobileAuthenticationToken(mobile);
            this.setDetails(request, authRequest);
            return this.getAuthenticationManager().authenticate(authRequest);
        }
    }

    private void preCheck(HttpServletRequest request) {
        smsValidateCodeChecker.validate(request);
    }

    protected void setDetails(HttpServletRequest request, MobileAuthenticationToken authRequest) {
        authRequest.setDetails(this.authenticationDetailsSource.buildDetails(request));
    }

    private String obtainMobile(HttpServletRequest request) {
        return request.getParameter(mobileParameter);
    }

}

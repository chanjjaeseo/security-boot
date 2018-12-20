package com.elliot.security.core.validate.sms;

import com.elliot.security.core.constant.SecurityConstant;
import com.elliot.security.core.validate.ValidateCodeProcessor;
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

    public static final String SECURITY_BOOT_MOBILE_KEY = "mobile";
    public static final String SECURITY_BOOT_SMS_KEY = "v-code-sms";
    private String mobileParameter = SECURITY_BOOT_MOBILE_KEY;
    private String smsCodeParameter = SECURITY_BOOT_SMS_KEY;
    private boolean postOnly = true;
    private ValidateCodeProcessor validateCodeProcessor;

    public MobileAuthenticationFilter() {
        super(new AntPathRequestMatcher(SecurityConstant.MobileLogin.LOGIN_PROCESS_URL, "POST"));
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException, IOException, ServletException {
        if (this.postOnly && !request.getMethod().equals("POST")) {
            throw new AuthenticationServiceException("Authentication method not supported: " + request.getMethod());
        } else {
            validateCodeProcessor.validate(request);
            String mobile = this.obtainMobile(request);
            String smsCode = this.obtainSMSCode(request);
            if (mobile == null) {
                mobile = "";
            }
            if (smsCode == null) {
                smsCode = "";
            }
            mobile = mobile.trim();
            smsCode = smsCode.trim();
            MobileAuthenticationToken authRequest = new MobileAuthenticationToken(mobile, smsCode);
            this.setDetails(request, authRequest);
            return this.getAuthenticationManager().authenticate(authRequest);
        }
    }

    public ValidateCodeProcessor getValidateCodeProcessor() {
        return validateCodeProcessor;
    }

    public void setValidateCodeProcessor(ValidateCodeProcessor validateCodeProcessor) {
        this.validateCodeProcessor = validateCodeProcessor;
    }

    protected void setDetails(HttpServletRequest request, MobileAuthenticationToken authRequest) {
        authRequest.setDetails(this.authenticationDetailsSource.buildDetails(request));
    }

    private String obtainMobile(HttpServletRequest request) {
        return request.getParameter(mobileParameter);
    }

    private String obtainSMSCode(HttpServletRequest request) {
        return request.getParameter(smsCodeParameter);
    }

}

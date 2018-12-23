package com.elliot.security.core.validate.processor;

import com.elliot.security.core.validate.code.ValidateCode;
import com.elliot.security.core.util.ValidateCodeUtil;
import com.elliot.security.core.validate.generate.CodeGenerator;
import com.elliot.security.core.validate.code.SMSValidateCode;
import org.apache.commons.lang.StringUtils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class SessionSMSCodeProcessor extends SessionValidateCodeProcessor {

    private String requestParameter;

    public SessionSMSCodeProcessor(CodeGenerator codeGenerator, String sessionName, String mobileParameter) {
        super(codeGenerator, sessionName);
        this.requestParameter = mobileParameter;
    }

    @Override
    protected void send(HttpServletRequest request, HttpServletResponse response, ValidateCode validateCode) {
        // 发送与验证的request parameter一致
        String phoneNum = ValidateCodeUtil.getValidateCodeFromRequest(request, requestParameter);
        if (StringUtils.isNotBlank(phoneNum)) {
            doSend(phoneNum, validateCode.getCode());
        }
    }

    protected void doSend(String phoneNum, String code) {
        System.out.println("向手机号" + phoneNum + "发送短信验证码" + code);
    }

    @Override
    protected void save(HttpServletRequest request, ValidateCode validateCode) {
        String phoneNum = ValidateCodeUtil.getValidateCodeFromRequest(request, requestParameter);
        SMSValidateCode smsValidateCode = new SMSValidateCode(validateCode, phoneNum);
        request.getSession().setAttribute(sessionName, smsValidateCode);
    }
}

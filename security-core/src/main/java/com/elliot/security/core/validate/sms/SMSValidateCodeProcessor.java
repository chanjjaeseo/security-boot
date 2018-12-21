package com.elliot.security.core.validate.sms;

import com.elliot.security.core.config.bean.SecurityBootBean;
import com.elliot.security.core.constant.SecurityConstant;
import com.elliot.security.core.validate.processor.AbstractValidateCodeGenerator;
import com.elliot.security.core.validate.processor.AbstractValidateCodeProcessor;
import com.elliot.security.core.validate.ValidateCode;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.ServletRequestUtils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Component("smsValidateCodeProcessor")
public class SMSValidateCodeProcessor extends AbstractValidateCodeProcessor {

    private Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    private SecurityBootBean securityBootBean;

    private SMSCodeGenerator smsCodeGenerator = new SMSCodeGenerator();

    @Override
    protected ValidateCode generate() {
        return smsCodeGenerator.generate();
    }

    @Override
    protected void send(HttpServletRequest request, HttpServletResponse response, ValidateCode validateCode) {
        // 发送与验证的request parameter一致
        String phoneNum = getMobileNumber(request, SecurityConstant.MobileLogin.MOBILE_REQUEST_PARAMETER);
        if (StringUtils.isNotBlank(phoneNum)) {
            doSend(phoneNum, validateCode.getCode());
        }
    }

    private String getMobileNumber(HttpServletRequest request, String requestParameter) {
        try {
            String phoneNum = ServletRequestUtils.getRequiredStringParameter(request, requestParameter);
            if (StringUtils.isNotBlank(phoneNum)) {
                return phoneNum;
            }
        } catch (Exception e) {
            logger.warn("获取手机号异常", e);
        }
        return null;
    }

    protected void doSend(String phoneNum, String code) {
        System.out.println("向手机号" + phoneNum + "发送短信验证码" + code);
    }

    @Override
    protected void save(HttpServletRequest request, ValidateCode validateCode) {
        String phoneNum = getMobileNumber(request, SecurityConstant.MobileLogin.MOBILE_REQUEST_PARAMETER);
        SMSValidateCode smsValidateCode = new SMSValidateCode(validateCode, phoneNum);
        request.getSession().setAttribute(SecurityConstant.ValidateCode.SMS_VALIDATE_CODE_SESSION_NAME, smsValidateCode);
    }

    protected class SMSCodeGenerator extends AbstractValidateCodeGenerator {

        @Override
        public ValidateCode generate() {
            int smsCodeLength = securityBootBean.getCode().getSms().getLength();
            int expiredMinutes = securityBootBean.getCode().getSms().getExpiredMinutes();
            String code = generateCode(smsCodeLength);
            return new ValidateCode(code, expiredMinutes);
        }

    }

}

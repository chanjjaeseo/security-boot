package com.elliot.security.core.validate.sms;

import com.elliot.security.core.config.bean.SMSCode;
import com.elliot.security.core.config.bean.SecurityBootBean;
import com.elliot.security.core.exception.ValidateException;
import com.elliot.security.core.validate.AbstractValidateCodeGenerator;
import com.elliot.security.core.validate.AbstractValidateCodeProcessor;
import com.elliot.security.core.validate.ValidateCode;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.ServletRequestBindingException;
import org.springframework.web.bind.ServletRequestUtils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Component("smsValidateCodeProcessor")
public class SMSValidateCodeProcessor extends AbstractValidateCodeProcessor implements InitializingBean {

    private Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    private SecurityBootBean securityBootBean;

    private SMSCodeGenerator smsCodeGenerator = new SMSCodeGenerator();

    private String phoneParameter;

    private String sessionName;

    @Override
    public void afterPropertiesSet() {
        SMSCode smsCode = securityBootBean.getCode().getSms();
        phoneParameter = smsCode.getPhoneParameter();
        sessionName = smsCode.getSessionAttribute();
    }

    @Override
    protected ValidateCode generate() {
        return smsCodeGenerator.generate();
    }

    @Override
    protected void send(HttpServletRequest request, HttpServletResponse response, ValidateCode validateCode) {
        String phoneNum = getMobileNumber(request);
        if (StringUtils.isNotBlank(phoneNum)) {
            doSend(phoneNum, validateCode.getCode());
        }
    }

    @Override
    protected void extraCheck(HttpServletRequest request, ValidateCode validateCode) {
        SMSValidateCode smsValidateCode = (SMSValidateCode) validateCode;
        String mobileInRequest = getMobileNumber(request);
        if (mobileInRequest == null || !mobileInRequest.equals(smsValidateCode.getMobile())) {
            throw new ValidateException("验证失败:手机号错误");
        }
    }

    private String getMobileNumber(HttpServletRequest request) {
        try {
            String phoneNum = ServletRequestUtils.getRequiredStringParameter(request, phoneParameter);
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
        String phoneNum = getMobileNumber(request);
        SMSValidateCode smsValidateCode = new SMSValidateCode(validateCode, phoneNum);
        request.getSession().setAttribute(sessionName, smsValidateCode);
    }


    protected class SMSCodeGenerator extends AbstractValidateCodeGenerator {

        @Override
        public ValidateCode generate() {
            int smsCodeLength = securityBootBean.getCode().getSms().getLength();
            int expiredMinutes = securityBootBean.getCode().getSms().getExpiredMinutes();
            String code = generateCodeString(smsCodeLength);
            return new ValidateCode(code, expiredMinutes);
        }

    }

}

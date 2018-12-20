package com.elliot.security.core.validate.sms;

import com.elliot.security.core.config.bean.SecurityBootBean;
import com.elliot.security.core.exception.ValidateException;
import com.elliot.security.core.validate.AbstractValidateCodeGenerator;
import com.elliot.security.core.validate.AbstractValidateCodeProcessor;
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
        String phoneParameter = securityBootBean.getCode().getSms().getPhoneParameter();
        String phoneNum = null;
        try {
            phoneNum = ServletRequestUtils.getRequiredStringParameter(request, phoneParameter);
            if (StringUtils.isBlank(phoneNum)) {
                throw new ValidateException("手机号不能为空");
            }
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
        }
        doSendSMSMessage(phoneNum, validateCode.getCode());
    }

    private void doSendSMSMessage(String phoneNum, String code) {
        System.out.println("向手机号" + phoneNum + "发送短信验证码" + code);
    }

    @Override
    protected void save(HttpServletRequest request, ValidateCode validateCode) {

    }

    @Override
    public void validate(HttpServletRequest request) {

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

package com.elliot.security.core.validate.generate;

import com.elliot.security.core.config.bean.SecurityBootBean;
import com.elliot.security.core.validate.code.ValidateCode;

public class SMSCodeGenerator extends AbstractValidateCodeGenerator{


    public SMSCodeGenerator(SecurityBootBean securityBootBean) {
        super(securityBootBean);
    }

    @Override
    public ValidateCode generate() {
        int smsCodeLength = securityBootBean.getCode().getSms().getLength();
        int expiredMinutes = securityBootBean.getCode().getSms().getExpiredMinutes();
        String code = generateCode(smsCodeLength);
        return new ValidateCode(code, expiredMinutes);
    }

}

package com.elliot.security.core.validate.sms;

import com.elliot.security.core.validate.ValidateCode;
import org.springframework.security.core.userdetails.UserDetails;

public class SMSValidateCode extends ValidateCode {

    private String mobile;

    public SMSValidateCode(ValidateCode validateCode, String mobile) {
        super(validateCode.getCode(), validateCode.getInvalidTime());
    }

    public SMSValidateCode(String mobile, String code, int delayMinutes) {
        super(code, delayMinutes);
        this.mobile = mobile;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }
}

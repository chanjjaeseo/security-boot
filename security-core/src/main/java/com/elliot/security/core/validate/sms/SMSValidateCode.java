package com.elliot.security.core.validate.sms;

import com.elliot.security.core.validate.ValidateCode;

public class SMSValidateCode extends ValidateCode {

    private String mobile;

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

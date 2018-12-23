package com.elliot.security.core.validate.code;

import java.time.LocalDateTime;


public class SMSValidateCode extends ValidateCode {

    private String mobile;

    public SMSValidateCode() {

    }

    public SMSValidateCode(String code, LocalDateTime invalidTime) {
        super(code, invalidTime);
    }


    public SMSValidateCode(ValidateCode validateCode, String mobile) {
        super(validateCode.getCode(), validateCode.getInvalidTime());
        this.mobile = mobile;
    }

//    public SMSValidateCode(String mobile, String code, int delayMinutes) {
//        super(code, delayMinutes);
//        this.mobile = mobile;
//    }

//    public SMSValidateCode(String code, LocalDateTime invalidTime, String mobile) {
//        super(code, invalidTime);
//        this.mobile = mobile;
//    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }
}

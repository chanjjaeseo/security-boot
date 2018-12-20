package com.elliot.security.core.config.bean;

public class SMSCode {

    private int length = 6;

    private int expiredMinutes = 3;

    private String phoneParameter = "v-code-sms";

    private String sessionAttribute = "VALIDATE_CODE_IMAGE";

    public String getPhoneParameter() {
        return phoneParameter;
    }

    public void setPhoneParameter(String phoneParameter) {
        this.phoneParameter = phoneParameter;
    }

    public int getLength() {
        return length;
    }

    public void setLength(int length) {
        this.length = length;
    }

    public int getExpiredMinutes() {
        return expiredMinutes;
    }

    public void setExpiredMinutes(int expiredMinutes) {
        this.expiredMinutes = expiredMinutes;
    }

    public String getSessionAttribute() {
        return sessionAttribute;
    }

    public void setSessionAttribute(String sessionAttribute) {
        this.sessionAttribute = sessionAttribute;
    }
}

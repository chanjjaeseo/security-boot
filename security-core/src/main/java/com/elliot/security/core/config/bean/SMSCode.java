package com.elliot.security.core.config.bean;

public class SMSCode {

    private int length = 6;

    private int expiredMinutes = 3;

    private String phoneParameter = "phone-num";

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
}

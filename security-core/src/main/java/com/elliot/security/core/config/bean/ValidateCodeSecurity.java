package com.elliot.security.core.config.bean;

public class ValidateCodeSecurity {

    private SMSCode sms = new SMSCode();

    private ImageCode image = new ImageCode();

    public SMSCode getSms() {
        return sms;
    }

    public void setSms(SMSCode sms) {
        this.sms = sms;
    }

    public ImageCode getImage() {
        return image;
    }

    public void setImage(ImageCode image) {
        this.image = image;
    }
}

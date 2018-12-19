package com.elliot.security.core.constant;

public enum ValidateCodeEnum {

    SMS("sms-code", "sms"),
    IMAGE("image-code", "image");

    private String requestParameter;

    private String type;

    ValidateCodeEnum(String requestParameter, String type) {
        this.requestParameter = requestParameter;
        this.type = type;
    }

    public String getRequestParameter() {
        return requestParameter;
    }

    public String getType() {
        return type;
    }
}

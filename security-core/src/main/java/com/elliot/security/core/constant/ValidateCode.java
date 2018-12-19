package com.elliot.security.core.constant;

public enum ValidateCode {

    SMS("sms-code", "sms"),
    IMAGE("image-code", "image");

    private String requestParameter;

    private String urlSuffix;

    ValidateCode(String requestParameter, String urlSuffix) {
        this.requestParameter = requestParameter;
        this.urlSuffix = urlSuffix;
    }

    public String getRequestParameter() {
        return requestParameter;
    }

    public String getUrlSuffix() {
        return urlSuffix;
    }
}

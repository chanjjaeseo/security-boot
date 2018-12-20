package com.elliot.security.core.config.bean;

public class ImageCode {

    private String requestParameter = "v-code-image";

    private String sessionAttibute = "VALIDATE_CODE_IMAGE";

    private int length = 4;

    private int width = 70;

    private int height = 25;

    private int expiredMinutes = 3;

    public int getLength() {
        return length;
    }

    public void setLength(int length) {
        this.length = length;
    }

    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public int getExpiredMinutes() {
        return expiredMinutes;
    }

    public void setExpiredMinutes(int expiredMinutes) {
        this.expiredMinutes = expiredMinutes;
    }

    public String getRequestParameter() {
        return requestParameter;
    }

    public void setRequestParameter(String requestParameter) {
        this.requestParameter = requestParameter;
    }

    public String getSessionAttibute() {
        return sessionAttibute;
    }

    public void setSessionAttibute(String sessionAttibute) {
        this.sessionAttibute = sessionAttibute;
    }
}

package com.elliot.security.core.validate;

import java.time.LocalDateTime;

public class ValidateCode {

    private String code;

    private LocalDateTime invalidTime;

    public ValidateCode(String code, int delayMinutes) {
        this.code = code;
        this.invalidTime = LocalDateTime.now().plusMinutes((long) delayMinutes);
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public LocalDateTime getInvalidTime() {
        return invalidTime;
    }

    public void setInvalidTime(LocalDateTime invalidTime) {
        this.invalidTime = invalidTime;
    }
}

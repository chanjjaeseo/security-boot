package com.elliot.security.core.validate.code;

import java.time.LocalDateTime;

public class ValidateCode {

    private String code;

    private LocalDateTime invalidTime;

    public ValidateCode() {

    }

    public ValidateCode(String code, int delayMinutes) {
        this(code, LocalDateTime.now().plusMinutes((long) delayMinutes));
    }

    public ValidateCode(String code, LocalDateTime invalidTime) {
        this.code = code;
        this.invalidTime = invalidTime;
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

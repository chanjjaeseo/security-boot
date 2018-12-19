package com.elliot.security.core.exception;

import org.springframework.security.core.AuthenticationException;

public class ValidateException extends AuthenticationException {

    public ValidateException(String exDesc) {
        super(exDesc);
    }

}

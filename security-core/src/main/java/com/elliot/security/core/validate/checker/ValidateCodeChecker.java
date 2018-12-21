package com.elliot.security.core.validate.checker;


import org.springframework.security.core.AuthenticationException;

import javax.servlet.http.HttpServletRequest;

public interface ValidateCodeChecker {

    void validate(HttpServletRequest request, Object validateParam) throws AuthenticationException;

}

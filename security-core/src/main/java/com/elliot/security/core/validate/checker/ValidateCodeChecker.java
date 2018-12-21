package com.elliot.security.core.validate.checker;

import javax.servlet.http.HttpServletRequest;

public interface ValidateCodeChecker {

    void validate(HttpServletRequest request);

}

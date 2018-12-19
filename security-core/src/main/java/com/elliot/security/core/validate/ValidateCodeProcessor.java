package com.elliot.security.core.validate;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public interface ValidateCodeProcessor {

    void create(HttpServletRequest request, HttpServletResponse response);


    void validate(HttpServletRequest request);
}

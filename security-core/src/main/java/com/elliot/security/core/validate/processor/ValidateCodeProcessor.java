package com.elliot.security.core.validate.processor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public interface ValidateCodeProcessor {

    void create(HttpServletRequest request, HttpServletResponse response);

}

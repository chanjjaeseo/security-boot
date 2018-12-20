package com.elliot.security.core.validate;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public abstract class AbstractValidateCodeProcessor implements ValidateCodeProcessor {

    protected Logger logger = LoggerFactory.getLogger(getClass());

    @Override
    public void create(HttpServletRequest request, HttpServletResponse response) {
        ValidateCode code = generate();
        save(request, code);
        send(request, response, code);
    }

    protected abstract ValidateCode generate();

    protected abstract void save(HttpServletRequest request, ValidateCode validateCode);

    protected abstract void send(HttpServletRequest request, HttpServletResponse response, ValidateCode validateCode);

}

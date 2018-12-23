package com.elliot.security.core.validate.processor;

import com.elliot.security.core.validate.code.ValidateCode;
import com.elliot.security.core.util.ValidateCodeUtil;
import com.elliot.security.core.validate.generate.CodeGenerator;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public abstract class SessionValidateCodeProcessor extends AbstractValidateCodeProcessor {

    protected String sessionName;

    public SessionValidateCodeProcessor(CodeGenerator codeGenerator, String sessionName) {
        super(codeGenerator);
        this.sessionName = sessionName;
    }

    @Override
    protected void save(HttpServletRequest request, ValidateCode validateCode) {
        ValidateCodeUtil.setValidateCodeToSession(request, sessionName, validateCode);
    }

    @Override
    abstract protected void send(HttpServletRequest request, HttpServletResponse response, ValidateCode validateCode);
}

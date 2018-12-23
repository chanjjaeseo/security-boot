package com.elliot.security.core.validate.processor;

import com.elliot.security.core.validate.code.ValidateCode;
import com.elliot.security.core.validate.generate.CodeGenerator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public abstract class AbstractValidateCodeProcessor implements ValidateCodeProcessor {

    protected Logger logger = LoggerFactory.getLogger(getClass());

    private CodeGenerator codeGenerator;

    public AbstractValidateCodeProcessor(CodeGenerator codeGenerator) {
        this.codeGenerator = codeGenerator;
    }

    @Override
    public void create(HttpServletRequest request, HttpServletResponse response) {
        ValidateCode code = codeGenerator.generate();
        save(request, code);
        send(request, response, code);
    }

    protected abstract void save(HttpServletRequest request, ValidateCode validateCode);

    protected abstract void send(HttpServletRequest request, HttpServletResponse response, ValidateCode validateCode);


}

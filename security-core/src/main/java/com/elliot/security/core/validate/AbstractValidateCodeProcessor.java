package com.elliot.security.core.validate;

import com.elliot.security.core.exception.ValidateException;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.web.bind.ServletRequestBindingException;
import org.springframework.web.bind.ServletRequestUtils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.time.LocalDateTime;

public abstract class AbstractValidateCodeProcessor implements ValidateCodeProcessor {

    protected Logger logger = LoggerFactory.getLogger(getClass());

    private static final String EMPTY_VALIDATE_CODE = "&";

    protected String sessionAttribute;

    protected String requestParameter;

    @Override
    public void create(HttpServletRequest request, HttpServletResponse response) {
        ValidateCode code = generate();
        save(request, code);
        send(request, response, code);
    }

    protected abstract ValidateCode generate();

    protected abstract void save(HttpServletRequest request, ValidateCode validateCode);

    protected abstract void send(HttpServletRequest request, HttpServletResponse response, ValidateCode validateCode);

    public void validate(HttpServletRequest request) {
        String code = getValidateCodeFromRequest(request);
        ValidateCode codeInSession = getValidateCodeFromSession(request);
        if (StringUtils.isBlank(code)) {
            throw new ValidateException("验证码不能为空");
        }
        if (LocalDateTime.now().isAfter(codeInSession.getInvalidTime())) {
            throw new ValidateException("验证码已失效");
        }
        if (!codeInSession.getCode().equals(code)) {
            throw new ValidateException("验证码不匹配");
        }
        extraCheck(request, codeInSession);
    }

    protected abstract void extraCheck(HttpServletRequest request, ValidateCode validateCode);

    protected String getValidateCodeFromRequest(HttpServletRequest request) {
        String requestName = getRequestParameter();
        String validateCode = EMPTY_VALIDATE_CODE;
        try {
            validateCode = ServletRequestUtils.getStringParameter(request, requestName);
        } catch (ServletRequestBindingException e) {
            logger.error("从request中获取验证码失败", e);
        }
        return validateCode;
    }

    // only web support
    protected ValidateCode getValidateCodeFromSession(HttpServletRequest request) {
        String sessionName = getSessionAttribute();
        ValidateCode codeInSession = (ValidateCode) request.getSession().getAttribute(sessionName);
        request.getSession().removeAttribute(sessionName);
        return codeInSession;
    }

    public String getRequestParameter() {
        return requestParameter;
    }


    public String getSessionAttribute() {
        return sessionAttribute;
    }

    public void setSessionAttribute(String sessionAttribute) {
        this.sessionAttribute = sessionAttribute;
    }

    public void setRequestParameter(String requestParameter) {
        this.requestParameter = requestParameter;
    }
}

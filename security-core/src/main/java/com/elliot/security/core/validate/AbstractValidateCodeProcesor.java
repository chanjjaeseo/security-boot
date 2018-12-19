package com.elliot.security.core.validate;

import com.elliot.security.core.exception.ValidateException;
import org.apache.commons.lang.StringUtils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.time.LocalDateTime;

public abstract class AbstractValidateCodeProcesor implements ValidateCodeProcessor  {

    private static final String CLASS_SUFFIX = "ValidateCodeProccessor";

    private static final String VALIDATE_CODE_SESSION_PREFIX = "VALIDATE_CODE-";

    private static final String VALIDATE_CODE_REQUEST_PREFIX = "v_code_";

    @Override
    public void create(HttpServletRequest request, HttpServletResponse response) {
        ValidateCode code = generate();
        save(code, request);
        send(request, response, code);
    }

    protected abstract ValidateCode generate();

    protected abstract void send(HttpServletRequest request, HttpServletResponse response, ValidateCode validateCode);

    public void save(ValidateCode validateCode, HttpServletRequest request) {
        String sessionName = getSessionName();
        request.getSession().setAttribute(sessionName, validateCode);
    }

    private String getSessionName() {
        String validatecodeType = getValidateCodeType();
        return VALIDATE_CODE_SESSION_PREFIX + validatecodeType;
    }

    private String getValidateCodeType() {
        String className = this.getClass().getSimpleName();
        String codeType = StringUtils.substringBefore(className, CLASS_SUFFIX);
        return codeType.toUpperCase();
    }

    private String getValidateCodeFromRequest(HttpServletRequest request) {
        String requestName = VALIDATE_CODE_REQUEST_PREFIX + getValidateCodeType();
        Object requestParameter = request.getAttribute(requestName);
        return requestParameter == null ? null : (String) requestParameter;
    }

    @Override
    public void validate(HttpServletRequest request) {
        String code = getValidateCodeFromRequest(request);
        String sessionName = getSessionName();
        ValidateCode codeInSession = (ValidateCode) request.getSession().getAttribute(sessionName);
        request.getSession().removeAttribute(sessionName);
        if (StringUtils.isBlank(code)) {
            throw new ValidateException("验证码不能为空");
        }
        if (LocalDateTime.now().isAfter(codeInSession.getInvalidTime())) {
            throw new ValidateException("验证码已失效");
        }
        if (codeInSession.getCode().equals(code)) {
            throw new ValidateException("验证码不匹配");
        }
    }
}

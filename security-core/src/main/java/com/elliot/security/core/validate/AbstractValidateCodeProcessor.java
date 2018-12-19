package com.elliot.security.core.validate;

import com.elliot.security.core.constant.ValidateCodeEnum;
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

public abstract class AbstractValidateCodeProcessor implements ValidateCodeProcessor, InitializingBean {

    protected Logger logger = LoggerFactory.getLogger(getClass());

    private static final String CLASS_SUFFIX = "ValidateCodeProcessor";

    private static final String VALIDATE_CODE_SESSION_PREFIX = "VALIDATE_CODE-";

    private static final String VALIDATE_CODE_REQUEST_PREFIX = "v-code-";

    private static final String EMPTY_VALIDATE_CODE = "&";

    private ValidateCodeEnum validateCodeEnum;

    @Override
    public void afterPropertiesSet() {
        validateCodeEnum = getValidateCodeEunm();
    }

    private ValidateCodeEnum getValidateCodeEunm() {
        String className = this.getClass().getSimpleName();
        String prefix = StringUtils.substringBefore(className, CLASS_SUFFIX);
        for (ValidateCodeEnum validateCodeEnum : ValidateCodeEnum.values()) {
            boolean found = validateCodeEnum.getType().equals(prefix.toLowerCase());
            if (found) return validateCodeEnum;
        }
        return null;
    }

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
        if (validateCodeEnum == null)
            throw new ValidateException("根据processor class信息查找不到关键信息");
        return VALIDATE_CODE_SESSION_PREFIX + validateCodeEnum.getType();
    }

    private String getValidateCodeFromRequest(HttpServletRequest request) {
        if (validateCodeEnum == null)
            throw new ValidateException("根据processor class信息查找不到关键信息");
        String requestName = VALIDATE_CODE_REQUEST_PREFIX + validateCodeEnum.getType();
        String validateCode = EMPTY_VALIDATE_CODE;
        try {
             validateCode = ServletRequestUtils.getStringParameter(request, requestName);
        } catch (ServletRequestBindingException e) {
            logger.error("从request中获取验证码失败", e);
        }
        return validateCode;
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
        if (!codeInSession.getCode().equals(code)) {
            throw new ValidateException("验证码不匹配");
        }
    }
}

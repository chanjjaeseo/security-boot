package com.elliot.security.core.validate.checker;


import com.elliot.security.core.exception.ValidateException;
import com.elliot.security.core.validate.code.ValidateCode;
import com.elliot.security.core.util.ValidateCodeUtil;
import org.apache.commons.lang.StringUtils;
import org.springframework.security.core.AuthenticationException;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDateTime;

public abstract class StorageValidateCodeChecker implements ValidateCodeChecker {

    private String requestParameter;

    public StorageValidateCodeChecker(String requestParameter) {
        this.requestParameter = requestParameter;
    }

    @Override
    public void validate(HttpServletRequest request, Object storageId) throws AuthenticationException {
        ValidateCode codeInStorage = getValidateCodeFromStorage(request, (String) storageId);
        String code = ValidateCodeUtil.getValidateCodeFromRequest(request, requestParameter);
        preCheck(codeInStorage, code);
        postCheck(request, codeInStorage);
    }

    private void preCheck(ValidateCode codeInStorage, String code) {
        if (codeInStorage == null) {
            throw new ValidateException("验证码已失效");
        }
        if (StringUtils.isBlank(code)) {
            throw new ValidateException("验证码不能为空");
        }
        if (LocalDateTime.now().isAfter(codeInStorage.getInvalidTime())) {
            throw new ValidateException("验证码已失效");
        }
        if (!codeInStorage.getCode().equals(code)) {
            throw new ValidateException("验证码不匹配");
        }
    }

    protected abstract void postCheck(HttpServletRequest request, ValidateCode codeInStorage);

    protected abstract ValidateCode getValidateCodeFromStorage(HttpServletRequest request, String storageId);

    public String getRequestParameter() {
        return requestParameter;
    }

    public void setRequestParameter(String requestParameter) {
        this.requestParameter = requestParameter;
    }

}

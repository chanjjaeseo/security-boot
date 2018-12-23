package com.elliot.security.core.validate.checker;

import com.elliot.security.core.validate.code.ValidateCode;
import com.elliot.security.core.util.ValidateCodeUtil;

import javax.servlet.http.HttpServletRequest;

public class DefaultValidateCodeChecker extends StorageValidateCodeChecker {

    private String sessionName;

    public DefaultValidateCodeChecker(String requestParameter, String sessionName) {
        super(requestParameter);
        this.sessionName = sessionName;
    }

    protected void postCheck(HttpServletRequest request, ValidateCode codeInStorage) {
        return;
    }

    @Override
    protected ValidateCode getValidateCodeFromStorage(HttpServletRequest request, String storageId) {
        return ValidateCodeUtil.getCodeFromSession(request, sessionName);
    }
}

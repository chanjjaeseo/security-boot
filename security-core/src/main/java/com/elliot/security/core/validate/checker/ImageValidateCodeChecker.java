package com.elliot.security.core.validate.checker;

import com.elliot.security.core.constant.SecurityConstant;
import com.elliot.security.core.validate.ValidateCode;

import javax.servlet.http.HttpServletRequest;

public class ImageValidateCodeChecker extends StorageValidateCodeChecker {

    public ImageValidateCodeChecker(String requestParameter, String storageId) {
        super(requestParameter, storageId);
    }

    public ImageValidateCodeChecker() {
        super(SecurityConstant.ValidateCode.IMAGE_VALIDATE_CODE_REQUEST_NAME, SecurityConstant.ValidateCode.IMAGE_VALIDATE_CODE_SESSION_NAME);
    }

    @Override
    protected void postCheck(HttpServletRequest request, ValidateCode codeInStorage) {
        return;
    }

    @Override
    protected ValidateCode getValidateCodeFromStorage(HttpServletRequest request, String storageId) {
        ValidateCode codeInSession = (ValidateCode) request.getSession().getAttribute(storageId);
        request.getSession().removeAttribute(storageId);
        return codeInSession;
    }

}

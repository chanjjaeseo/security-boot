package com.elliot.security.core.validate.checker;

import com.elliot.security.core.constant.SecurityConstant;
import com.elliot.security.core.exception.ValidateException;
import com.elliot.security.core.util.WebUtil;
import com.elliot.security.core.validate.ValidateCode;
import com.elliot.security.core.validate.sms.SMSValidateCode;

import javax.servlet.http.HttpServletRequest;

public class SMSValidateCodeChecker extends StorageValidateCodeChecker {

    private String mobileParameter;

    public SMSValidateCodeChecker() {
        super(SecurityConstant.ValidateCode.SMS_VALIDATE_CODE_REQUEST_NAME, SecurityConstant.ValidateCode.SMS_VALIDATE_CODE_SESSION_NAME);
        this.mobileParameter = SecurityConstant.MobileLogin.MOBILE_REQUEST_PARAMETER;
    }

    public SMSValidateCodeChecker(String requestParameter, String mobileParameter, String storageId) {
        super(requestParameter, storageId);
        this.mobileParameter = mobileParameter;
    }

    @Override
    protected void postCheck(HttpServletRequest request, ValidateCode codeInStorage) {
        SMSValidateCode code = (SMSValidateCode) codeInStorage;
        String mobileInStorage = code.getMobile();
        String mobileInRequest = WebUtil.getValidateCodeFromRequest(request, mobileParameter);
        if (!mobileInStorage.equals(mobileInRequest)) {
            throw new ValidateException("验证码错误");
        }
    }

    @Override
    protected ValidateCode getValidateCodeFromStorage(HttpServletRequest request, String storageId) {
        ValidateCode codeInSession = (ValidateCode) request.getSession().getAttribute(storageId);
        request.getSession().removeAttribute(storageId);
        return codeInSession;
    }
}

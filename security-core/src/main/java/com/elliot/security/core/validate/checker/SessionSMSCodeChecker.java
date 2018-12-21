package com.elliot.security.core.validate.checker;


import com.elliot.security.core.exception.ValidateException;
import com.elliot.security.core.validate.ValidateCode;
import com.elliot.security.core.validate.ValidateCodeUtil;
import com.elliot.security.core.validate.sms.SMSValidateCode;

import javax.servlet.http.HttpServletRequest;

public class SessionSMSCodeChecker extends DefaultValidateCodeChecker {

    public SessionSMSCodeChecker(String requestParameter, String sessionName) {
        super(requestParameter, sessionName);
    }

    @Override
    protected void postCheck(HttpServletRequest request, ValidateCode codeInStorage) {
        String mobile = ValidateCodeUtil.getValidateCodeFromRequest(request, "mobile");
        SMSValidateCode code = (SMSValidateCode) codeInStorage;
        if (code == null && !mobile.equals(code.getMobile())) {
            throw new ValidateException("手机号验证失败");
        }
    }
}

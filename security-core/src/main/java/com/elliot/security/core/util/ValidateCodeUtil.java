package com.elliot.security.core.util;

import com.elliot.security.core.validate.code.ValidateCode;
import org.springframework.web.bind.ServletRequestBindingException;
import org.springframework.web.bind.ServletRequestUtils;

import javax.servlet.http.HttpServletRequest;

public class ValidateCodeUtil {

    public static ValidateCode getCodeFromSession(HttpServletRequest request, String sessionName) {
        ValidateCode codeInSession = (ValidateCode) request.getSession().getAttribute(sessionName);
        request.getSession().removeAttribute(sessionName);
        return codeInSession;
    }

    public static String getValidateCodeFromRequest(HttpServletRequest request, String requestParameter) {
        String code = "";
        try {
            code = ServletRequestUtils.getStringParameter(request, requestParameter);
            code = code.trim();
        } catch (ServletRequestBindingException e) {
            //
        }
        return code;
    }

    public static void setValidateCodeToSession(HttpServletRequest request, String sessionName,ValidateCode validateCode) {
        request.getSession().setAttribute(sessionName, validateCode);
    }

}

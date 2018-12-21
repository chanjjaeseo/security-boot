package com.elliot.security.core.util;

import com.elliot.security.core.validate.ValidateCode;
import org.springframework.web.bind.ServletRequestBindingException;
import org.springframework.web.bind.ServletRequestUtils;

import javax.servlet.http.HttpServletRequest;

public class WebUtil {

    public static String getValidateCodeFromRequest(HttpServletRequest request, String requestParameter) {
        String code = "";
        try {
            code = ServletRequestUtils.getStringParameter(request, requestParameter);
        } catch (ServletRequestBindingException e) {
            //
        }
        return code;
    }

    // only web support
    public static ValidateCode getValidateCodeFromSession(HttpServletRequest request, String sessionAttribute) {
        ValidateCode codeInSession = (ValidateCode) request.getSession().getAttribute(sessionAttribute);
        request.getSession().removeAttribute(sessionAttribute);
        return codeInSession;
    }

    public static void setValidateCodeToSession(HttpServletRequest request, String sessionName,ValidateCode validateCode) {
        request.getSession().setAttribute(sessionName, validateCode);
    }

}

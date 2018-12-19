package com.elliot.security.core.constant;

public interface SecurityConstant {

    interface FormLogin {

        String LOGIN_PAGE_URL = "/signIn.html";

        String LOGIN_PROCESS_URL = "/login/form";

    }

    interface MobileLogin {

        String LOGIN_PROCESS_URL = "/login/sms";

    }

    interface ValidateCode {

        String VALIDATE_CODE_URL_PREFIX = "/code";

    }

}

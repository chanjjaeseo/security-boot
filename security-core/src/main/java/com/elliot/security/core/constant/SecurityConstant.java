package com.elliot.security.core.constant;

public interface SecurityConstant {

    interface FormLogin {

        String LOGIN_PAGE_URL = "/signIn.html";

        String LOGIN_PROCESS_URL = "/login/form";

    }

    interface MobileLogin {

        String LOGIN_PROCESS_URL = "/login/sms";

        String MOBILE_REQUEST_PARAMETER = "mobile";

    }

    interface ValidateCode {

        String VALIDATE_CODE_URL_PREFIX = "/code";

        String SMS_VALIDATE_CODE_SESSION_NAME = "sms_v_code_session";

        String IMAGE_VALIDATE_CODE_SESSION_NAME = "image_v_code_session";

        String SMS_VALIDATE_CODE_REQUEST_NAME = "sms_v_code";

        String IMAGE_VALIDATE_CODE_REQUEST_NAME = "image_v_code";

    }

    interface OAuth {

        String OAUTH_URL_PREFIX = "/oauth";

    }

}

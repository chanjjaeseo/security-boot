package com.elliot.security.core.validate.checker;

import com.elliot.security.core.validate.ValidateCode;
import com.elliot.security.core.validate.sms.SMSValidateCode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.data.redis.core.RedisTemplate;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

public class RedisSMSCodeChecker extends StorageValidateCodeChecker {

    private RedisTemplate redisTemplate;

    private final static String SMS_CODE_REDIS__HASH_KEY = "sms_validate_code";

    private ObjectMapper objectMapper = new ObjectMapper();

    public RedisSMSCodeChecker(String requestParameter) {
        super(requestParameter);
    }

    @Override
    protected void postCheck(HttpServletRequest request, ValidateCode codeInStorage) {

    }

    @Override
    protected SMSValidateCode getValidateCodeFromStorage(HttpServletRequest request, String storageId) {
        String content = (String) redisTemplate.opsForHash().get(SMS_CODE_REDIS__HASH_KEY, storageId);
        SMSValidateCode code = null;
        try {
            code = objectMapper.readValue(content, SMSValidateCode.class);
        } catch (IOException e) {
            //
        }
        return code;
    }

}

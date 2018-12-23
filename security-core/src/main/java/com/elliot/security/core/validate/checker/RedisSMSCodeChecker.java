package com.elliot.security.core.validate.checker;

import com.elliot.security.core.exception.ValidateException;
import com.elliot.security.core.util.JsonUtil;
import com.elliot.security.core.validate.code.ValidateCode;
import com.elliot.security.core.util.ValidateCodeUtil;
import com.elliot.security.core.validate.code.SMSValidateCode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.data.redis.core.RedisTemplate;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

public class RedisSMSCodeChecker extends StorageValidateCodeChecker {

    private RedisTemplate redisTemplate;

    private String redisHashKey;

    public RedisSMSCodeChecker(String requestParameter, String redisHashKey) {
        super(requestParameter);
        this.redisHashKey = redisHashKey;
    }

    @Override
    protected void postCheck(HttpServletRequest request, ValidateCode codeInStorage) {
        String mobile = ValidateCodeUtil.getValidateCodeFromRequest(request, "mobile");
        SMSValidateCode code = (SMSValidateCode) codeInStorage;
        if (code == null && !mobile.equals(code.getMobile())) {
            throw new ValidateException("手机号验证失败");
        }
    }


    @Override
    protected SMSValidateCode getValidateCodeFromStorage(HttpServletRequest request, String storageId) {
        String content = (String) redisTemplate.opsForHash().get(redisHashKey, storageId);
        SMSValidateCode code = null;
        try {
            ObjectMapper objectMapper = JsonUtil.getObjectMapper();
            code = objectMapper.readValue(content, SMSValidateCode.class);
        } catch (IOException e) {
            //
        }
        return code;
    }

    public static void main(String[] args) throws IOException {
        String content = "{\"code\":\"184628\",\"invalidTime\":\"2018:12:23 12:26:53\",\"mobile\":\"13047187816\"}";
        ObjectMapper objectMapper = JsonUtil.getObjectMapper();
        SMSValidateCode code = objectMapper.readValue(content, SMSValidateCode.class);
        System.out.println(code);
//        String content;
//        System.out.println(content = objectMapper.writeValueAsString(LocalDateTime.now()));
//        LocalDateTime localDateTime = objectMapper.readValue(content, LocalDateTime.class);
//        System.out.println(localDateTime);
    }

    public RedisTemplate getRedisTemplate() {
        return redisTemplate;
    }

    public void setRedisTemplate(RedisTemplate redisTemplate) {
        this.redisTemplate = redisTemplate;
    }
}

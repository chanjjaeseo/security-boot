package com.elliot.security.core.validate.processor;

import com.elliot.security.core.util.JsonUtil;
import com.elliot.security.core.validate.code.SMSValidateCode;
import com.elliot.security.core.validate.code.ValidateCode;
import com.elliot.security.core.util.ValidateCodeUtil;
import com.elliot.security.core.validate.generate.CodeGenerator;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.commons.lang.StringUtils;
import org.springframework.data.redis.core.RedisTemplate;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class SMSValidateCodeProcessor extends AbstractValidateCodeProcessor {

    private String storagePrefix;

    private String requestParameter;

    private String redisHashKey;

    private RedisTemplate redisTemplate;

    public SMSValidateCodeProcessor(CodeGenerator codeGenerator,
                                    String requestParameter,
                                    String storagePrefix,
                                    String redisHashKey,
                                    RedisTemplate redisTemplate) {
        super(codeGenerator);
        this.requestParameter = requestParameter;
        this.storagePrefix = storagePrefix;
        this.redisHashKey = redisHashKey;
        this.redisTemplate = redisTemplate;
    }

    public SMSValidateCodeProcessor(CodeGenerator codeGenerator, RedisTemplate redisTemplate) {
        super(codeGenerator);
        this.requestParameter = "mobile";
        this.storagePrefix = "mobile-";
        this.redisHashKey = "sms_validate_code";
        this.redisTemplate = redisTemplate;
    }

    @Override
    protected void save(HttpServletRequest request, ValidateCode validateCode) {
        String phoneNum = ValidateCodeUtil.getValidateCodeFromRequest(request, requestParameter);
        SMSValidateCode smsValidateCode = new SMSValidateCode(validateCode, phoneNum);
        try {
            ObjectMapper objectMapper = JsonUtil.getObjectMapper();
            String redisContent = objectMapper.writeValueAsString(smsValidateCode);
            String storageId = storagePrefix + phoneNum;
            redisTemplate.opsForHash().put(redisHashKey, storageId, redisContent);
        } catch (Exception e) {
            //
        }
    }

    @Override
    protected void send(HttpServletRequest request, HttpServletResponse response, ValidateCode validateCode) {
        // 发送与验证的request parameter一致
        String phoneNum = ValidateCodeUtil.getValidateCodeFromRequest(request, requestParameter);
        if (StringUtils.isNotBlank(phoneNum)) {
            doSend(phoneNum, validateCode.getCode());
        }
    }

    protected void doSend(String phoneNum, String code) {
        System.out.println("向手机号" + phoneNum + "发送短信验证码" + code);
    }

}

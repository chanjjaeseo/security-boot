package com.elliot.security.core.config;

import com.elliot.security.core.config.bean.SecurityBootBean;
import com.elliot.security.core.constant.SecurityConstant;
import com.elliot.security.core.validate.generate.ImageCodeGenerator;
import com.elliot.security.core.validate.generate.SMSCodeGenerator;
import com.elliot.security.core.validate.processor.SMSValidateCodeProcessor;
import com.elliot.security.core.validate.processor.SessionImageCodeProcessor;
import com.elliot.security.core.validate.processor.SessionSMSCodeProcessor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import javax.websocket.Session;

@Configuration
public class GlobalSecurityConfig {

    @Autowired
    private SecurityBootBean securityBootBean;

    @Autowired
    private RedisTemplate redisTemplate;

    @Bean
    @ConditionalOnMissingBean(PasswordEncoder.class)
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

//    @Bean
//    public SessionImageCodeProcessor sessionImageCodeProcessor() {
//        ImageCodeGenerator generator = new ImageCodeGenerator(securityBootBean);
//        return new SessionImageCodeProcessor(generator, SecurityConstant.ValidateCode.IMAGE_VALIDATE_CODE_SESSION_NAME);
//    }
//
//    @Bean
//    public SessionSMSCodeProcessor sessionSMSCodeProcessor() {
//        SMSCodeGenerator generator = new SMSCodeGenerator(securityBootBean);
//        return new SessionSMSCodeProcessor(generator,
//                SecurityConstant.ValidateCode.SMS_VALIDATE_CODE_SESSION_NAME,
//                "mobile");
//    }

    @Bean
    public SMSValidateCodeProcessor smsValidateCodeProcessor() {
        SMSCodeGenerator generator = new SMSCodeGenerator(securityBootBean);
        return new SMSValidateCodeProcessor(generator, redisTemplate);
    }

}

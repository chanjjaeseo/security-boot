package com.elliot.security.core.validate;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@RestController
public class ValidateController {

    private final static String SMS_CODE = "sms";

    private final static String IMAGE_CODE = "image";

    @Autowired
    private ValidateCodeProcessor imageValidateCodeProcessor;

    @Autowired
    private ValidateCodeProcessor smsValidateCodeProcessor;

    @GetMapping("/code/{type}")
    public void smsValidateCode(@PathVariable String type, HttpServletRequest request, HttpServletResponse response) {
        if (type.equals(SMS_CODE)) {
            smsValidateCodeProcessor.create(request, response);
        } else if (type.equals(IMAGE_CODE)) {
            imageValidateCodeProcessor.create(request, response);
        }
    }


}

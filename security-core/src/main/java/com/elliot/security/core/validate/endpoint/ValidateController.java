package com.elliot.security.core.validate.endpoint;

import com.elliot.security.core.constant.ValidateCode;
import com.elliot.security.core.validate.ValidateCodeProcessor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@RestController
public class ValidateController {

    @Autowired
    private ValidateCodeProcessor imageValidateCodeProcessor;

    @Autowired
    private ValidateCodeProcessor smsValidateCodeProcessor;

    @GetMapping("/code/{type}")
    public void smsValidateCode(@PathVariable String type, HttpServletRequest request, HttpServletResponse response) {
        if (type.equals(ValidateCode.SMS.getUrlSuffix())) {
            smsValidateCodeProcessor.create(request, response);
        } else if (type.equals(ValidateCode.IMAGE.getUrlSuffix())) {
            imageValidateCodeProcessor.create(request, response);
        }
    }


}

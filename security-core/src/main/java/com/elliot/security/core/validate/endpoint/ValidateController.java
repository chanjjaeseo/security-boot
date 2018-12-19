package com.elliot.security.core.validate.endpoint;

import com.elliot.security.core.constant.ValidateCode;
import com.elliot.security.core.validate.ValidateCodeProcessor;
import com.elliot.security.core.validate.ValidateCodeProcessorHolder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@RestController
public class ValidateController {

    @Autowired
    private ValidateCodeProcessorHolder validateCodeProcessorHolder;

    @GetMapping("/code/{type}")
    public void smsValidateCode(@PathVariable String type, HttpServletRequest request, HttpServletResponse response) {
        ValidateCodeProcessor processor = findValidateCodeProcessor(type);
        if (processor != null) {
            processor.create(request, response);
        }
    }

    private ValidateCodeProcessor findValidateCodeProcessor(String type) {
        for(ValidateCode validateCode : ValidateCode.values()) {
            if(validateCode.getUrlSuffix().equals(type)) {
                return validateCodeProcessorHolder.getProcessorByType(validateCode);
            }
        }
        return null;
    }


}

package com.elliot.security.core.validate;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@RestController
public class ValidateController {

    @GetMapping("/code/sms")
    public void smsValidateCode(HttpServletRequest request, HttpServletResponse response) {

    }

    @GetMapping("/code/picture")
    public void pictrueValidateCode(HttpServletRequest request, HttpServletResponse response) {

    }


}

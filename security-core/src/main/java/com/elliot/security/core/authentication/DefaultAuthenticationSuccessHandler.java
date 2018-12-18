package com.elliot.security.core.authentication;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component("defaultAuthenticationSuccessHandler")
public class DefaultAuthenticationSuccessHandler implements AuthenticationSuccessHandler{

    @Autowired
    private ObjectMapper objectMapper;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
                                        Authentication authentication) throws IOException, ServletException {
        String authenticationDetails = objectMapper.writeValueAsString(authentication.getDetails());
        response.setContentType("application/json;charset=UTF-8");
        response.getWriter().write(authenticationDetails);
    }

}

package com.elliot.security.app.oauth;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component("defaultAuthenticationFailureHandler")
public class DefaultAuthenticationFailureHandler implements AuthenticationFailureHandler{

    @Autowired
    private ObjectMapper objectMapper;

    @Override
    public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response,
                                        AuthenticationException exception) throws IOException, ServletException {
        String authention = objectMapper.writeValueAsString(exception.getCause());
        response.setContentType("application/json;charset=UTF-8");
        response.getWriter().write(authention);
    }

}

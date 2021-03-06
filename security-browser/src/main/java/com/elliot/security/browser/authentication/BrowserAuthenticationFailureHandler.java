package com.elliot.security.browser.authentication;

import com.elliot.security.core.util.JsonUtil;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component("browserAuthenticationFailureHandler")
public class BrowserAuthenticationFailureHandler implements AuthenticationFailureHandler {

    @Override
    public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response,
                                        AuthenticationException exception) throws IOException, ServletException {
        ObjectMapper objectMapper = JsonUtil.getObjectMapper();
        String authentication = objectMapper.writeValueAsString(exception.getMessage());
        response.setContentType("application/json;charset=UTF-8");
        response.getWriter().write(authentication);
    }

}

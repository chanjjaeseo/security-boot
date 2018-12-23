package com.elliot.security.browser.authentication;

import com.elliot.security.core.util.JsonUtil;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component("browserAuthenticationSuccessHandler")
public class BrowserAuthenticationSuccessHandler implements AuthenticationSuccessHandler{

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
                                        Authentication authentication) throws IOException, ServletException {
        ObjectMapper objectMapper = JsonUtil.getObjectMapper();
        String authenticationDetails = objectMapper.writeValueAsString(authentication.getDetails());
        response.setContentType("application/json;charset=UTF-8");
        response.getWriter().write(authenticationDetails);
    }

}

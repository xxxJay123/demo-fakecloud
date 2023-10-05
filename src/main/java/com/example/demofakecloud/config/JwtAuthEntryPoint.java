package com.example.demofakecloud.config;

import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.authentication.www.BasicAuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
public class JwtAuthEntryPoint extends BasicAuthenticationEntryPoint {
    @Override
    public void commence(HttpServletRequest request,
            HttpServletResponse response, AuthenticationException authException)
            throws IOException, IOException {

        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        response.setContentType("application/json");
        response.getWriter().write(
                "{ \"message\": \"" + authException.getMessage() + "\" }");
    }

    @Override
    public void afterPropertiesSet() {
        setRealmName("JWT Authentication");
        super.afterPropertiesSet();
    }
    // @Override
    // public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException)
    // throws IOException, ServletException {
    // response.sendError(HttpServletResponse.SC_UNAUTHORIZED, authException.getMessage());
    // }
}

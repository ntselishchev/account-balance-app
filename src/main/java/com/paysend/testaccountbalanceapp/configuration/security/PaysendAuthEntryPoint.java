package com.paysend.testaccountbalanceapp.configuration.security;

import com.paysend.testaccountbalanceapp.configuration.security.builder.ResponseBuilder;
import lombok.AllArgsConstructor;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Component
@AllArgsConstructor
public class PaysendAuthEntryPoint implements AuthenticationEntryPoint {
    private final ResponseBuilder unauthorizedResponseBuilder;
    private final ResponseBuilder userNotFoundResponseBuilder;
    private final ResponseBuilder technicalExceptionResponseBuilder;

    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException) {
        if (authException.getClass().isAssignableFrom(UsernameNotFoundException.class)) {
            userNotFoundResponseBuilder.build(request, response, authException.getMessage());
        } else if (authException.getClass().isAssignableFrom(BadCredentialsException.class)) {
            unauthorizedResponseBuilder.build(request, response, authException.getMessage());
        } else {
            technicalExceptionResponseBuilder.build(request, response, authException.getMessage());
        }
    }
}

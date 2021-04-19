package com.paysend.testaccountbalanceapp.configuration.security;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.paysend.testaccountbalanceapp.configuration.security.wrapper.CachedHttpServletRequestWrapper;
import com.paysend.testaccountbalanceapp.exception.TechnicalException;
import com.paysend.testaccountbalanceapp.model.security.CredentialsRequest;
import lombok.AllArgsConstructor;
import lombok.SneakyThrows;
import org.apache.commons.io.IOUtils;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Collections;

@AllArgsConstructor
public class AuthBeforeFilter extends OncePerRequestFilter {
    private final ObjectMapper xmlMapper;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest requestWrapper = new CachedHttpServletRequestWrapper(request);
        SecurityContextHolder.getContext().setAuthentication(getUserInfo(requestWrapper));
        filterChain.doFilter(requestWrapper, response);
    }

    @SneakyThrows
    private Authentication getUserInfo(HttpServletRequest request) {
        if ("POST".equalsIgnoreCase(request.getMethod())) {
            String requestBody = IOUtils.toString(request.getReader());
            CredentialsRequest credentialsRequest = xmlMapper.readValue(requestBody, CredentialsRequest.class);
            if (!StringUtils.hasText(credentialsRequest.getLogin()) || credentialsRequest.getPassword() == null) {
                throw new BadCredentialsException("Login and password required");
            }
            if (credentialsRequest.isSignUpRequest()) {
                UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken(credentialsRequest.getLogin(), credentialsRequest.getPassword(),
                        Collections.singletonList(new SimpleGrantedAuthority("SIGN_UP")));
                token.setAuthenticated(false);
                return token;
            }
            return new UsernamePasswordAuthenticationToken(credentialsRequest.getLogin(), credentialsRequest.getPassword());
        }
        throw new TechnicalException("POST method required");
    }
}

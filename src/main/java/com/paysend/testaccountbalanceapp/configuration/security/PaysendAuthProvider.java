package com.paysend.testaccountbalanceapp.configuration.security;

import lombok.AllArgsConstructor;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Collections;

@AllArgsConstructor
public class PaysendAuthProvider implements AuthenticationProvider {
    private final UserDetailsService myUserDetailsService;
    private final PasswordEncoder passwordEncoder;

    @Override
    public Authentication authenticate(Authentication authentication) {
        String username = (String) authentication.getPrincipal();
        String password = new String((byte[]) authentication.getCredentials());
        if (authentication.getAuthorities().contains(new SimpleGrantedAuthority("SIGN_UP"))) {
            return new UsernamePasswordAuthenticationToken(username, passwordEncoder.encode(password), Collections.emptyList());
        }
        UserDetails userDetails = myUserDetailsService.loadUserByUsername(username);
        if (passwordEncoder.matches(password, userDetails.getPassword())) {
            return new UsernamePasswordAuthenticationToken(userDetails.getUsername(), userDetails.getPassword(), Collections.emptyList());
        }
        throw new BadCredentialsException("Wrong password provided");
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return UsernamePasswordAuthenticationToken.class.isAssignableFrom(authentication);
    }
}


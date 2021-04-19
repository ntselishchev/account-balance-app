package com.paysend.testaccountbalanceapp.service.security;

import com.paysend.testaccountbalanceapp.repository.UserJdbcRepository;
import lombok.AllArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

@Service
@AllArgsConstructor
public class PaysendUserDetailsService implements UserDetailsService {
    private final UserJdbcRepository userRepository;
    private static final String USER_NOT_FOUND_MESSAGE_TEMPLATE = "User '%s' not found";

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        if (StringUtils.hasText(username)) {
            return userRepository.findByUsername(username)
                    .orElseThrow(() -> new UsernameNotFoundException(String.format(USER_NOT_FOUND_MESSAGE_TEMPLATE, username)));
        }
        throw new UsernameNotFoundException(String.format(USER_NOT_FOUND_MESSAGE_TEMPLATE, username));
    }
}

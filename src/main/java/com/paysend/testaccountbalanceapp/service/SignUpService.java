package com.paysend.testaccountbalanceapp.service;

import com.paysend.testaccountbalanceapp.exception.UserAlreadyExistsException;
import com.paysend.testaccountbalanceapp.model.ServiceRequest;
import com.paysend.testaccountbalanceapp.model.ServiceResponse;
import com.paysend.testaccountbalanceapp.model.domain.PaysendUserDetails;
import com.paysend.testaccountbalanceapp.model.domain.UserAccount;
import com.paysend.testaccountbalanceapp.repository.UserAccountJdbcRepository;
import com.paysend.testaccountbalanceapp.repository.UserJdbcRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class SignUpService implements PaysendOperationService {
    private final UserJdbcRepository userRepository;
    private final UserAccountJdbcRepository userAccountJdbcRepository;

    @Override
    public ServiceRequest.RequestType getRequestType() {
        return ServiceRequest.RequestType.CREATE_AGT;
    }

    @Override
    @Transactional
    public ServiceResponse process(ServiceRequest request) {
        try {
            PaysendUserDetails user = userRepository.save(new PaysendUserDetails()
                    .setUsername((String) SecurityContextHolder.getContext().getAuthentication().getPrincipal())
                    .setPassword((String) SecurityContextHolder.getContext().getAuthentication().getCredentials())
            );
            userAccountJdbcRepository.save(new UserAccount().setUserId(user.getId()));
            return new ServiceResponse()
                    .setResultCode(ServiceResponse.ResultCode.OK);
        } catch (DuplicateKeyException e) {
            throw new UserAlreadyExistsException((String) SecurityContextHolder.getContext().getAuthentication().getPrincipal());
        }
    }
}

package com.paysend.testaccountbalanceapp.service;

import com.paysend.testaccountbalanceapp.model.ServiceRequest;
import com.paysend.testaccountbalanceapp.model.ServiceResponse;
import com.paysend.testaccountbalanceapp.repository.UserJdbcRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.Locale;

@Service
@RequiredArgsConstructor
public class GetBalanceService implements PaysendOperationService {
    private final UserJdbcRepository userRepository;
    private static final String BALANCE_FIELD_NAME = "balance";

    @Override
    public ServiceRequest.RequestType getRequestType() {
        return ServiceRequest.RequestType.GET_BALANCE;
    }

    @Override
    public ServiceResponse process(ServiceRequest request) {
        Double balance = userRepository.getBalance((String) SecurityContextHolder.getContext().getAuthentication().getPrincipal());
        return new ServiceResponse()
                .setResultCode(ServiceResponse.ResultCode.OK)
                .setExtras(Collections.singletonList(
                        new ServiceResponse.Extra()
                                .setName(BALANCE_FIELD_NAME)
                                .setValue(String.format(Locale.US, "%.2f", balance))
                ));
    }
}

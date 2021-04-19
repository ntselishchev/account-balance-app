package com.paysend.testaccountbalanceapp.service;

import com.paysend.testaccountbalanceapp.model.ServiceRequest;
import com.paysend.testaccountbalanceapp.model.ServiceResponse;

public interface PaysendOperationService {
    ServiceRequest.RequestType getRequestType();
    ServiceResponse process(ServiceRequest request);
}

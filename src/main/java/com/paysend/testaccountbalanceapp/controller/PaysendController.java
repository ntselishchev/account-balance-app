package com.paysend.testaccountbalanceapp.controller;

import com.paysend.testaccountbalanceapp.exception.TechnicalException;
import com.paysend.testaccountbalanceapp.model.ServiceRequest;
import com.paysend.testaccountbalanceapp.model.ServiceResponse;
import com.paysend.testaccountbalanceapp.service.PaysendOperationService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class PaysendController {
    private final List<PaysendOperationService> services;

    @PostMapping(consumes = "application/xml;charset=UTF-8")
    public ServiceResponse process(ServiceRequest request) {
        return findByRequestType(request).process(request);
    }

    private PaysendOperationService findByRequestType(ServiceRequest request) {
        return services.stream()
                .filter(x -> x.getRequestType() == request.getRequestType())
                .findFirst()
                .orElseThrow(() -> new TechnicalException("Service not found"));
    }
}

package com.paysend.testaccountbalanceapp.configuration.security.builder;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.paysend.testaccountbalanceapp.model.ServiceResponse;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Component
@RequiredArgsConstructor
public class UnauthorizedResponseBuilder implements ResponseBuilder {
    private final ObjectMapper xmlObjectMapper;

    @SneakyThrows
    @Override
    public void build(HttpServletRequest request, HttpServletResponse response, String message) {
        response.setStatus(HttpStatus.OK.value());
        response.setContentType(MediaType.APPLICATION_XML_VALUE);
        response.getWriter().write(
                xmlObjectMapper.writeValueAsString(new ServiceResponse().setResultCode(ServiceResponse.ResultCode.INVALID_PASSWORD))
        );
    }
}

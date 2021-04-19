package com.paysend.testaccountbalanceapp.controller;

import com.paysend.testaccountbalanceapp.exception.TechnicalException;
import com.paysend.testaccountbalanceapp.exception.UserAlreadyExistsException;
import com.paysend.testaccountbalanceapp.model.ServiceResponse;
import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Log4j2
@RestControllerAdvice
@AllArgsConstructor
public class ExceptionController implements ErrorController {
    @Override
    public String getErrorPath() {
        return "/error";
    }

    @ExceptionHandler({TechnicalException.class, Exception.class})
    public ResponseEntity<ServiceResponse> handleTechnicalException(Exception e) {
        log.error(e);
        ServiceResponse response = new ServiceResponse()
                .setResultCode(ServiceResponse.ResultCode.TECHNICAL_ERROR);
        return toResponseEntity(response);
    }

    @ExceptionHandler({UserAlreadyExistsException.class})
    public ResponseEntity<ServiceResponse> handleUserAlreadyExistsException(Exception e) {
        log.error(e);
        ServiceResponse response = new ServiceResponse()
                .setResultCode(ServiceResponse.ResultCode.USER_ALREADY_EXISTS);
        return toResponseEntity(response);
    }

    private ResponseEntity<ServiceResponse> toResponseEntity(ServiceResponse serviceResponse) {
        return ResponseEntity
                .status(HttpStatus.OK)
                .contentType(MediaType.APPLICATION_XML)
                .body(serviceResponse);
    }
}

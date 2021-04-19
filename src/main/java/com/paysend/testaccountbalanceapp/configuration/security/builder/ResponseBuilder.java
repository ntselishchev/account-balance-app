package com.paysend.testaccountbalanceapp.configuration.security.builder;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public interface ResponseBuilder {
    void build(HttpServletRequest request, HttpServletResponse response, String message);

}

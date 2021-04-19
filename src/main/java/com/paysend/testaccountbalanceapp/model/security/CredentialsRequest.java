package com.paysend.testaccountbalanceapp.model.security;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlElementWrapper;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlText;
import com.paysend.testaccountbalanceapp.model.ServiceRequest;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import lombok.SneakyThrows;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlValue;
import java.util.List;

@Getter
@Setter
@JsonIgnoreProperties(ignoreUnknown = true)
@JacksonXmlRootElement(localName = "request")
@XmlAccessorType(XmlAccessType.FIELD)
public class CredentialsRequest {
    @JacksonXmlProperty(localName = "request-type")
    private ServiceRequest.RequestType requestType;
    @JacksonXmlProperty(localName = "extra")
    @JacksonXmlElementWrapper(localName = "extra", useWrapping = false)
    private List<Extra> extras;

    @Data
    @XmlAccessorType(XmlAccessType.FIELD)
    public static class Extra {
        @XmlAttribute(name = "name")
        @JacksonXmlProperty(localName = "name", isAttribute = true)
        private String name;

        @XmlValue
        @JacksonXmlText
        private String value;
    }

    public String getLogin() {
        return extras.stream()
                .filter(x -> x.getName().equalsIgnoreCase("login"))
                .findFirst()
                .orElse(new Extra())
                .getValue();
    }

    @SneakyThrows
    public byte[] getPassword() {
        Extra extra = extras.stream()
                .filter(x -> x.getName().equalsIgnoreCase("password"))
                .findFirst()
                .orElse(new Extra());
        return extra.getValue() == null ? null : extra.getValue().getBytes();
    }

    public boolean isSignUpRequest() {
        return requestType == ServiceRequest.RequestType.CREATE_AGT;
    }
}

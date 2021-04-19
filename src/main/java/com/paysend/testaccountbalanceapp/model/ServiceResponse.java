package com.paysend.testaccountbalanceapp.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlElementWrapper;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlText;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

import javax.xml.bind.annotation.*;
import java.util.List;

@Getter
@Setter
@Accessors(chain = true)
@JacksonXmlRootElement(localName = "response")
@XmlAccessorType(XmlAccessType.FIELD)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ServiceResponse {
    @JacksonXmlProperty(localName = "result-code")
    private ResultCode resultCode;

    @JacksonXmlProperty(localName = "extra")
    @JacksonXmlElementWrapper(localName = "extra", useWrapping = false)
    private List<Extra> extras;

    @Getter
    @Setter
    @XmlAccessorType(XmlAccessType.FIELD)
    public static class Extra {
        @XmlAttribute(name = "name")
        @JacksonXmlProperty(localName = "name", isAttribute = true)
        private String name;

        @XmlValue
        @JacksonXmlText
        private String value;
    }

    @XmlEnum
    public enum ResultCode {
        @XmlEnumValue("0")
        OK,
        @XmlEnumValue("1")
        USER_ALREADY_EXISTS,
        @XmlEnumValue("2")
        TECHNICAL_ERROR,
        @XmlEnumValue("3")
        USER_NOT_FOUND,
        @XmlEnumValue("4")
        INVALID_PASSWORD
    }
}

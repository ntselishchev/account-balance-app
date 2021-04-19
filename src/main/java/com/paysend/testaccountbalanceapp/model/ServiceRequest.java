package com.paysend.testaccountbalanceapp.model;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlElementWrapper;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlText;
import lombok.Getter;
import lombok.Setter;

import javax.xml.bind.annotation.*;
import java.util.List;

@Getter
@Setter
@JacksonXmlRootElement(localName = "request")
@XmlAccessorType(XmlAccessType.FIELD)
public class ServiceRequest {
    @JacksonXmlProperty(localName = "request-type")
    private RequestType requestType;
    @JacksonXmlProperty(localName = "extra")
    @JacksonXmlElementWrapper(localName = "extra", useWrapping = false)
    private List<Extra> extra;

    @XmlEnum
    public enum RequestType {
        @XmlEnumValue("CREATE-AGT")
        CREATE_AGT,
        @XmlEnumValue("GET-BALANCE")
        GET_BALANCE
    }

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
}

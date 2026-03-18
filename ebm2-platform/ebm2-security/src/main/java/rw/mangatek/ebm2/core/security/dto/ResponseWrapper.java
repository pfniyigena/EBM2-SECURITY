package rw.mangatek.ebm2.core.security.dto;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;

import lombok.Getter;
import lombok.Setter;
@Getter
@Setter
@JacksonXmlRootElement(localName = "response")
public class ResponseWrapper {
    private Header header;
    private Row row;
}
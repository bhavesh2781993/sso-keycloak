package com.ezbytz.keycloak.models.requests;

import lombok.Getter;
import lombok.Setter;

import java.util.HashMap;
import java.util.Map;

import com.fasterxml.jackson.annotation.JsonInclude;

@Getter
@Setter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ProtocolMapperRequest {

    private String name;

    private String protocol;

    private String protocolMapper;

    private Map<String, String> config = new HashMap<>();

}

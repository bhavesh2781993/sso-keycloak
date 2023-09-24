package com.ezbytz.keycloak.models.requests;

import lombok.Getter;
import lombok.Setter;

import java.util.List;
import java.util.Map;

import com.fasterxml.jackson.annotation.JsonInclude;

@Getter
@Setter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ClientScopeRegistrationRequest {

    private String name;

    private String description;

    private String protocol;

    private Map<String, String> attributes;

    private List<ProtocolMapperRequest> protocolMappers;

}

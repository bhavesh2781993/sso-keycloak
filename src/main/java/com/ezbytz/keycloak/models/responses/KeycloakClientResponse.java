package com.ezbytz.keycloak.models.responses;

import lombok.Getter;
import lombok.Setter;

import com.fasterxml.jackson.annotation.JsonProperty;

@Getter
@Setter
public class KeycloakClientResponse {

    private String id;

    private String clientId;

    @JsonProperty("name")
    private String clientName;

}

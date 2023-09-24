package com.ezbytz.keycloak.models.responses;

import lombok.Getter;
import lombok.Setter;

import com.fasterxml.jackson.annotation.JsonProperty;

@Getter
@Setter
public class KeycloakUserResponse {

    @JsonProperty("id")
    private String userId;
}

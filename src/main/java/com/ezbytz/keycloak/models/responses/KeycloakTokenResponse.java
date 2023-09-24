package com.ezbytz.keycloak.models.responses;

import lombok.Getter;
import lombok.Setter;

import com.fasterxml.jackson.annotation.JsonProperty;

@Setter
@Getter
public class KeycloakTokenResponse {

    @JsonProperty("access_token")
    private String accessToken;

}

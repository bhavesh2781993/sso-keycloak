package com.ezbytz.keycloak.models.responses;

import lombok.Getter;
import lombok.Setter;
import org.springframework.http.HttpStatus;

import com.fasterxml.jackson.annotation.JsonProperty;

@Getter
@Setter
public class KeycloakErrorResponse {

    private HttpStatus statusCode;

    private String error;

    private String errorMessage;

    @JsonProperty("error_description")
    private String errorDescription;

}

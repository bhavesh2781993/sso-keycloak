package com.ezbytz.keycloak.models.responses;

import lombok.Getter;
import lombok.Setter;

import com.fasterxml.jackson.annotation.JsonProperty;

@Getter
@Setter
public class KeycloakRoleResponse {

    @JsonProperty("id")
    private String roleId;

    @JsonProperty("name")
    private String roleName;
}

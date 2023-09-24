package com.ezbytz.keycloak.models.requests;

import lombok.Getter;
import lombok.Setter;

import java.util.Set;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.Valid;

@Getter
@Setter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ClientRoleRegistrationRequest {

    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private String clientName;

    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private String realmName;

    @Valid
    private Set<ClientRoleRequest> roles;

}

package com.ezbytz.keycloak.models.requests;

import static com.fasterxml.jackson.annotation.JsonProperty.Access.READ_ONLY;

import com.ezbytz.keycloak.configs.validators.ValidateEnumSet;
import com.ezbytz.keycloak.models.enums.Role;

import lombok.Getter;
import lombok.Setter;

import java.util.Set;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

@Getter
@Setter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class UserRoleRegistrationRequest {

    @JsonProperty(access = READ_ONLY)
    private String userEmail;

    private String clientName;

    @JsonProperty(access = READ_ONLY)
    private String realmName;

    @ValidateEnumSet(type = Role.class)
    private Set<String> roles;

}

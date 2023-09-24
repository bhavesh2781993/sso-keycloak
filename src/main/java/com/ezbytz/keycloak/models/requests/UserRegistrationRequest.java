package com.ezbytz.keycloak.models.requests;

import com.ezbytz.keycloak.configs.validators.ValidateEnumSet;
import com.ezbytz.keycloak.models.enums.Role;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import java.util.Set;

import com.fasterxml.jackson.annotation.JsonInclude;

@Getter
@Setter
@RequiredArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class UserRegistrationRequest {

    private String username;

    private String email;

    private String firstName;

    private String lastName;

    private Boolean enabled;

    @ValidateEnumSet(type = Role.class, isNullable = true)
    private Set<String> roles;

    private String realmName;

}

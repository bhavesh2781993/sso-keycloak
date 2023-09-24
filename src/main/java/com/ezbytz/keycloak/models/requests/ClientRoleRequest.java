package com.ezbytz.keycloak.models.requests;

import com.ezbytz.keycloak.configs.validators.ValidateEnum;
import com.ezbytz.keycloak.models.enums.Role;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ClientRoleRequest {

    @ValidateEnum(type = Role.class)
    private String roleName;

    private String description;

    private Boolean clientRole;

}

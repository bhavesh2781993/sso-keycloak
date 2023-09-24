package com.ezbytz.keycloak.models.requests;

import static com.ezbytz.keycloak.constants.ErrorMessage.ERR_MSG_FIELD_CAN_NOT_BE_BLANK;
import static com.fasterxml.jackson.annotation.JsonProperty.Access.READ_ONLY;

import lombok.Getter;
import lombok.Setter;

import java.util.Set;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;

@Getter
@Setter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ClientRegistrationRequest {

    private String clientId;

    @NotBlank(message = ERR_MSG_FIELD_CAN_NOT_BE_BLANK)
    private String clientName;

    private String description;

    private String baseUrl;

    private Boolean enabled;

    private Boolean standardFlowEnabled;

    @Valid
    private Set<ClientRoleRequest> roles;

    @JsonProperty(access = READ_ONLY)
    private String realmName;

}

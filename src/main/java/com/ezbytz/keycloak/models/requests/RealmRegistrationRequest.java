package com.ezbytz.keycloak.models.requests;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;

@Getter
@Setter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class RealmRegistrationRequest {

    @NotBlank(message = "Realm cannot be blank")
    private String realm;

    private String displayName;

    private Boolean enabled;

    private Boolean resetPasswordAllowed;

    private Boolean loginWithEmailAllowed;

    @Valid
    private ClientRegistrationRequest client;

    @Valid
    private List<UserRegistrationRequest> users;

    private List<ClientScopeRegistrationRequest> clientScopes;

    private List<String> defaultClientScopes;

}

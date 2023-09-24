package com.ezbytz.keycloak.controllers;

import com.ezbytz.keycloak.models.requests.ClientRegistrationRequest;
import com.ezbytz.keycloak.models.requests.ClientRoleRegistrationRequest;
import com.ezbytz.keycloak.services.KeycloakClientService;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import jakarta.validation.Valid;

@RestController
@RequiredArgsConstructor
@RequestMapping("/keycloak/{realmName}/clients")
public class KeycloakClientController {

    private final KeycloakClientService keycloakClientService;

    @PostMapping()
    public ResponseEntity<Void> registerClient(final @PathVariable("realmName") String realmName,
                                               final @Valid @RequestBody ClientRegistrationRequest clientRegistrationRequest) {
        clientRegistrationRequest.setRealmName(realmName);
        keycloakClientService.registerClient(clientRegistrationRequest);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @PostMapping("/{clientName}/roles")
    public ResponseEntity<Void> registerClientRoles(final @PathVariable("realmName") String realmName,
                                                    final @PathVariable("clientName") String clientName,
                                                    final @Valid @RequestBody ClientRoleRegistrationRequest clientRoleRegistrationRequest) {
        clientRoleRegistrationRequest.setRealmName(realmName);
        clientRoleRegistrationRequest.setClientName(clientName);
        keycloakClientService.registerClientRole(clientRoleRegistrationRequest);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

}

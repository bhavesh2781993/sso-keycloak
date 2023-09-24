package com.ezbytz.keycloak.controllers;

import com.ezbytz.keycloak.models.requests.UserRegistrationRequest;
import com.ezbytz.keycloak.models.requests.UserRoleRegistrationRequest;
import com.ezbytz.keycloak.services.KeycloakUserService;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import jakarta.validation.Valid;

@RestController
@RequiredArgsConstructor
@RequestMapping("/keycloak/realms/{realmName}")
public class KeycloakUserController {

    private final KeycloakUserService keycloakUserService;

    @PostMapping("/users")
    public ResponseEntity<Void> registerUser(final @PathVariable("realmName") String realmName,
                                             final @Valid @RequestBody UserRegistrationRequest userRegistrationRequest) {
        userRegistrationRequest.setRealmName(realmName);
        keycloakUserService.registerUser(userRegistrationRequest);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @PutMapping("/clients/{clientName}/users/{userEmail}/roles")
    public ResponseEntity<Void> addClientRolesToUser(final @PathVariable("realmName") String realmName,
                                                     final @PathVariable("clientName") String clientName,
                                                     final @PathVariable("userEmail") String userEmail,
                                                     final @Valid @RequestBody UserRoleRegistrationRequest userRoleRegistrationRequest) {
        userRoleRegistrationRequest.setRealmName(realmName);
        userRoleRegistrationRequest.setClientName(clientName);
        userRoleRegistrationRequest.setUserEmail(userEmail);
        keycloakUserService.addClientRolesToUser(userRoleRegistrationRequest);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

}

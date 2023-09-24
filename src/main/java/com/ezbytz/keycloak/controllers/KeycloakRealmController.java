package com.ezbytz.keycloak.controllers;

import com.ezbytz.keycloak.mappers.RealmMapper;
import com.ezbytz.keycloak.models.requests.RealmRegistrationRequest;
import com.ezbytz.keycloak.services.integration.KeycloakRealmApiService;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import jakarta.validation.Valid;
import org.keycloak.representations.idm.RealmRepresentation;

@RestController
@RequiredArgsConstructor
@RequestMapping("/keycloak/realms")
public class KeycloakRealmController {

    private final KeycloakRealmApiService keycloakRealmApiService;

    private final RealmMapper realmMapper;

    @PostMapping
    public ResponseEntity<Void> createRealm(final @Valid @RequestBody RealmRegistrationRequest realmRegistrationRequest) {
        final RealmRepresentation representation = realmMapper.toCustomRealmRepresentation(realmRegistrationRequest);
        keycloakRealmApiService.createRealm(representation);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }
}

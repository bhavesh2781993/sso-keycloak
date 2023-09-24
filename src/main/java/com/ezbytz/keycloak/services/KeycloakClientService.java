package com.ezbytz.keycloak.services;

import static com.ezbytz.keycloak.constants.ErrorMessage.ERR_MSG_KEYCLOAK_CLIENT_NOT_FOUND;

import com.ezbytz.keycloak.exceptions.DataNotFoundException;
import com.ezbytz.keycloak.models.requests.ClientRegistrationRequest;
import com.ezbytz.keycloak.models.requests.ClientRoleRegistrationRequest;
import com.ezbytz.keycloak.models.responses.KeycloakClientResponse;
import com.ezbytz.keycloak.services.integration.KeycloakClientApiService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

import org.keycloak.representations.idm.ClientRepresentation;
import org.keycloak.representations.idm.RoleRepresentation;

@RequiredArgsConstructor
@Service
@Slf4j
public class KeycloakClientService {

    private final KeycloakClientApiService keycloakClientApiService;

    public void registerClient(final ClientRegistrationRequest clientRegistrationRequest) {
        final ClientRepresentation clientRepresentation = new ClientRepresentation();
        clientRepresentation.setName(clientRegistrationRequest.getClientName());
        clientRepresentation.setClientId(clientRegistrationRequest.getClientName());
        clientRepresentation.setDescription(clientRegistrationRequest.getDescription());
        clientRepresentation.setBaseUrl(clientRegistrationRequest.getBaseUrl());
        clientRepresentation.setEnabled(clientRegistrationRequest.getEnabled());
        clientRepresentation.setStandardFlowEnabled(clientRegistrationRequest.getStandardFlowEnabled());

        keycloakClientApiService.registerClient(clientRepresentation, clientRegistrationRequest.getRealmName());
    }

    public void registerClientRole(final ClientRoleRegistrationRequest roleRegistrationRequest) {
        final List<KeycloakClientResponse> clientResponses =
            keycloakClientApiService.getClient(roleRegistrationRequest.getClientName(), roleRegistrationRequest.getRealmName());
        final KeycloakClientResponse clientResponse = clientResponses.stream().findAny()
            .orElseThrow(() -> new DataNotFoundException(ERR_MSG_KEYCLOAK_CLIENT_NOT_FOUND));

        roleRegistrationRequest.getRoles().stream()
            .map(clientRole -> {
                final RoleRepresentation roleRepresentation = new RoleRepresentation();
                roleRepresentation.setName(clientRole.getRoleName());
                roleRepresentation.setDescription(clientRole.getDescription());
                roleRepresentation.setClientRole(Boolean.TRUE);
                return roleRepresentation;
            })
            .forEach(roleRepresentation -> keycloakClientApiService
                .registerClientRole(roleRepresentation, clientResponse.getId(), roleRegistrationRequest.getRealmName()));
    }

}

package com.ezbytz.keycloak.services;

import static com.ezbytz.keycloak.constants.ErrorMessage.ERR_MSG_KEYCLOAK_CLIENT_NOT_FOUND;
import static com.ezbytz.keycloak.constants.ErrorMessage.ERR_MSG_KEYCLOAK_USER_NOT_FOUND;

import com.ezbytz.keycloak.configs.utils.UUIDGenerator;
import com.ezbytz.keycloak.exceptions.DataNotFoundException;
import com.ezbytz.keycloak.models.requests.UserRegistrationRequest;
import com.ezbytz.keycloak.models.requests.UserRoleRegistrationRequest;
import com.ezbytz.keycloak.models.responses.KeycloakClientResponse;
import com.ezbytz.keycloak.models.responses.KeycloakRoleResponse;
import com.ezbytz.keycloak.models.responses.KeycloakUserResponse;
import com.ezbytz.keycloak.services.integration.KeycloakClientApiService;
import com.ezbytz.keycloak.services.integration.KeycloakUserApiService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

import org.keycloak.representations.idm.CredentialRepresentation;
import org.keycloak.representations.idm.UserRepresentation;

@RequiredArgsConstructor
@Service
@Slf4j
public class KeycloakUserService {

    private final KeycloakUserApiService keycloakUserApiService;
    private final KeycloakClientApiService keycloakClientApiService;

    public void registerUser(final UserRegistrationRequest userRegistrationRequest) {
        final UserRepresentation userRepresentation = new UserRepresentation();

        userRepresentation.setUsername(userRegistrationRequest.getUsername());
        userRepresentation.setEmail(userRegistrationRequest.getEmail());
        userRepresentation.setFirstName(userRegistrationRequest.getFirstName());
        userRepresentation.setLastName(userRegistrationRequest.getLastName());
        userRepresentation.setEnabled(true);

        final CredentialRepresentation credentialRepresentation = new CredentialRepresentation();
        credentialRepresentation.setType(CredentialRepresentation.PASSWORD);
        credentialRepresentation.setValue(UUIDGenerator.generateUUID());
        credentialRepresentation.setTemporary(Boolean.FALSE);
        userRepresentation.setCredentials(List.of(credentialRepresentation));

        keycloakUserApiService.registerUser(userRepresentation, userRegistrationRequest.getRealmName());
    }

    public void addClientRolesToUser(final UserRoleRegistrationRequest userRoleRegistrationRequest) {

        final List<KeycloakClientResponse> clientResponses =
            keycloakClientApiService.getClient(userRoleRegistrationRequest.getClientName(), userRoleRegistrationRequest.getRealmName());
        final KeycloakClientResponse clientResponse =
            clientResponses.stream().findAny().orElseThrow(() -> new DataNotFoundException(ERR_MSG_KEYCLOAK_CLIENT_NOT_FOUND));
        final List<KeycloakUserResponse> keycloakUserResponses =
            keycloakUserApiService.getUser(userRoleRegistrationRequest.getUserEmail(), userRoleRegistrationRequest.getRealmName());

        final String keycloakUserId = keycloakUserResponses.stream().map(KeycloakUserResponse::getUserId)
            .findAny().orElseThrow(()
                -> new DataNotFoundException(String.format(ERR_MSG_KEYCLOAK_USER_NOT_FOUND, userRoleRegistrationRequest.getUserEmail())));

        final List<KeycloakRoleResponse> clientRoles =
            keycloakClientApiService.getClientRoles(clientResponse.getId(), userRoleRegistrationRequest.getRealmName());
        final List<KeycloakRoleResponse> matchingRoles = clientRoles.stream()
            .filter(roleAndId -> userRoleRegistrationRequest.getRoles().contains(roleAndId.getRoleName()))
            .toList();
        keycloakUserApiService
            .addClientRolesToUser(matchingRoles, keycloakUserId, clientResponse.getId(), userRoleRegistrationRequest.getRealmName());
    }

}

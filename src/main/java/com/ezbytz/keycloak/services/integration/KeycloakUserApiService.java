package com.ezbytz.keycloak.services.integration;

import static com.ezbytz.keycloak.constants.KeycloakConstant.URI_KEYCLOAK_USERS;
import static com.ezbytz.keycloak.constants.KeycloakConstant.URI_KEYCLOAK_USER_CLIENT_ROLE_MAPPING;

import com.ezbytz.keycloak.exceptions.handlers.KeycloakExceptionHandler;
import com.ezbytz.keycloak.models.responses.KeycloakRoleResponse;
import com.ezbytz.keycloak.models.responses.KeycloakUserResponse;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClientResponseException;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.List;

import org.keycloak.representations.idm.UserRepresentation;

@Component
@Slf4j
@RequiredArgsConstructor
public class KeycloakUserApiService {

    private static final String EMAIL = "email";

    private final KeycloakClientApiService keycloakClientApiService;
    private final WebClient keycloakClient;

    public void registerUser(final UserRepresentation userRepresentation, final String realmName) {
        try {
            final String accessToken = keycloakClientApiService.getAdminAccessToken().getAccessToken();

            final String uri = UriComponentsBuilder.newInstance()
                .path(URI_KEYCLOAK_USERS)
                .build(realmName)
                .toString();
            keycloakClient.post()
                .uri(uri)
                .headers(header -> header.setBearerAuth(accessToken))
                .body(BodyInserters.fromValue(userRepresentation))
                .retrieve()
                .bodyToMono(Object.class)
                .block();
        } catch (final WebClientResponseException ex) {
            KeycloakExceptionHandler.handleKeycloakFailure(ex);
        }
    }

    public List<KeycloakUserResponse> getUser(final String email, final String realmName) {
        final String accessToken = keycloakClientApiService.getAdminAccessToken().getAccessToken();

        final String uri = UriComponentsBuilder.newInstance()
            .path(URI_KEYCLOAK_USERS)
            .queryParam(EMAIL, email)
            .build(realmName)
            .toString();
        return keycloakClient.get()
            .uri(uri)
            .headers(header -> header.setBearerAuth(accessToken))
            .retrieve()
            .bodyToFlux(KeycloakUserResponse.class)
            .collectList()
            .block();
    }

    public void addClientRolesToUser(final List<KeycloakRoleResponse> matchingRoles,
                                     final String userId,
                                     final String clientId,
                                     final String realmName) {
        final String accessToken = keycloakClientApiService.getAdminAccessToken().getAccessToken();

        final String uri = UriComponentsBuilder.newInstance()
            .path(URI_KEYCLOAK_USER_CLIENT_ROLE_MAPPING)
            .build(realmName, userId, clientId)
            .toString();
        keycloakClient.post()
            .uri(uri)
            .headers(header -> header.setBearerAuth(accessToken))
            .contentType(MediaType.APPLICATION_JSON)
            .body(BodyInserters.fromValue(matchingRoles))     //https://lists.jboss.org/pipermail/keycloak-user/2019-February/017199.html
            .retrieve()
            .toBodilessEntity()
            .block();
    }

}

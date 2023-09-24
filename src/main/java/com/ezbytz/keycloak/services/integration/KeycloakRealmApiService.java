package com.ezbytz.keycloak.services.integration;

import static com.ezbytz.keycloak.constants.KeycloakConstant.URI_KEYCLOAK_ADMIN_REALMS;

import com.ezbytz.keycloak.exceptions.handlers.KeycloakExceptionHandler;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClientResponseException;

import org.keycloak.representations.idm.RealmRepresentation;

@Service
@Slf4j
@RequiredArgsConstructor
public class KeycloakRealmApiService {

    private final WebClient keycloakClient;
    private final KeycloakClientApiService keycloakClientApiService;

    public void createRealm(final RealmRepresentation realmRepresentation) {
        final String accessToken = keycloakClientApiService.getAdminAccessToken().getAccessToken();
        try {
            keycloakClient.post()
                .uri(URI_KEYCLOAK_ADMIN_REALMS)
                .headers(header -> header.setBearerAuth(accessToken))
                .body(BodyInserters.fromValue(realmRepresentation))
                .retrieve()
                .toBodilessEntity()
                .block();
        } catch (final WebClientResponseException ex) {
            KeycloakExceptionHandler.handleKeycloakFailure(ex);
        }
    }

}

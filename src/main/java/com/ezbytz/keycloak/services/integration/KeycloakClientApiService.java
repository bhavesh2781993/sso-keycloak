package com.ezbytz.keycloak.services.integration;

import static com.ezbytz.keycloak.constants.KeycloakConstant.ADMIN_CLI;
import static com.ezbytz.keycloak.constants.KeycloakConstant.URI_KEYCLOAK_ADMIN_TOKEN;
import static com.ezbytz.keycloak.constants.KeycloakConstant.URI_KEYCLOAK_CLIENTS;
import static com.ezbytz.keycloak.constants.KeycloakConstant.URI_KEYCLOAK_CLIENT_ROLES;
import static org.keycloak.OAuth2Constants.CLIENT_CREDENTIALS;

import com.ezbytz.keycloak.configs.properties.KeycloakProperty;
import com.ezbytz.keycloak.exceptions.handlers.KeycloakExceptionHandler;
import com.ezbytz.keycloak.models.responses.KeycloakClientResponse;
import com.ezbytz.keycloak.models.responses.KeycloakRoleResponse;
import com.ezbytz.keycloak.models.responses.KeycloakTokenResponse;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClientResponseException;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.List;

import org.keycloak.OAuth2Constants;
import org.keycloak.representations.idm.ClientRepresentation;
import org.keycloak.representations.idm.RoleRepresentation;

@Component
@Slf4j
@RequiredArgsConstructor
public class KeycloakClientApiService {

    private static final String CLIENT_ID = "clientId";

    private final WebClient keycloakClient;
    private final KeycloakProperty keycloakProperty;

    public void registerClient(final ClientRepresentation clientRepresentation, final String realmName) {
        try {
            final String accessToken = getAdminAccessToken().getAccessToken();

            final String uri = UriComponentsBuilder.newInstance()
                .path(URI_KEYCLOAK_CLIENTS)
                .build(realmName)
                .toString();
            keycloakClient.post()
                .uri(uri)
                .headers(header -> header.setBearerAuth(accessToken))
                .body(BodyInserters.fromValue(clientRepresentation))
                .retrieve()
                .toBodilessEntity()
                .block();
        } catch (final WebClientResponseException ex) {
            KeycloakExceptionHandler.handleKeycloakFailure(ex);
        }
    }

    public void registerClientRole(final RoleRepresentation roleRepresentation, final String clientId, final String realmName) {
        try {
            final String accessToken = getAdminAccessToken().getAccessToken();

            final String uri = UriComponentsBuilder.newInstance()
                .path(URI_KEYCLOAK_CLIENT_ROLES)
                .build(realmName, clientId)
                .toString();
            keycloakClient.post()
                .uri(uri)
                .headers(header -> header.setBearerAuth(accessToken))
                .body(BodyInserters.fromValue(roleRepresentation))
                .retrieve()
                .toBodilessEntity()
                .block();
        } catch (final WebClientResponseException ex) {
            KeycloakExceptionHandler.handleKeycloakFailure(ex);
        }
    }

    public List<KeycloakClientResponse> getClient(final String clientName, final String realmName) {
        final String accessToken = getAdminAccessToken().getAccessToken();

        final String uri = UriComponentsBuilder.newInstance()
            .path(URI_KEYCLOAK_CLIENTS)
            .queryParam(CLIENT_ID, clientName)
            .build(realmName)
            .toString();
        return keycloakClient.get()
            .uri(uri)
            .headers(header -> header.setBearerAuth(accessToken))
            .retrieve()
            .bodyToFlux(KeycloakClientResponse.class)
            .collectList()
            .block();
    }

    public List<KeycloakRoleResponse> getClientRoles(final String clientId, final String realmName) {
        final String accessToken = getAdminAccessToken().getAccessToken();

        final String uri = UriComponentsBuilder.newInstance()
            .path(URI_KEYCLOAK_CLIENT_ROLES)
            .build(realmName, clientId)
            .toString();
        return keycloakClient.get()
            .uri(uri)
            .headers(header -> header.setBearerAuth(accessToken))
            .retrieve()
            .bodyToFlux(KeycloakRoleResponse.class)
            .collectList()
            .block();
    }

    protected KeycloakTokenResponse getAdminAccessToken() {
        final MultiValueMap<String, String> formData = new LinkedMultiValueMap<>();
        /*
         * Note: TO USE WITH GRANT_TYPE : password
         * formData.add("username", "admin_username");
         * formData.add("password", "admin_password");
         * formData.add("grant_type", "password");
         * formData.add("client_id", "admin-cli");
         */

        // TO USE WITH GRANT_TYPE: client_credentials
        formData.add(OAuth2Constants.GRANT_TYPE, CLIENT_CREDENTIALS);
        formData.add(OAuth2Constants.CLIENT_ID, ADMIN_CLI);
        formData.add(OAuth2Constants.CLIENT_SECRET, keycloakProperty.getAdminCli().getClientSecret());

        return keycloakClient.post()
            .uri(URI_KEYCLOAK_ADMIN_TOKEN)
            .contentType(MediaType.APPLICATION_FORM_URLENCODED)
            .body(BodyInserters.fromFormData(formData))
            .retrieve()
            .bodyToMono(KeycloakTokenResponse.class)
            .block();
    }

}

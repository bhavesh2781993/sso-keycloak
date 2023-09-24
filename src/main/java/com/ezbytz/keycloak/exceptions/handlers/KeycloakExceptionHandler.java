package com.ezbytz.keycloak.exceptions.handlers;

import static lombok.AccessLevel.PRIVATE;

import com.ezbytz.keycloak.exceptions.AuthenticationException;
import com.ezbytz.keycloak.exceptions.AuthorizationException;
import com.ezbytz.keycloak.exceptions.KeycloakConflictException;
import com.ezbytz.keycloak.models.responses.KeycloakErrorResponse;

import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.reactive.function.client.WebClientResponseException;

import java.util.Objects;

@Slf4j
@NoArgsConstructor(access = PRIVATE)
public class KeycloakExceptionHandler {

    public static void handleKeycloakFailure(final WebClientResponseException ex) {
        log.error("Unable To Process WebClient Request -> ", ex);
        final KeycloakErrorResponse errorResponse = ex.getResponseBodyAs(KeycloakErrorResponse.class);
        if (Objects.nonNull(errorResponse)) {
            if (HttpStatus.CONFLICT.equals(ex.getStatusCode())) {
                throw new KeycloakConflictException(errorResponse.getErrorMessage());
            }
            if (HttpStatus.UNAUTHORIZED.equals(ex.getStatusCode())) {
                throw new AuthenticationException(errorResponse.getError());
            }
            if (HttpStatus.FORBIDDEN.equals(ex.getStatusCode())) {
                throw new AuthorizationException();
            }
        }
    }

}

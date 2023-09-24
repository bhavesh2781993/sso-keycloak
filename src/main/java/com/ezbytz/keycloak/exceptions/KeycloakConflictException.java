package com.ezbytz.keycloak.exceptions;

public class KeycloakConflictException extends RuntimeException {

    public KeycloakConflictException(final String message) {
        super(message);
    }
}

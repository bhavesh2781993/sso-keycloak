package com.ezbytz.keycloak.exceptions;

import java.io.Serial;

public class AuthenticationException extends RuntimeException {

    @Serial
    private static final long serialVersionUID = -1L;

    public AuthenticationException(final String message) {
        super(message);
    }

}

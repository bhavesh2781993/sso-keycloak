package com.ezbytz.keycloak.exceptions;

import java.io.Serial;

public class DataValidationException extends RuntimeException {

    @Serial
    private static final long serialVersionUID = 6973926344792731860L;

    public DataValidationException(final String message) {
        super(message);
    }
}

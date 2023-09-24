package com.ezbytz.keycloak.constants;

import static lombok.AccessLevel.PRIVATE;

import lombok.NoArgsConstructor;

@NoArgsConstructor(access = PRIVATE)
public class ErrorMessage {
    public static final String ERR_MSG_FIELD_CAN_NOT_BE_BLANK = "Field cannot be blank";
    public static final String ERR_MSG_KEYCLOAK_USER_NOT_FOUND = "No User found with email: %s";
    public static final String ERR_MSG_KEYCLOAK_CLIENT_NOT_FOUND = "No Client data found";

}

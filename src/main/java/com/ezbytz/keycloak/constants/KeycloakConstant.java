package com.ezbytz.keycloak.constants;


import static lombok.AccessLevel.PRIVATE;

import lombok.NoArgsConstructor;

@NoArgsConstructor(access = PRIVATE)
public class KeycloakConstant {
    public static final String ADMIN_CLI = "admin-cli";
    public static final String URI_KEYCLOAK_ADMIN_TOKEN = "/realms/master/protocol/openid-connect/token";
    public static final String URI_KEYCLOAK_ADMIN_REALMS = "/admin/realms";
    public static final String URI_KEYCLOAK_ADMIN_REALM_BY_NAME = URI_KEYCLOAK_ADMIN_REALMS + "/{realm}";
    public static final String URI_KEYCLOAK_USERS = URI_KEYCLOAK_ADMIN_REALM_BY_NAME + "/users";
    public static final String URI_KEYCLOAK_USER_BY_ID = URI_KEYCLOAK_USERS + "/{userId}";
    public static final String URI_KEYCLOAK_USER_CLIENT_ROLE_MAPPING = URI_KEYCLOAK_USER_BY_ID + "/role-mappings/clients/{clientId}";
    public static final String URI_KEYCLOAK_CLIENTS = URI_KEYCLOAK_ADMIN_REALM_BY_NAME + "/clients";
    public static final String URI_KEYCLOAK_CLIENT_BY_ID = URI_KEYCLOAK_CLIENTS + "/{clientId}";
    public static final String URI_KEYCLOAK_CLIENT_ROLES = URI_KEYCLOAK_CLIENT_BY_ID + "/roles";

}

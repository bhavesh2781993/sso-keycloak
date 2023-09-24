package com.ezbytz.keycloak.configs.security;

import static lombok.AccessLevel.PRIVATE;

import com.ezbytz.keycloak.models.enums.Authority;
import com.ezbytz.keycloak.models.enums.Role;

import lombok.NoArgsConstructor;

import java.util.List;
import java.util.Map;

@NoArgsConstructor(access = PRIVATE)
public final class RoleAuthorityMapper {

    public static final Map<Role, List<Authority>> ROLE_AUTHORITIES_MAP;

    static {
        ROLE_AUTHORITIES_MAP = Map.of(
            Role.ROLE_ADMIN, List.of(Authority.READ, Authority.WRITE, Authority.DELETE),
            Role.ROLE_MANAGER, List.of(Authority.READ, Authority.WRITE),
            Role.ROLE_USER, List.of(Authority.READ)
        );
    }

}

package com.ezbytz.keycloak.configs.security;

import com.ezbytz.keycloak.models.enums.Authority;
import com.ezbytz.keycloak.models.enums.Role;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;

@Component
public class RoleAuthorityEvaluator {

    public boolean hasAuthority(final String... allowedAuthorities) {
        Assert.notEmpty(allowedAuthorities, "authorities can't be empty");
        final Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        final Collection<? extends GrantedAuthority> roles = authentication.getAuthorities();
        return roles.stream()
            .map(role -> Role.valueOf(role.getAuthority()))
            .anyMatch(role -> roleHasAuthority(role, allowedAuthorities));
    }

    private boolean roleHasAuthority(final Role userRole, final String... allowedAuthoritiesArr) {
        final List<Authority> userAuthorities = RoleAuthorityMapper.ROLE_AUTHORITIES_MAP.get(userRole);
        final List<Authority> allowedAuthorities = Arrays.stream(allowedAuthoritiesArr).map(Authority::valueOf).toList();
        return allowedAuthorities.stream().anyMatch(userAuthorities::contains);
    }

}

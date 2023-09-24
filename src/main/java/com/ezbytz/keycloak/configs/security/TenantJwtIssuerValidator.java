package com.ezbytz.keycloak.configs.security;

import com.ezbytz.keycloak.models.entities.Tenant;
import com.ezbytz.keycloak.services.TenantService;

import lombok.RequiredArgsConstructor;
import org.springframework.security.oauth2.core.OAuth2TokenValidator;
import org.springframework.security.oauth2.core.OAuth2TokenValidatorResult;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.jwt.JwtIssuerValidator;
import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;


@Component
@RequiredArgsConstructor
public class TenantJwtIssuerValidator implements OAuth2TokenValidator<Jwt> {

    private final TenantService tenantService;
    private final Map<String, JwtIssuerValidator> validators = new ConcurrentHashMap<>();

    @Override
    public OAuth2TokenValidatorResult validate(final Jwt token) {
        return this.validators.computeIfAbsent(toTenant(token), this::fromTenant)
            .validate(token);
    }

    private String toTenant(final Jwt jwt) {
        return jwt.getIssuer().toString();
    }

    private JwtIssuerValidator fromTenant(final String tenant) {
        return Optional.ofNullable(this.tenantService.findById(tenant))
            .map(Tenant::getIssuer)
            .map(JwtIssuerValidator::new)
            .orElseThrow(() -> new IllegalArgumentException("unknown tenant"));
    }
}

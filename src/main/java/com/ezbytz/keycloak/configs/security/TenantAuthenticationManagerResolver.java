package com.ezbytz.keycloak.configs.security;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationManagerResolver;
import org.springframework.security.oauth2.jwt.JwtDecoders;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationProvider;
import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import jakarta.servlet.http.HttpServletRequest;

/**
 * Note: Not to be Used.
 * This is a Custom AuthenticationManagerResolver used for multi-tenant application.
 * This is inefficient as it has to decode token twice throughout the single request.
 */
@Component
public class TenantAuthenticationManagerResolver implements AuthenticationManagerResolver<HttpServletRequest> {

    private static final Map<String, AuthenticationManager> AUTHENTICATION_MANAGERS = new ConcurrentHashMap<>();

    private final CustomJwtAuthenticationConverter customJwtAuthenticationConverter;

    public TenantAuthenticationManagerResolver() {
        this.customJwtAuthenticationConverter = new CustomJwtAuthenticationConverter();
    }

    @Override
    public AuthenticationManager resolve(final HttpServletRequest request) {
        // TODO: FIGURE A WAY TO GET TENANT DYNAMICALLY FROM REQUESTED TOKEN
        final var tenantId = "tenant_y";
        return AUTHENTICATION_MANAGERS.computeIfAbsent(tenantId, this::buildAuthenticationManager);
    }

    private AuthenticationManager buildAuthenticationManager(final String tenantId) {
        final var issuerUri = "http://localhost:8180/realms/" + tenantId;
        final var jwtAuthenticationProvider = new JwtAuthenticationProvider(JwtDecoders.fromIssuerLocation(issuerUri));
        jwtAuthenticationProvider.setJwtAuthenticationConverter(customJwtAuthenticationConverter);
        return jwtAuthenticationProvider::authenticate;
    }
}

package com.ezbytz.keycloak.configs.security;


import com.ezbytz.keycloak.models.entities.Tenant;
import com.ezbytz.keycloak.services.TenantService;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.net.URL;
import java.security.Key;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

import com.nimbusds.jose.JWSHeader;
import com.nimbusds.jose.KeySourceException;
import com.nimbusds.jose.proc.JWSAlgorithmFamilyJWSKeySelector;
import com.nimbusds.jose.proc.JWSKeySelector;
import com.nimbusds.jose.proc.SecurityContext;
import com.nimbusds.jwt.JWTClaimsSet;
import com.nimbusds.jwt.proc.JWTClaimsSetAwareJWSKeySelector;

@Component
@RequiredArgsConstructor
public class TenantJWSKeySelector implements JWTClaimsSetAwareJWSKeySelector<SecurityContext> {

    private final TenantService tenantService;
    private final Map<String, JWSKeySelector<SecurityContext>> selectors = new ConcurrentHashMap<>();

    @Override
    public List<? extends Key> selectKeys(final JWSHeader jwsHeader,
                                          final JWTClaimsSet jwtClaimsSet,
                                          final SecurityContext securityContext) throws KeySourceException {
        return this.selectors.computeIfAbsent(toTenant(jwtClaimsSet), this::fromTenant)
            .selectJWSKeys(jwsHeader, securityContext);
    }

    private String toTenant(final JWTClaimsSet claimSet) {
        return (String) claimSet.getClaim("iss");
    }

    private JWSKeySelector<SecurityContext> fromTenant(final String tenant) {
        return Optional.ofNullable(this.tenantService.findById(tenant))
            .map(Tenant::getJwksUri)
            .map(this::fromUri)
            .orElseThrow(() -> new IllegalArgumentException("unknown tenant"));
    }

    private JWSKeySelector<SecurityContext> fromUri(final String uri) {
        try {
            return JWSAlgorithmFamilyJWSKeySelector.fromJWKSetURL(new URL(uri));
        } catch (final Exception ex) {
            throw new IllegalArgumentException(ex);
        }
    }

}

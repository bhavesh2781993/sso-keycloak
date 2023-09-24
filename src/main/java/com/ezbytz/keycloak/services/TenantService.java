package com.ezbytz.keycloak.services;

import com.ezbytz.keycloak.exceptions.DataValidationException;
import com.ezbytz.keycloak.models.entities.Tenant;
import com.ezbytz.keycloak.repositories.TenantRepository;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class TenantService {

    private final TenantRepository tenantRepository;

    public Tenant findById(final String issuer) {
        return tenantRepository.findById(issuer)
            .orElseThrow(() -> new DataValidationException("Invalid Token Issuer"));
    }

}

package com.ezbytz.keycloak.repositories;

import com.ezbytz.keycloak.models.entities.Tenant;

import org.springframework.data.jpa.repository.JpaRepository;

public interface TenantRepository extends JpaRepository<Tenant, String> {

}

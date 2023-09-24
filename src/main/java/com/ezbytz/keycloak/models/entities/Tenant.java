package com.ezbytz.keycloak.models.entities;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.Type;

import java.util.Map;

import com.vladmihalcea.hibernate.type.json.JsonBinaryType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Getter
@Setter
@Entity
@Table(name = "keycloak_realms_lookup")
public class Tenant {
    @Id
    private String issuer;

    private String realm;

    @Type(JsonBinaryType.class)
    @Column(name = "realm_attribute", columnDefinition = "jsonb", nullable = false)
    private Map<String, Object> attribute;

    public String getJwksUri() {
        return attribute.get("jwksUri").toString();
    }

}

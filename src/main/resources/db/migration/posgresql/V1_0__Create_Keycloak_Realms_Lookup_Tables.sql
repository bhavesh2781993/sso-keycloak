create table keycloak_realms_lookup
(
    realm           varchar(255) not null,
    issuer          varchar(255) not null,
    realm_attribute jsonb        not null,
    primary key (issuer)
);
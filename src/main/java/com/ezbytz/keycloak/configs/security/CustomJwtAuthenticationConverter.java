package com.ezbytz.keycloak.configs.security;

import org.springframework.core.convert.converter.Converter;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;

import java.util.List;

public class CustomJwtAuthenticationConverter implements Converter<Jwt, JwtAuthenticationToken> {

    @Override
    public JwtAuthenticationToken convert(final Jwt source) {
        final List<String> authorities = (List<String>) source.getClaims().get("authorities");
        final List<SimpleGrantedAuthority> simpleGrantedAuthorities = authorities.stream()
            .map(authority -> "ROLE_" + authority)
            .map(SimpleGrantedAuthority::new)
            .toList();
        return new JwtAuthenticationToken(source, simpleGrantedAuthorities);
    }
}

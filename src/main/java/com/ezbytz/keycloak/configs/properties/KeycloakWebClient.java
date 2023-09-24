package com.ezbytz.keycloak.configs.properties;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.WebClient;

@RequiredArgsConstructor
@Configuration
public class KeycloakWebClient {

    private final KeycloakProperty keycloakProperty;

    @Bean
    @Qualifier("keycloakClient")
    public WebClient keycloakClient() {
        return WebClient.builder()
            .baseUrl(keycloakProperty.getAuthServerUrl())
            .build();
    }
}

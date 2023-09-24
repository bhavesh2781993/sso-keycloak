package com.ezbytz.keycloak.configs.properties;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Getter
@Setter
@Configuration
@ConfigurationProperties(prefix = "keycloak")
public class KeycloakProperty {

    private String authServerUrl;
    private Admin adminCli;

    @Getter
    @Setter
    public static class Admin {
        private String clientSecret;
    }

}

package com.ezbytz.keycloak.controllers;

import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("/test")
public class TestController {

    private static final String RESPONSE_STATUS = "SUCCESS";

    @GetMapping
    @PreAuthorize("hasAnyRole('USER')")
    public String get() {
        log.info("Inside get");
        return RESPONSE_STATUS;
    }

    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    public String create() {
        log.info("Inside create");
        return RESPONSE_STATUS;
    }

    @PutMapping
    @PreAuthorize("hasAnyRole('ADMIN', 'MANAGER')")
    public String update() {
        log.info("Inside update");
        return RESPONSE_STATUS;
    }

    @DeleteMapping
    @PreAuthorize("hasAnyRole('ADMIN')")
    public String delete() {
        log.info("Inside delete");
        return RESPONSE_STATUS;
    }

}

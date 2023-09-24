package com.ezbytz.keycloak.controllers;

import lombok.extern.slf4j.Slf4j;
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

    @GetMapping
    public String get() {
        log.info("Inside get");
        return "Success";
    }

    @PostMapping
    public String create() {
        log.info("Inside create");
        return "Success";
    }

    @PutMapping
    public String update() {
        log.info("Inside update");
        return "Success";
    }

    @DeleteMapping
    public String delete() {
        log.info("Inside delete");
        return "Success";
    }

}

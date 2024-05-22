package com.fiap.techchallenge.adapters.in.rest.controller;

import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "Healthcheck Controller")
@RestController
public class HealthCheckController {

    @GetMapping("/healthcheck")
    public ResponseEntity<String> test() {
        return ResponseEntity.ok("A API está funcionando!");
    }

}

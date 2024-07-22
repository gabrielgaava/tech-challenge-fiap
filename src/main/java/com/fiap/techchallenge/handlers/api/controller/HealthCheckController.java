package com.fiap.techchallenge.handlers.api.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "Healthcheck Controller")
@RestController
public class HealthCheckController {

    @Operation(summary = "Returns current API status")
    @GetMapping("/healthcheck")
    public ResponseEntity<String> test() {
        return ResponseEntity.ok("API is up and running");
    }

}

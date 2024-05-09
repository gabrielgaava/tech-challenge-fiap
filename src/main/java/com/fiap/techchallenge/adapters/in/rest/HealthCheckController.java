package com.fiap.techchallenge.adapters.in.rest;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HealthCheckController {

    @RequestMapping("/healthcheck")
    public ResponseEntity<String> test() {
        return ResponseEntity.ok("API is up and running");
    }

}

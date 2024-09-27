package com.fiap.techchallenge.handlers.rest.api;

import com.fiap.techchallenge.handlers.rest.dto.HealthCheckDTO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "Healthcheck API")
@RestController
public class HealthCheckAPI {

    @Operation(summary = "Returns current API status")
    @GetMapping("/healthcheck")
    public ResponseEntity<HealthCheckDTO> test() {
        HealthCheckDTO response = new HealthCheckDTO("OK", "API is up and running");
        return ResponseEntity.ok(response);
    }

}

package org.mm97.pagilab.controllers;

import org.mm97.pagilab.dto.ApiInfoDto;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HomeController {

    @GetMapping("/api")
    public ResponseEntity<ApiInfoDto> home() {
        return ResponseEntity.ok(
                new ApiInfoDto("Pagila API", "UP", "1.0.0")
        );
    }

    @GetMapping("/api/health")
    public ResponseEntity<ApiInfoDto> health() {
        return ResponseEntity.ok(
                new ApiInfoDto("Pagila APP", "UP", "1.0.0")
        );
    }
}
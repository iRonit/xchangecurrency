/*
 * Copyright (c) 2023 XChangeCurrency API.
 *
 */
package com.xchangecurrency.controllers;

import com.xchangecurrency.constants.UrlConst;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * REST Endpoints for Health Check (v1).
 *
 * @author Ronit Pradhan
 */
@RestController
@RequestMapping("/v1/health")
public class HealthCheckController {

    /**
     * PING endpoint to check for the service availability.
     *
     * @return HTTP status 200 OK
     */
    @GetMapping(UrlConst.PING)
    public ResponseEntity<Void> ping() {
        return ResponseEntity.ok().build();
    }
}

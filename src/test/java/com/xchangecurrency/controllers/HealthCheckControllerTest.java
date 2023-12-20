/*
 * Copyright (c) 2023 XChangeCurrency API.
 *
 */
package com.xchangecurrency.controllers;

import org.junit.jupiter.api.Test;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class HealthCheckControllerTest extends AbstractControllerTest {

    @Test
    void testPing() throws Exception {
        this.mockMvc.perform(get("/v1/health/ping")).andExpect(status().isOk());
    }
}

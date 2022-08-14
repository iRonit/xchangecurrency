/*
 * Copyright (c) 2022 XChangeCurrency API.
 *
 */
package com.xchangecurrency.controllers;

import com.xchangecurrency.dtos.CurrencyGet;
import com.xchangecurrency.dtos.ExchangeRateGet;
import com.xchangecurrency.errorhandling.ClientException;
import com.xchangecurrency.errorhandling.ServerException;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;

import java.time.Instant;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Date;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class CurrencyExchangeControllerTest extends AbstractControllerTest {

    @Test
    void testGetCurrencyExchangeRate_ValidInputs() throws Exception {
        // Given
        ExchangeRateGet expected = ExchangeRateGet.builder()
                .fromCurrency(CurrencyGet.builder().code("GBP").name("British Pound").build())
                .toCurrency(CurrencyGet.builder().code("INR").name("Indian Rupee").build())
                .lastUpdated(Date.from(Instant.now())).amount(100F).build();
        when(currencyExchangeService.getExchangeRate("gbp", "inr")).thenReturn(expected);

        // When - Then
        DateTimeFormatter ISO_8601_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSSxxx")
                .withZone(ZoneId.of("UTC"));
        this.mockMvc.perform(get("/v1/xchange/gbp/inr")).andExpect(status().isOk())
                .andExpect(jsonPath("$.fromCurrency.code").value(expected.getFromCurrency().getCode()))
                .andExpect(jsonPath("$.fromCurrency.name").value(expected.getFromCurrency().getName()))
                .andExpect(jsonPath("$.toCurrency.code").value(expected.getToCurrency().getCode()))
                .andExpect(jsonPath("$.toCurrency.name").value(expected.getToCurrency().getName()))
                .andExpect(jsonPath("$.lastUpdated")
                        .value(ISO_8601_FORMATTER.format(expected.getLastUpdated().toInstant())))
                .andExpect(jsonPath("$.amount").value(expected.getAmount()));
    }

    @Test
    void testGetCurrencyExchangeRate_InvalidCurrency_ClientException() throws Exception {
        // Given
        when(currencyExchangeService.getExchangeRate("invalid", "inr")).thenThrow(new ClientException("some message"));

        // When - Then
        this.mockMvc.perform(get("/v1/xchange/invalid/inr")).andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.url").value(TEST_URL_PREFIX + "/v1/xchange/invalid/inr"))
                .andExpect(jsonPath("$.status").value(HttpStatus.BAD_REQUEST.getReasonPhrase()))
                .andExpect(jsonPath("$.timestamp").isNotEmpty()).andExpect(jsonPath("$.message").value("some message"));
    }

    @Test
    void testGetCurrencyExchangeRate_InvalidStateExternalResource_ServerException() throws Exception {
        // Given
        when(currencyExchangeService.getExchangeRate("gbp", "inr")).thenThrow(new ServerException("some message"));

        // When - Then
        this.mockMvc.perform(get("/v1/xchange/gbp/inr")).andExpect(status().isInternalServerError())
                .andExpect(jsonPath("$.url").value(TEST_URL_PREFIX + "/v1/xchange/gbp/inr"))
                .andExpect(jsonPath("$.status").value(HttpStatus.INTERNAL_SERVER_ERROR.getReasonPhrase()))
                .andExpect(jsonPath("$.timestamp").isNotEmpty()).andExpect(jsonPath("$.message").value("some message"));
    }
}

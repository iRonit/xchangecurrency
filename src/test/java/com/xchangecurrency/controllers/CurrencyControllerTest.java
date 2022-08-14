/*
 * Copyright (c) 2022 XChangeCurrency API.
 *
 */
package com.xchangecurrency.controllers;

import com.xchangecurrency.dtos.AvailableCurrenciesGet;
import com.xchangecurrency.dtos.CurrencyGet;
import com.xchangecurrency.errorhandling.ClientException;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;

import java.util.List;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class CurrencyControllerTest extends AbstractControllerTest {

    @Test
    void testGetAvailableCurrencies() throws Exception {
        // Given
        AvailableCurrenciesGet expected = AvailableCurrenciesGet.builder()
                .availableCurrencies(List.of(CurrencyGet.builder().code("GBP").name("British Pound").build(),
                                             CurrencyGet.builder().code("INR").name("Indian Rupee").build()))
                .total(2).build();
        when(currencyService.getAvailableCurrencies()).thenReturn(expected);

        // When - Then
        this.mockMvc.perform(get("/v1/currencies")).andExpect(status().isOk())
                .andExpect(jsonPath("$.total").value(expected.getTotal()))
                .andExpect(jsonPath("$.availableCurrencies[0].code")
                        .value(expected.getAvailableCurrencies().get(0).getCode()))
                .andExpect(jsonPath("$.availableCurrencies[0].name")
                        .value(expected.getAvailableCurrencies().get(0).getName()))
                .andExpect(jsonPath("$.availableCurrencies[1].code")
                        .value(expected.getAvailableCurrencies().get(1).getCode()))
                .andExpect(jsonPath("$.availableCurrencies[1].name")
                        .value(expected.getAvailableCurrencies().get(1).getName()));
    }

    @Test
    void testGetCurrencyNameForCode_ValidInput() throws Exception {
        // Given
        CurrencyGet expected = CurrencyGet.builder().code("INR").name("Indian Rupee").build();
        when(currencyService.getCurrencyNameForCode("inr")).thenReturn(expected);

        // When - Then
        this.mockMvc.perform(get("/v1/currencies/inr")).andExpect(status().isOk())
                .andExpect(jsonPath("$.code").value(expected.getCode()))
                .andExpect(jsonPath("$.name").value(expected.getName()));
    }

    @Test
    void testGetCurrencyNameForCode_InvalidInput() throws Exception {
        // Given
        when(currencyService.getCurrencyNameForCode("invalid")).thenThrow(new ClientException("some message"));

        // When - Then
        this.mockMvc.perform(get("/v1/currencies/invalid")).andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.url").value(TEST_URL_PREFIX + "/v1/currencies/invalid"))
                .andExpect(jsonPath("$.status").value(HttpStatus.BAD_REQUEST.getReasonPhrase()))
                .andExpect(jsonPath("$.timestamp").isNotEmpty()).andExpect(jsonPath("$.message").value("some message"));
    }

}

/*
 * Copyright (c) 2022 XChangeCurrency API.
 *
 */
package com.xchangecurrency.services;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

import com.xchangecurrency.constants.ExceptionMessages;
import com.xchangecurrency.dtos.AvailableCurrenciesGet;
import com.xchangecurrency.dtos.CurrencyGet;

import java.util.List;
import java.util.Map;

import com.xchangecurrency.errorhandling.ClientException;
import org.junit.jupiter.api.Test;

class CurrencyServiceTest extends AbstractServiceTest {

    @Test
    void testGetAvailableCurrencies() {
        // Given
        AvailableCurrenciesGet expected = AvailableCurrenciesGet.builder()
                .availableCurrencies(List.of(CurrencyGet.builder().code("GBP").name("British Pound").build(),
                                             CurrencyGet.builder().code("INR").name("Indian Rupee").build()))
                .total(2).build();
        when(currenciesProperties.getCurrencies()).thenReturn(Map.of("GBP", "British Pound", "INR", "Indian Rupee"));

        // When
        AvailableCurrenciesGet actual = currencyService.getAvailableCurrencies();

        // Then
        assertEquals(expected, actual);
    }

    @Test
    void testGetCurrencyNameForCode_ValidInput() throws ClientException {
        // Given
        CurrencyGet expected = CurrencyGet.builder().code("INR").name("Indian Rupee").build();
        when(currenciesProperties.getCurrencies()).thenReturn(Map.of("GBP", "British Pound", "INR", "Indian Rupee"));

        // When
        CurrencyGet actual = currencyService.getCurrencyNameForCode("INR");

        // Then
        assertEquals(expected, actual);
    }

    @Test
    void testGetCurrencyNameForCode_ValidInput_CaseInsensitive() throws ClientException {
        // Given
        CurrencyGet expected = CurrencyGet.builder().code("INR").name("Indian Rupee").build();
        when(currenciesProperties.getCurrencies()).thenReturn(Map.of("GBP", "British Pound", "INR", "Indian Rupee"));

        // When
        CurrencyGet actual = currencyService.getCurrencyNameForCode("iNR");

        // Then
        assertEquals(expected, actual);
    }

    @Test
    void testGetCurrencyNameForCode_InvalidInput() {
        // Given
        when(currenciesProperties.getCurrencies()).thenReturn(Map.of("GBP", "British Pound", "INR", "Indian Rupee"));

        // When - Then
        ClientException thrown = assertThrows(ClientException.class,
                                              () -> currencyService.getCurrencyNameForCode("INVALID"));
        assertEquals(ExceptionMessages.UNSUPPORTED_CURRENCY + "INVALID", thrown.getMessage(),
                     "Exception message doesn't match.");
    }
}

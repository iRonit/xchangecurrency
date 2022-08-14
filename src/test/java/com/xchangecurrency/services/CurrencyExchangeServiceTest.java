/*
 * Copyright (c) 2022 XChangeCurrency API.
 *
 */
package com.xchangecurrency.services;

import static org.apache.commons.lang3.StringUtils.EMPTY;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;
import static org.springframework.http.HttpMethod.GET;

import com.xchangecurrency.constants.ExceptionMessages;
import com.xchangecurrency.dtos.CurrencyGet;
import com.xchangecurrency.dtos.ExchangeRateGet;
import com.xchangecurrency.errorhandling.ClientException;
import com.xchangecurrency.errorhandling.ServerException;
import java.util.Map;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.ResponseEntity;

@SpringBootTest
class CurrencyExchangeServiceTest extends AbstractServiceTest {

    private static final String CURRENCY_ME_UK_VALID_URL = CurrencyExchangeService.CURRENCY_ME_UK_URL + "gbp/inr";

    @Test
    void testGetExchangeRate_ValidInput() throws ServerException, ClientException {
        // Given
        ExchangeRateGet expected = ExchangeRateGet.builder()
                .fromCurrency(CurrencyGet.builder().code("GBP").name("British Pound").build())
                .toCurrency(CurrencyGet.builder().code("INR").name("Indian Rupee").build()).amount(100F).build();
        when(currenciesProperties.getCurrencies()).thenReturn(Map.of("GBP", "British Pound", "INR", "Indian Rupee"));
        when(restTemplate.exchange(CURRENCY_ME_UK_VALID_URL, GET, null, String.class)).thenReturn(ResponseEntity
                .ok("<h3>Latest Currency Exchange Rates: 1 British Pound = 100.000 Indian Rupee</h3>"));

        // When
        ExchangeRateGet actual = currencyExchangeService.getExchangeRate("GBP", "INR");

        // Then
        assertEquals(expected, actual);
    }

    @Test
    void testGetExchangeRate_ValidInput_CaseInsensitive() throws ServerException, ClientException {
        // Given
        ExchangeRateGet expected = ExchangeRateGet.builder()
                .fromCurrency(CurrencyGet.builder().code("GBP").name("British Pound").build())
                .toCurrency(CurrencyGet.builder().code("INR").name("Indian Rupee").build()).amount(100F).build();
        when(currenciesProperties.getCurrencies()).thenReturn(Map.of("GBP", "British Pound", "INR", "Indian Rupee"));
        when(restTemplate.exchange(CURRENCY_ME_UK_VALID_URL, GET, null, String.class)).thenReturn(ResponseEntity
                .ok("<h3>Latest Currency Exchange Rates: 1 British Pound = 100.000 Indian Rupee</h3>"));

        // When
        ExchangeRateGet actual = currencyExchangeService.getExchangeRate("gBp", "iNR");

        // Then
        assertEquals(expected, actual);
    }

    @Test
    void testGetExchangeRate_InvalidFromCurrency() {
        // Given
        when(currenciesProperties.getCurrencies()).thenReturn(Map.of("GBP", "British Pound", "INR", "Indian Rupee"));

        // When - Then
        ClientException thrown = assertThrows(ClientException.class,
                                              () -> currencyExchangeService.getExchangeRate("INVALID", "INR"));
        assertEquals(ExceptionMessages.UNSUPPORTED_CURRENCY + "INVALID", thrown.getMessage(),
                     "Exception message doesn't match.");
    }

    @Test
    void testGetExchangeRate_InvalidToCurrency() {
        // Given
        when(currenciesProperties.getCurrencies()).thenReturn(Map.of("GBP", "British Pound", "INR", "Indian Rupee"));

        // When - Then
        ClientException thrown = assertThrows(ClientException.class,
                                              () -> currencyExchangeService.getExchangeRate("GBP", "INVALID"));
        assertEquals(ExceptionMessages.UNSUPPORTED_CURRENCY + "INVALID", thrown.getMessage(),
                     "Exception message doesn't match.");
    }

    @Test
    void testGetExchangeRate_ExternalResourceUnavailable() {
        // Given
        when(currenciesProperties.getCurrencies()).thenReturn(Map.of("GBP", "British Pound", "INR", "Indian Rupee"));
        when(restTemplate.exchange(CurrencyExchangeService.CURRENCY_ME_UK_URL + "gbp/inr", GET, null, String.class))
                .thenReturn(ResponseEntity.ok(EMPTY));

        // When - Then
        ServerException thrown = assertThrows(ServerException.class,
                                              () -> currencyExchangeService.getExchangeRate("GBP", "INR"));
        assertEquals(ExceptionMessages.CURRENCY_EXCHANGE_INFO_NOT_FOUND, thrown.getMessage(),
                     "Exception message doesn't match.");
    }

    @Test
    void testGetExchangeRate_ExternalResourceBodyInvalidPattern() {
        // Given
        when(currenciesProperties.getCurrencies()).thenReturn(Map.of("GBP", "British Pound", "INR", "Indian Rupee"));
        when(restTemplate.exchange(CURRENCY_ME_UK_VALID_URL, GET, null, String.class))
                .thenReturn(ResponseEntity.ok("pattern has changed: 1 British Pound = 100.000 Indian Rupee</h3>"));

        // When - Then
        ServerException thrown = assertThrows(ServerException.class,
                                              () -> currencyExchangeService.getExchangeRate("GBP", "INR"));
        assertEquals(ExceptionMessages.CURRENCY_EXCHANGE_INFO_NOT_FOUND, thrown.getMessage(),
                     "Exception message doesn't match.");
    }
}

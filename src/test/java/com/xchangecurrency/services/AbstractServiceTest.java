/*
 * Copyright (c) 2022 XChangeCurrency API.
 *
 */
package com.xchangecurrency.services;

import com.xchangecurrency.configs.CurrenciesProperties;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.web.client.RestTemplate;

@SpringBootTest
public abstract class AbstractServiceTest {
    @Mock
    protected RestTemplate restTemplate;

    @Mock
    protected CurrenciesProperties currenciesProperties;

    @InjectMocks
    protected CurrencyExchangeService currencyExchangeService;

    @InjectMocks
    protected CurrencyService currencyService;
}

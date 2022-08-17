/*
 * Copyright (c) 2022 XChangeCurrency API.
 *
 */
package com.xchangecurrency.services;

import com.xchangecurrency.configs.CurrenciesProperties;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.web.client.RestTemplate;

@ExtendWith(SpringExtension.class)
public abstract class AbstractServiceTest {

            static final String TESTING = "Making a change to test merge";

    @Mock
    protected RestTemplate restTemplate;

    @Mock
    protected CurrenciesProperties currenciesProperties;

    @InjectMocks
    protected CurrencyExchangeService currencyExchangeService;

    @InjectMocks
    protected CurrencyService currencyService;
}

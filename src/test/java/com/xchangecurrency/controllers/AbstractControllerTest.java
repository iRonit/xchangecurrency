/*
 * Copyright (c) 2022 XChangeCurrency API.
 *
 */
package com.xchangecurrency.controllers;

import com.xchangecurrency.services.CurrencyExchangeService;
import com.xchangecurrency.services.CurrencyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

@WebMvcTest
public abstract class AbstractControllerTest {

    protected static final String TEST_URL_PREFIX = "http://localhost";

    @Autowired
    protected MockMvc mockMvc;

    @MockBean
    protected CurrencyService currencyService;

    @MockBean
    protected CurrencyExchangeService currencyExchangeService;
}

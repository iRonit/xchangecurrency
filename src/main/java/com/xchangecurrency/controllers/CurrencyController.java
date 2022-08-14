/*
 * Copyright (c) 2022 XChangeCurrency API.
 *
 */
package com.xchangecurrency.controllers;

import com.xchangecurrency.constants.UrlConst;
import com.xchangecurrency.dtos.AvailableCurrenciesGet;
import com.xchangecurrency.dtos.CurrencyGet;
import com.xchangecurrency.errorhandling.ClientException;
import com.xchangecurrency.services.CurrencyService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * REST endpoints for v1 version of currencies APIs. Limited to providing details on currency.
 *
 * @author Ronit Pradhan
 */
@RestController
@RequestMapping("/v1/currencies")
@RequiredArgsConstructor
public class CurrencyController {
    private final CurrencyService currencyService;

    /**
     * Available/Supported Currencies List API. Fetches the list of supported currencies.
     *
     * @return {@link AvailableCurrenciesGet}
     */
    @GetMapping
    public AvailableCurrenciesGet getAvailableCurrencies() {
        return currencyService.getAvailableCurrencies();
    }

    /**
     * Currency Name API. Fetches the name of a currency for its code.
     *
     * @param currCode Currency Code
     * @return {@link CurrencyGet}
     * @throws ClientException if the currency code is invalid or not supported
     */
    @GetMapping(UrlConst.GET_CURRENCY_NAME_URL)
    public CurrencyGet getCurrencyNameForCode(@PathVariable final String currCode) throws ClientException {
        return currencyService.getCurrencyNameForCode(currCode);
    }
}

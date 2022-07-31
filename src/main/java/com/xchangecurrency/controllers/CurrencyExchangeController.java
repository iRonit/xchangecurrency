package com.xchangecurrency.controllers;

import com.xchangecurrency.services.CurrencyExchangeService;
import constants.UrlConst;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * REST endpoints for v1 version of xchange APIs.
 * Limited to providing currency exchange rate.
 *
 * @author Ronit Pradhan
 */
@RestController
@RequestMapping("/v1/xchange")
@RequiredArgsConstructor
public class CurrencyExchangeController {

    private final CurrencyExchangeService currencyExchangeService;

    @GetMapping(UrlConst.GET_CURRENCY_EXCHANGE_RATE_URL)
    public Float getCurrencyExchangeRate(@PathVariable final String frmCurr, @PathVariable final String toCurr) {
        return currencyExchangeService.getExchangeRate(frmCurr, toCurr);
    }
}

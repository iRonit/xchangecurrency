/*
 * Copyright (c) 2022 XChangeCurrency API.
 *
 */
package com.xchangecurrency.controllers;

import com.xchangecurrency.constants.UrlConst;
import com.xchangecurrency.dtos.ExchangeRateGet;
import com.xchangecurrency.errorhandling.ClientException;
import com.xchangecurrency.errorhandling.ServerException;
import com.xchangecurrency.services.CurrencyExchangeService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * REST endpoints for v1 version of xchange APIs. Limited to providing currency exchange rate.
 *
 * @author Ronit Pradhan
 */
@RestController
@RequestMapping("/v1/xchange")
@RequiredArgsConstructor
public class CurrencyExchangeController {
  private final CurrencyExchangeService currencyExchangeService;

  /**
   * Exchange Rate API. Gets the latest foreign currency exchange rate.
   *
   * @param frmCurr From Currency
   * @param toCurr  To Currency
   * @return {@link ExchangeRateGet}
   * @throws ClientException if the currencies are invalid or not supported
   * @throws ServerException if data is unable to be fetched from external source
   */
  @GetMapping(UrlConst.GET_CURRENCY_EXCHANGE_RATE_URL)
  public ExchangeRateGet getCurrencyExchangeRate(
          @PathVariable final String frmCurr, @PathVariable final String toCurr)
          throws ServerException, ClientException {
    return currencyExchangeService.getExchangeRate(frmCurr, toCurr);
  }
}

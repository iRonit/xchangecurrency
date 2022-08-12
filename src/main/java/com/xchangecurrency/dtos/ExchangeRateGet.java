/*
 * Copyright (c) 2022 XChangeCurrency API.
 *
 */
package com.xchangecurrency.dtos;

import lombok.Builder;
import lombok.Data;

import java.util.Date;

@Data
@Builder
public class ExchangeRateGet {
  private CurrencyGet fromCurrency;

  private CurrencyGet toCurrency;

  private float amount;

  private Date lastUpdated;
}

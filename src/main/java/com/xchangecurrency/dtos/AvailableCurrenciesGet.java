/*
 * Copyright (c) 2022 XChangeCurrency API.
 *
 */
package com.xchangecurrency.dtos;

import java.util.List;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class AvailableCurrenciesGet {
  private int total;

  private List<CurrencyGet> availableCurrencies;
}

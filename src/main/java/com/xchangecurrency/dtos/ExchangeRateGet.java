/*
 * Copyright (c) 2022 XChangeCurrency API.
 *
 */
package com.xchangecurrency.dtos;

import java.util.Date;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@Builder
public class ExchangeRateGet {
    private CurrencyGet fromCurrency;

    private CurrencyGet toCurrency;

    private float amount;

    @EqualsAndHashCode.Exclude
    private Date lastUpdated;
}

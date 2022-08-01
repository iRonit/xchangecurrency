package com.xchangecurrency.dtos;

import lombok.Builder;
import lombok.Data;

import java.util.Date;

@Data
@Builder
public class ExchangeRateGet {

    private Currency fromCurrency;
    private Currency toCurrency;
    private float amount;
    private Date lastUpdated;
}

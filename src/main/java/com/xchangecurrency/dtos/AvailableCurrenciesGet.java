package com.xchangecurrency.dtos;

import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class AvailableCurrenciesGet {
    private int total;
    private List<Currency> availableCurrencies;
}

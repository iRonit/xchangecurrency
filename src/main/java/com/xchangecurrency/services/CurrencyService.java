package com.xchangecurrency.services;

import com.xchangecurrency.configs.CurrenciesProperties;
import com.xchangecurrency.dtos.AvailableCurrenciesGet;
import com.xchangecurrency.dtos.Currency;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 * Handles the logic around Currency Details.
 *
 * @author Ronit Pradhan
 */
@Service
@RequiredArgsConstructor
@Slf4j
public class CurrencyService {
    private final CurrenciesProperties currenciesProperties;

    /**
     * Returns a list of currencies supported.
     *
     * @return {@link AvailableCurrenciesGet}
     */
    public AvailableCurrenciesGet getAvailableCurrencies() {
        return AvailableCurrenciesGet.builder()
                .total(currenciesProperties.getCurrencies().size())
                .availableCurrencies(currenciesProperties.getCurrencies().keySet().stream()
                        .map(key -> Currency.builder().code(key).name(currenciesProperties.getCurrencies().get(key)).build())
                        .toList())
                .build();
    }
}

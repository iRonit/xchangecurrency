package com.xchangecurrency.services;

import com.xchangecurrency.configs.CurrenciesProperties;
import com.xchangecurrency.dtos.AvailableCurrenciesGet;
import com.xchangecurrency.dtos.CurrencyGet;
import com.xchangecurrency.errorhandling.ClientException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.lang.NonNull;
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
                        .map(key -> CurrencyGet.builder().code(key).name(currenciesProperties.getCurrencies().get(key)).build())
                        .toList())
                .build();
    }

    /**
     * Returns the currency name for a currency code.
     *
     * @param currCode Currency Code
     * @return {@link CurrencyGet}
     * @throws {@link ClientException} on Bad requests
     */
    public CurrencyGet getCurrencyNameForCode(@NonNull final String currCode) throws ClientException {
        // Validation
        if (!currenciesProperties.getCurrencies().containsKey(currCode.toUpperCase())) {
            log.error("Currency Code is not present in the data-map: {}", currCode);
            throw new ClientException("Unsupported currency: " + currCode);
        }

        // Return
        return CurrencyGet.builder()
                .code(currCode.toUpperCase())
                .name(currenciesProperties.getCurrencies().get(currCode.toUpperCase()))
                .build();
    }
}

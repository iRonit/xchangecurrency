package com.xchangecurrency.services;

import com.xchangecurrency.configs.CurrenciesProperties;
import com.xchangecurrency.configs.CurrencyExchangeConfig;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static java.util.regex.Pattern.MULTILINE;
import static org.apache.commons.lang3.StringUtils.isBlank;
import static org.springframework.http.HttpMethod.GET;

/**
 * Handles the logic around Currency Exchange.
 *
 * @author Ronit Pradhan
 */
@Service
@CacheConfig(cacheNames = CurrencyExchangeConfig.CURRENCIES_CACHE)
@RequiredArgsConstructor
@Slf4j
public class CurrencyExchangeService {

    private static final String CURRENCY_ME_UK_URL = "https://www.currency.me.uk/convert/";

    private final RestTemplate restTemplate;
    private final CurrenciesProperties currenciesProperties;

    /**
     * Gets the Current Currency Exchange Rate from the External Source
     * Caches the result
     *
     * @param frmCurr From Currency
     * @param toCurr  To Currency
     * @return the current currency exchange rate
     * @throws Exception if there's any issue while fetching from the external source
     */
    @Cacheable
    public float getExchangeRate(@NonNull final String frmCurr, @NonNull final String toCurr) throws Exception {
        // Validation
        validateGetExchangeRateRequest(frmCurr, toCurr);

        // Fetch and Return
        return fetchCurrentExchangeRate(frmCurr, toCurr);
    }

    /**
     * Validate the currencies in the request parameter.
     *
     * @param frmCurr From Currency
     * @param toCurr  To Currency
     * @throws IllegalArgumentException if the currencies are not supported
     */
    private void validateGetExchangeRateRequest(final String frmCurr, final String toCurr) throws IllegalArgumentException {
        // Validate From Currency
        if (!currenciesProperties.getCurrencies().containsKey(frmCurr.toUpperCase())) {
            log.error("From Currency is not present in the data-map: {}", frmCurr);
            throw new IllegalArgumentException("Unsupported currency: " + frmCurr);
        }
        // Validate To Currency
        if (!currenciesProperties.getCurrencies().containsKey(toCurr.toUpperCase())) {
            log.error("To Currency is not present in the data-map: {}", toCurr);
            throw new IllegalArgumentException("Unsupported currency: " + toCurr);
        }
    }

    /**
     * Fetch and Parse the Current Currency Exchange Rate from External Source
     *
     * @param frmCurr From Currency
     * @param toCurr  To Currency
     * @return the current exchange rate
     * @throws Exception if the value is unable to be fetched from the external source
     */
    private float fetchCurrentExchangeRate(final String frmCurr, final String toCurr) throws Exception {
        // Fetch the html content that has the currency exchange rate info
        String response = fireApiCallToExternal(frmCurr, toCurr);

        // Extract the numerical value
        return extractExchangeRateFromResponse(response);
    }

    /**
     * Call the External URL to fetch the HTML body that holds the currency exchange information.
     *
     * @param frmCurr From Currency
     * @param toCurr  To Currency
     * @return HTML Body in String format
     * @throws Exception If no response body found
     */
    private String fireApiCallToExternal(final String frmCurr, final String toCurr) throws Exception {
        String urlToCall = CURRENCY_ME_UK_URL + frmCurr.toLowerCase() + "/" + toCurr.toLowerCase();
        ResponseEntity<String> response = restTemplate.exchange(urlToCall, GET, null, String.class);
        if (isBlank(response.getBody())) {
            log.error("Empty response body from : {}", urlToCall);
            // TODO: Create custom exception
            throw new Exception("Currency Exchange Rate information not found.");
        }
        return response.getBody();
    }

    /**
     * Parse and Extract the numerical value of the Currency Exchange Rate from Response Body
     *
     * @param response Response Body in String format
     * @return Exchange Rate Value
     * @throws Exception If Exchange Rate information is not found or could not be parsed
     */
    private float extractExchangeRateFromResponse(final String response) throws Exception {
        float exchangeRateValue;
        Pattern pattern = Pattern.compile("Latest Currency Exchange Rates:.*\\s(\\d+\\.\\d+).*", MULTILINE);
        Matcher matcher = pattern.matcher(response);
        if (matcher.find()) {
            exchangeRateValue = Float.parseFloat(matcher.group(1).trim());
        } else {
            log.error("Could not find the exchange rate value.");
            // TODO: Create custom exception
            throw new Exception("Currency Exchange Rate information not found.");
        }
        return exchangeRateValue;
    }
}

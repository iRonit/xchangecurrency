/*
 * Copyright (c) 2022 XChangeCurrency API.
 *
 */
package com.xchangecurrency.configs;

import com.google.common.cache.CacheBuilder;
import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;
import org.springframework.cache.concurrent.ConcurrentMapCache;
import org.springframework.cache.concurrent.ConcurrentMapCacheManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.lang.NonNull;
import org.springframework.web.client.RestTemplate;

import java.util.List;

import static java.util.concurrent.TimeUnit.MINUTES;

@Configuration
public class CurrencyExchangeConfig {
    public static final String CURRENCIES_CACHE = "currencies_cache";

    @Bean
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }

    @Bean
    public CacheManager cacheManager() {
        ConcurrentMapCacheManager cacheManager =
                new ConcurrentMapCacheManager() {

                    @Override
                    @NonNull
                    protected Cache createConcurrentMapCache(@NonNull final String name) {
                        return new ConcurrentMapCache(
                                name,
                                CacheBuilder.newBuilder()
                                        .expireAfterWrite(15, MINUTES)
                                        .maximumSize(1000)
                                        .build()
                                        .asMap(),
                                false);
                    }
                };
        cacheManager.setCacheNames(List.of(CURRENCIES_CACHE));
        return cacheManager;
    }
}

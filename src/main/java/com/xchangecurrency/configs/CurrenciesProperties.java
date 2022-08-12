/*
 * Copyright (c) 2022 XChangeCurrency API.
 *
 */
package com.xchangecurrency.configs;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
@ConfigurationProperties(prefix = "data")
@Getter
@Setter
public class CurrenciesProperties {
  /**
   * Supported map of currencies
   */
  private Map<String, String> currencies;
}

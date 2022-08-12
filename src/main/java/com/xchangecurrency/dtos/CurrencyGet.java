/*
 * Copyright (c) 2022 XChangeCurrency API.
 *
 */
package com.xchangecurrency.dtos;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class CurrencyGet {
  private String code;

  private String name;
}

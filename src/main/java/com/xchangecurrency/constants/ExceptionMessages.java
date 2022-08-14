/*
 * Copyright (c) 2022 XChangeCurrency API.
 *
 */
package com.xchangecurrency.constants;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class ExceptionMessages {

    // Client
    public static final String UNSUPPORTED_CURRENCY = "Unsupported currency: ";

    // Server
    public static final String CURRENCY_EXCHANGE_INFO_NOT_FOUND = "Currency Exchange Rate information not found.";
}

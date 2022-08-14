/*
 * Copyright (c) 2022 XChangeCurrency API.
 *
 */
package com.xchangecurrency.constants;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class UrlConst {

    public static final String GET_CURRENCY_EXCHANGE_RATE_URL = "/{frmCurr}/{toCurr}";

    public static final String GET_CURRENCY_NAME_URL = "/{currCode}";
}

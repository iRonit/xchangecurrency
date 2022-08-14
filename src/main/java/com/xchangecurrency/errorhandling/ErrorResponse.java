/*
 * Copyright (c) 2022 XChangeCurrency API.
 *
 */
package com.xchangecurrency.errorhandling;

import java.util.Date;
import lombok.Builder;
import lombok.Data;

/**
 * HTTP Response when an Error/Exception occurs
 *
 * @author Ronit Pradhan
 */
@Data
@Builder
public class ErrorResponse {
    private String url;

    private String status;

    private Date timestamp;

    private String message;
}

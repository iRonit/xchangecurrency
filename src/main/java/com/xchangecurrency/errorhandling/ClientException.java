package com.xchangecurrency.errorhandling;

import lombok.experimental.StandardException;

/**
 * Signifies Exceptions occurring due to the Client side input
 *
 * @author Ronit Pradhan
 */
@StandardException
public class ClientException extends Exception {

    public ClientException(final String message) {
        super(message);
    }
}

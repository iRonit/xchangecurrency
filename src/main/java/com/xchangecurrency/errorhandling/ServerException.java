package com.xchangecurrency.errorhandling;

import lombok.experimental.StandardException;

/**
 * Signifies Exceptions occurring due to the Server side processing
 *
 * @author Ronit Pradhan
 */
@StandardException
public class ServerException extends Exception {

    public ServerException(final String message) {
        super(message);
    }
}

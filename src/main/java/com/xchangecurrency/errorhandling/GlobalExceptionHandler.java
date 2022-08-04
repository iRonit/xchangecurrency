package com.xchangecurrency.errorhandling;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.ServletWebRequest;
import org.springframework.web.context.request.WebRequest;

import java.time.Instant;
import java.util.Date;

/**
 * Exception Handler for all the endpoints
 *
 * @author Ronit Pradhan
 */
@ControllerAdvice
public class GlobalExceptionHandler {

    /**
     * Handle Bad Request Exceptions
     **/
    @ExceptionHandler(ClientException.class)
    public ResponseEntity<ErrorResponse> handleClientExceptions(final ClientException clientException, final WebRequest request) {
        return new ResponseEntity<>(
                ErrorResponse.builder()
                        .url(((ServletWebRequest) request).getRequest().getRequestURL().toString())
                        .status(HttpStatus.BAD_REQUEST.getReasonPhrase())
                        .timestamp(Date.from(Instant.now()))
                        .message(clientException.getMessage()).build(),
                HttpStatus.BAD_REQUEST);
    }


    /**
     * Handle any other Exception
     **/
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> handleAllOtherExceptions(final Exception anyException, final WebRequest request) {
        return new ResponseEntity<>(
                ErrorResponse.builder()
                        .url(((ServletWebRequest) request).getRequest().getRequestURL().toString())
                        .status(HttpStatus.INTERNAL_SERVER_ERROR.getReasonPhrase())
                        .timestamp(Date.from(Instant.now()))
                        .message(anyException.getMessage()).build(),
                HttpStatus.INTERNAL_SERVER_ERROR);
    }
}

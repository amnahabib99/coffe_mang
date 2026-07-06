package com.coffeeshop.exceptions;

/**
 * Thrown when login or account verification fails.
 */
public class AuthenticationException extends Exception {
    /**
     * Creates an authentication exception.
     *
     * @param message readable message
     */
    public AuthenticationException(String message) {
        super(message);
    }
}

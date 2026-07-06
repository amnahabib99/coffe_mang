package com.coffeeshop.exceptions;

/**
 * Thrown when a user tries to access a feature outside their role.
 */
public class UnauthorizedActionException extends Exception {
    /**
     * Creates an unauthorized action exception.
     *
     * @param message readable message
     */
    public UnauthorizedActionException(String message) {
        super(message);
    }
}

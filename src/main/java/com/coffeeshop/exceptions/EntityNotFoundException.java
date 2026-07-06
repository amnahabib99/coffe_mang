package com.coffeeshop.exceptions;

/**
 * Thrown when a requested database entity cannot be found.
 */
public class EntityNotFoundException extends Exception {
    /**
     * Creates a not-found exception.
     *
     * @param message readable message
     */
    public EntityNotFoundException(String message) {
        super(message);
    }
}

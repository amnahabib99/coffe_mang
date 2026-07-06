package com.coffeeshop.exceptions;

/**
 * Thrown when input violates validation rules.
 */
public class InvalidInputException extends Exception {
    /**
     * Creates an input validation exception.
     *
     * @param message readable message
     */
    public InvalidInputException(String message) {
        super(message);
    }
}

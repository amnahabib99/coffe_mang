package com.coffeeshop.exceptions;

/**
 * Thrown when an order cannot be completed.
 */
public class OrderProcessingException extends Exception {
    /**
     * Creates an order processing exception.
     *
     * @param message readable message
     */
    public OrderProcessingException(String message) {
        super(message);
    }
}

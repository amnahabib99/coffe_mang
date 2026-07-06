package com.coffeeshop.exceptions;

/**
 * Thrown when the application cannot connect to MySQL.
 */
public class DatabaseConnectionException extends Exception {
    /**
     * Creates a database connection exception.
     *
     * @param message readable message
     * @param cause original cause
     */
    public DatabaseConnectionException(String message, Throwable cause) {
        super(message, cause);
    }
}

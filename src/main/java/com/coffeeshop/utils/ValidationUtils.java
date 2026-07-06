package com.coffeeshop.utils;

import com.coffeeshop.exceptions.InvalidInputException;

/**
 * Static validation helper methods used by models and services.
 */
public final class ValidationUtils {
    private ValidationUtils() {
    }

    /**
     * Ensures text is not null or blank.
     *
     * @param value field value
     * @param fieldName displayed field name
     * @throws InvalidInputException when the value is blank
     */
    public static void requireText(String value, String fieldName) throws InvalidInputException {
        if (value == null || value.trim().isEmpty()) {
            throw new InvalidInputException(fieldName + " cannot be empty.");
        }
    }

    /**
     * Ensures a number is positive.
     *
     * @param value numeric value
     * @param fieldName displayed field name
     * @throws InvalidInputException when the value is not positive
     */
    public static void requirePositive(double value, String fieldName) throws InvalidInputException {
        if (value <= 0) {
            throw new InvalidInputException(fieldName + " must be greater than zero.");
        }
    }
}

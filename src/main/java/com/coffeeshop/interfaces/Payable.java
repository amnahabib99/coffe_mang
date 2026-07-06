package com.coffeeshop.interfaces;

/**
 * Defines payment total calculation behavior.
 */
public interface Payable {
    /**
     * Calculates the final total.
     *
     * @return total value
     */
    double calculateTotal();
}

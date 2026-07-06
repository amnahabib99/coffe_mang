package com.coffeeshop.interfaces;

/**
 * Defines discount calculation behavior.
 */
public interface Discountable {
    /**
     * Calculates a discount from a subtotal.
     *
     * @param subtotal subtotal
     * @return discount value
     */
    double calculateDiscount(double subtotal);
}

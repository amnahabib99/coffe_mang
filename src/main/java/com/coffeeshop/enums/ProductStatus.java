package com.coffeeshop.enums;

/**
 * Product availability status.
 */
public enum ProductStatus {
    /** Product can be sold. */
    AVAILABLE,
    /** Product is hidden from ordering. */
    UNAVAILABLE;

    @Override
    public String toString() {
        return this == AVAILABLE ? "متوفر" : "غير متوفر";
    }
}

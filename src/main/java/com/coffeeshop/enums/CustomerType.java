package com.coffeeshop.enums;

/**
 * Customer type used for discounts.
 */
public enum CustomerType {
    /** Normal customer. */
    REGULAR,
    /** VIP customer receives a discount. */
    VIP;

    @Override
    public String toString() {
        return this == VIP ? "عميل مميز" : "عميل عادي";
    }
}

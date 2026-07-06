package com.coffeeshop.models;

import com.coffeeshop.interfaces.Printable;

/**
 * Printable invoice wrapper for an order.
 */
public class Invoice implements Printable {
    private final Order order;

    /**
     * Creates an invoice for an order.
     *
     * @param order completed order
     */
    public Invoice(Order order) {
        this.order = order;
    }

    @Override
    public String print() {
        return order.print();
    }
}

package com.coffeeshop.services;

import com.coffeeshop.models.Invoice;
import com.coffeeshop.models.Order;

/**
 * Creates printable invoices from orders.
 */
public class InvoiceService {
    /**
     * Builds printable invoice text.
     *
     * @param order order
     * @return invoice text
     */
    public String createInvoiceText(Order order) {
        return new Invoice(order).print();
    }
}

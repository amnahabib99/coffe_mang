package com.coffeeshop.models;

import com.coffeeshop.enums.CustomerType;
import com.coffeeshop.exceptions.InvalidInputException;
import com.coffeeshop.interfaces.Discountable;

/**
 * VIP customer implementation used to demonstrate interface polymorphism.
 */
public class VIPCustomer extends Customer implements Discountable {
    /**
     * Creates a VIP customer.
     *
     * @throws InvalidInputException when name is invalid
     */
    public VIPCustomer(int id, String name, String phone) throws InvalidInputException {
        super(id, name, phone, CustomerType.VIP);
    }

    @Override
    public double calculateDiscount(double subtotal) {
        return subtotal * 0.10;
    }
}

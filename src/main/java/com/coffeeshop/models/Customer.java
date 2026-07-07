package com.coffeeshop.models;

import com.coffeeshop.abstracts.Person;
import com.coffeeshop.enums.CustomerType;
import com.coffeeshop.exceptions.InvalidInputException;

/**
 * Represents a customer attached to orders.
 */
public class Customer extends Person {
    private CustomerType type;

    /**
     * Creates a regular customer.
     *
     * @param name customer name
     * @param phone customer phone number
     * @throws InvalidInputException when name is invalid
     */
    public Customer(String name, String phone) throws InvalidInputException {
        this(0, name, phone, CustomerType.REGULAR);
    }

    /**
     * Creates a customer with type.
     *
     * @param id customer id
     * @param name customer name
     * @param phone customer phone number
     * @param type customer type
     * @throws InvalidInputException when name is invalid
     */
    public Customer(int id, String name, String phone, CustomerType type) throws InvalidInputException {
        super(id, name, phone);
        this.type = type == null ? CustomerType.REGULAR : type;
    }

    @Override
    public String getRoleDescription() {
        return type == CustomerType.VIP ? "عميل مميز: يحصل على خصم" : "عميل عادي";
    }

    /**
     * Returns the customer type.
     *
     * @return customer type
     */
    public CustomerType getType() {
        return type;
    }

    /**
     * Updates the customer type.
     *
     * @param type customer type
     */
    public void setType(CustomerType type) {
        this.type = type;
    }
}

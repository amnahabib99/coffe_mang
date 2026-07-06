package com.coffeeshop.services;

import com.coffeeshop.models.Customer;
import com.coffeeshop.repositories.CustomerRepository;

import java.util.List;

/**
 * Service for customer operations.
 */
public class CustomerService {
    private final CustomerRepository repository = new CustomerRepository();

    /**
     * Saves a customer.
     *
     * @param customer customer
     * @return saved customer
     * @throws Exception when saving fails
     */
    public Customer save(Customer customer) throws Exception {
        return repository.save(customer);
    }

    /**
     * @return all customers
     * @throws Exception when loading fails
     */
    public List<Customer> getAll() throws Exception {
        return repository.findAll();
    }
}

package com.coffeeshop.repositories;

import com.coffeeshop.database.DatabaseManager;
import com.coffeeshop.enums.CustomerType;
import com.coffeeshop.interfaces.Repository;
import com.coffeeshop.models.Customer;
import com.coffeeshop.models.VIPCustomer;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * Database repository for customers.
 */
public class CustomerRepository implements Repository<Customer> {
    @Override
    public Customer save(Customer customer) throws Exception {
        try (Connection connection = DatabaseManager.getConnection();
             PreparedStatement statement = connection.prepareStatement(
                     "INSERT INTO customers(name, phone, type) VALUES(?,?,?)", Statement.RETURN_GENERATED_KEYS)) {
            statement.setString(1, customer.getName());
            statement.setString(2, customer.getPhone());
            statement.setString(3, customer.getType().name());
            statement.executeUpdate();
            try (ResultSet keys = statement.getGeneratedKeys()) {
                if (keys.next()) {
                    customer.setId(keys.getInt(1));
                }
            }
            return customer;
        }
    }

    @Override
    public void update(Customer customer) throws Exception {
        try (Connection connection = DatabaseManager.getConnection();
             PreparedStatement statement = connection.prepareStatement("UPDATE customers SET name=?, phone=?, type=? WHERE id=?")) {
            statement.setString(1, customer.getName());
            statement.setString(2, customer.getPhone());
            statement.setString(3, customer.getType().name());
            statement.setInt(4, customer.getId());
            statement.executeUpdate();
        }
    }

    @Override
    public void delete(int id) throws Exception {
        try (Connection connection = DatabaseManager.getConnection();
             PreparedStatement statement = connection.prepareStatement("DELETE FROM customers WHERE id=?")) {
            statement.setInt(1, id);
            statement.executeUpdate();
        }
    }

    @Override
    public Optional<Customer> findById(int id) throws Exception {
        try (Connection connection = DatabaseManager.getConnection();
             PreparedStatement statement = connection.prepareStatement("SELECT * FROM customers WHERE id=?")) {
            statement.setInt(1, id);
            try (ResultSet resultSet = statement.executeQuery()) {
                return resultSet.next() ? Optional.of(map(resultSet)) : Optional.empty();
            }
        }
    }

    @Override
    public List<Customer> findAll() throws Exception {
        List<Customer> customers = new ArrayList<>();
        try (Connection connection = DatabaseManager.getConnection();
             PreparedStatement statement = connection.prepareStatement("SELECT * FROM customers ORDER BY name");
             ResultSet resultSet = statement.executeQuery()) {
            while (resultSet.next()) {
                customers.add(map(resultSet));
            }
        }
        return customers;
    }

    private Customer map(ResultSet resultSet) throws Exception {
        CustomerType type = CustomerType.valueOf(resultSet.getString("type"));
        if (type == CustomerType.VIP) {
            return new VIPCustomer(resultSet.getInt("id"), resultSet.getString("name"), resultSet.getString("phone"));
        }
        return new Customer(resultSet.getInt("id"), resultSet.getString("name"), resultSet.getString("phone"), type);
    }
}

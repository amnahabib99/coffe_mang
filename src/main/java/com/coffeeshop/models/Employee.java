package com.coffeeshop.models;

import com.coffeeshop.enums.UserRole;
import com.coffeeshop.enums.UserStatus;
import com.coffeeshop.exceptions.InvalidInputException;

/**
 * Employee user focused on order and invoice work.
 */
public class Employee extends User {
    /**
     * Creates an employee.
     *
     * @throws InvalidInputException when required data is invalid
     */
    public Employee(int id, String name, String username, String password, String phone,
                    String securityQuestion, String securityAnswer, UserStatus status) throws InvalidInputException {
        super(id, name, username, password, phone, UserRole.EMPLOYEE, securityQuestion, securityAnswer, status);
    }

    @Override
    public String getRoleDescription() {
        return "Employee: creates orders and views invoices.";
    }
}

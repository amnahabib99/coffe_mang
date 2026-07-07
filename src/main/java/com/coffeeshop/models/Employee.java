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
     * @param id employee id
     * @param name employee name
     * @param username login username
     * @param password login password
     * @param phone phone number
     * @param securityQuestion password recovery question
     * @param securityAnswer password recovery answer
     * @param status account status
     * @throws InvalidInputException when required data is invalid
     */
    public Employee(int id, String name, String username, String password, String phone,
                    String securityQuestion, String securityAnswer, UserStatus status) throws InvalidInputException {
        super(id, name, username, password, phone, UserRole.EMPLOYEE, securityQuestion, securityAnswer, status);
    }

    @Override
    public String getRoleDescription() {
        return "موظف: ينشئ الطلبات ويعرض الفواتير.";
    }
}

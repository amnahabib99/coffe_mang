package com.coffeeshop.models;

import com.coffeeshop.enums.UserRole;
import com.coffeeshop.enums.UserStatus;
import com.coffeeshop.exceptions.InvalidInputException;

/**
 * Manager user with full administrative access.
 */
public class Manager extends User {
    /**
     * Creates a manager.
     *
     * @throws InvalidInputException when required data is invalid
     */
    public Manager(int id, String name, String username, String password, String phone,
                   String securityQuestion, String securityAnswer, UserStatus status) throws InvalidInputException {
        super(id, name, username, password, phone, UserRole.MANAGER, securityQuestion, securityAnswer, status);
    }

    @Override
    public String getRoleDescription() {
        return "Manager: manages users, categories, products, reports, and sales.";
    }
}

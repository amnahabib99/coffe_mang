package com.coffeeshop.interfaces;

import com.coffeeshop.exceptions.AuthenticationException;
import com.coffeeshop.models.User;

/**
 * Defines authentication behavior for login-capable services.
 */
public interface Authenticatable {
    /**
     * Logs a user into the system.
     *
     * @param username username
     * @param password password
     * @return authenticated user
     * @throws AuthenticationException when credentials are invalid
     */
    User login(String username, String password) throws AuthenticationException;
}

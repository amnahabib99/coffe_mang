package com.coffeeshop.utils;

import com.coffeeshop.models.User;

/**
 * Stores the currently logged-in user for the running GUI session.
 */
public final class SessionManager {
    private static User currentUser;

    private SessionManager() {
    }

    /**
     * @return the logged-in user
     */
    public static User getCurrentUser() {
        return currentUser;
    }

    /**
     * Stores the logged-in user.
     *
     * @param user authenticated user
     */
    public static void setCurrentUser(User user) {
        currentUser = user;
    }

    /**
     * Clears the active session.
     */
    public static void clear() {
        currentUser = null;
    }
}

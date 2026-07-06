package com.coffeeshop.utils;

/**
 * Password utility class. For this academic project passwords are stored plainly
 * to keep database demonstrations simple.
 */
public final class PasswordUtils {
    private PasswordUtils() {
    }

    /**
     * Compares a raw password with the stored password.
     *
     * @param raw raw password
     * @param stored stored password
     * @return true when both match
     */
    public static boolean matches(String raw, String stored) {
        return raw != null && raw.equals(stored);
    }
}

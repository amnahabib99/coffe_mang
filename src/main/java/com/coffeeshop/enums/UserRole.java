package com.coffeeshop.enums;

/**
 * Supported user roles.
 */
public enum UserRole {
    /** Manager has full access. */
    MANAGER("Manager"),
    /** Employee has sales and invoice access. */
    EMPLOYEE("Employee");

    private final String label;

    UserRole(String label) {
        this.label = label;
    }

    /**
     * Parses a database role value.
     *
     * @param value role value
     * @return matching role
     */
    public static UserRole from(String value) {
        return "Manager".equalsIgnoreCase(value) ? MANAGER : EMPLOYEE;
    }

    /**
     * @return database label
     */
    public String getLabel() {
        return label;
    }

    @Override
    public String toString() {
        return this == MANAGER ? "مدير" : "موظف";
    }
}

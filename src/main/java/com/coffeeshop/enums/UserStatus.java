package com.coffeeshop.enums;

/**
 * User account status.
 */
public enum UserStatus {
    /** User can log in. */
    ACTIVE,
    /** User cannot log in. */
    INACTIVE,
    /** User awaits manager verification. */
    PENDING;

    @Override
    public String toString() {
        return switch (this) {
            case ACTIVE -> "مفعل";
            case INACTIVE -> "موقوف";
            case PENDING -> "بانتظار التحقق";
        };
    }
}

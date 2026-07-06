package com.coffeeshop.database;

/**
 * Central MySQL connection configuration.
 */
public final class DatabaseConfig {
    /** MySQL host. */
    public static final String HOST = "localhost";
    /** MySQL port. */
    public static final String PORT = "3306";
    /** Application database name. */
    public static final String DATABASE = "coffee_shop_db";
    /** MySQL username. */
    public static final String USERNAME = "root";
    /**
     * MySQL password. Prefer setting it locally with the COFFEE_DB_PASSWORD
     * environment variable so real passwords are not committed to GitHub.
     */
    public static final String PASSWORD = getPassword();
    /** Server URL used to create the database if needed. */
    public static final String SERVER_URL = "jdbc:mysql://" + HOST + ":" + PORT
            + "/?useSSL=false&allowPublicKeyRetrieval=true&serverTimezone=UTC";
    /** Database URL used by repositories. */
    public static final String DATABASE_URL = "jdbc:mysql://" + HOST + ":" + PORT + "/" + DATABASE
            + "?useSSL=false&allowPublicKeyRetrieval=true&serverTimezone=UTC";

    private DatabaseConfig() {
    }

    private static String getPassword() {
        String fromProperty = System.getProperty("coffee.db.password");
        if (fromProperty != null && !fromProperty.isBlank()) {
            return fromProperty;
        }
        String fromEnvironment = System.getenv("COFFEE_DB_PASSWORD");
        return fromEnvironment == null ? "" : fromEnvironment;
    }
}

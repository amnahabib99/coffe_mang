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
    /** MySQL password, empty by default for local demos. */
    public static final String PASSWORD = "";
    /** Server URL used to create the database if needed. */
    public static final String SERVER_URL = "jdbc:mysql://" + HOST + ":" + PORT
            + "/?useSSL=false&allowPublicKeyRetrieval=true&serverTimezone=UTC";
    /** Database URL used by repositories. */
    public static final String DATABASE_URL = "jdbc:mysql://" + HOST + ":" + PORT + "/" + DATABASE
            + "?useSSL=false&allowPublicKeyRetrieval=true&serverTimezone=UTC";

    private DatabaseConfig() {
    }
}

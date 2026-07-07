package com.coffeeshop.database;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Properties;

/**
 * Stores the MySQL connection settings used by the coffee shop application.
 * The password can be supplied through a JVM property, an environment variable,
 * or the local ignored file {@code .local-db.properties}.
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
     * MySQL password resolved at startup without committing private credentials.
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

    /**
     * Resolves the database password from supported local configuration sources.
     *
     * @return the configured MySQL password, or an empty string when no password is configured
     */
    private static String getPassword() {
        String fromProperty = System.getProperty("coffee.db.password");
        if (fromProperty != null && !fromProperty.isBlank()) {
            return fromProperty;
        }
        String fromEnvironment = System.getenv("COFFEE_DB_PASSWORD");
        if (fromEnvironment != null && !fromEnvironment.isBlank()) {
            return fromEnvironment;
        }
        return readLocalPassword();
    }

    /**
     * Reads the optional ignored local database configuration file.
     *
     * @return password from {@code .local-db.properties}, or an empty string when unavailable
     */
    private static String readLocalPassword() {
        Path localFile = Path.of(".local-db.properties");
        if (!Files.exists(localFile)) {
            return "";
        }
        Properties properties = new Properties();
        try (InputStream inputStream = Files.newInputStream(localFile)) {
            properties.load(inputStream);
            return properties.getProperty("db.password", "");
        } catch (IOException ex) {
            return "";
        }
    }
}

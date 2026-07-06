package com.coffeeshop.database;

import com.coffeeshop.exceptions.DatabaseConnectionException;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * Provides JDBC connections to MySQL.
 */
public final class DatabaseManager {
    private DatabaseManager() {
    }

    /**
     * Opens a connection to the application database.
     *
     * @return JDBC connection
     * @throws DatabaseConnectionException when MySQL is unavailable or credentials are wrong
     */
    public static Connection getConnection() throws DatabaseConnectionException {
        try {
            return DriverManager.getConnection(DatabaseConfig.DATABASE_URL, DatabaseConfig.USERNAME, DatabaseConfig.PASSWORD);
        } catch (SQLException ex) {
            throw new DatabaseConnectionException("Cannot connect to MySQL. Check that MySQL is running and credentials are correct.", ex);
        }
    }

    /**
     * Opens a server-level connection used for database creation.
     *
     * @return JDBC connection
     * @throws DatabaseConnectionException when MySQL is unavailable
     */
    static Connection getServerConnection() throws DatabaseConnectionException {
        try {
            return DriverManager.getConnection(DatabaseConfig.SERVER_URL, DatabaseConfig.USERNAME, DatabaseConfig.PASSWORD);
        } catch (SQLException ex) {
            throw new DatabaseConnectionException("Cannot connect to MySQL server. Start MySQL or update DatabaseConfig.", ex);
        }
    }
}

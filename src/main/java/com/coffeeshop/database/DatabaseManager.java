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
            throw new DatabaseConnectionException("تعذر الاتصال بقاعدة بيانات MySQL. تأكد من تشغيل MySQL وصحة بيانات الدخول.", ex);
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
            throw new DatabaseConnectionException("تعذر الاتصال بخادم MySQL. شغل MySQL أو راجع إعدادات الاتصال.", ex);
        }
    }
}

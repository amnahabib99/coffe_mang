package com.coffeeshop.database;

import com.coffeeshop.exceptions.DatabaseConnectionException;
import com.coffeeshop.utils.AppConstants;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * Creates the MySQL database, tables, and seed data required by the system.
 */
public final class SchemaInitializer {
    private SchemaInitializer() {
    }

    /**
     * Initializes schema and sample data.
     *
     * @throws DatabaseConnectionException when initialization fails
     */
    public static void initialize() throws DatabaseConnectionException {
        createDatabase();
        createTables();
        seedData();
    }

    private static void createDatabase() throws DatabaseConnectionException {
        try (Connection connection = DatabaseManager.getServerConnection();
             Statement statement = connection.createStatement()) {
            statement.executeUpdate("CREATE DATABASE IF NOT EXISTS " + DatabaseConfig.DATABASE);
        } catch (SQLException ex) {
            throw new DatabaseConnectionException("Could not create database " + DatabaseConfig.DATABASE + ".", ex);
        }
    }

    private static void createTables() throws DatabaseConnectionException {
        try (Connection connection = DatabaseManager.getConnection();
             Statement statement = connection.createStatement()) {
            statement.executeUpdate("""
                    CREATE TABLE IF NOT EXISTS users (
                        id INT AUTO_INCREMENT PRIMARY KEY,
                        name VARCHAR(100) NOT NULL,
                        username VARCHAR(50) NOT NULL UNIQUE,
                        password VARCHAR(100) NOT NULL,
                        phone VARCHAR(30),
                        role VARCHAR(30) NOT NULL,
                        security_question VARCHAR(255),
                        security_answer VARCHAR(255),
                        status VARCHAR(30) NOT NULL DEFAULT 'ACTIVE',
                        created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
                    )
                    """);
            statement.executeUpdate("""
                    CREATE TABLE IF NOT EXISTS categories (
                        id INT AUTO_INCREMENT PRIMARY KEY,
                        name VARCHAR(100) NOT NULL UNIQUE,
                        description VARCHAR(255)
                    )
                    """);
            statement.executeUpdate("""
                    CREATE TABLE IF NOT EXISTS products (
                        id INT AUTO_INCREMENT PRIMARY KEY,
                        name VARCHAR(100) NOT NULL,
                        category_id INT NOT NULL,
                        price DECIMAL(10,2) NOT NULL,
                        size VARCHAR(30),
                        status VARCHAR(30) NOT NULL DEFAULT 'AVAILABLE',
                        FOREIGN KEY (category_id) REFERENCES categories(id)
                    )
                    """);
            statement.executeUpdate("""
                    CREATE TABLE IF NOT EXISTS customers (
                        id INT AUTO_INCREMENT PRIMARY KEY,
                        name VARCHAR(100),
                        phone VARCHAR(30),
                        type VARCHAR(30) DEFAULT 'REGULAR'
                    )
                    """);
            statement.executeUpdate("""
                    CREATE TABLE IF NOT EXISTS orders (
                        id INT AUTO_INCREMENT PRIMARY KEY,
                        customer_id INT NULL,
                        cashier_id INT NOT NULL,
                        subtotal DECIMAL(10,2) NOT NULL,
                        tax DECIMAL(10,2) NOT NULL,
                        discount DECIMAL(10,2) NOT NULL,
                        total DECIMAL(10,2) NOT NULL,
                        status VARCHAR(30) NOT NULL DEFAULT 'COMPLETED',
                        created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
                        FOREIGN KEY (customer_id) REFERENCES customers(id),
                        FOREIGN KEY (cashier_id) REFERENCES users(id)
                    )
                    """);
            statement.executeUpdate("""
                    CREATE TABLE IF NOT EXISTS order_items (
                        id INT AUTO_INCREMENT PRIMARY KEY,
                        order_id INT NOT NULL,
                        product_id INT NOT NULL,
                        product_name VARCHAR(100) NOT NULL,
                        quantity INT NOT NULL,
                        unit_price DECIMAL(10,2) NOT NULL,
                        line_total DECIMAL(10,2) NOT NULL,
                        FOREIGN KEY (order_id) REFERENCES orders(id),
                        FOREIGN KEY (product_id) REFERENCES products(id)
                    )
                    """);
        } catch (SQLException ex) {
            throw new DatabaseConnectionException("Could not create required tables.", ex);
        }
    }

    private static void seedData() throws DatabaseConnectionException {
        try (Connection connection = DatabaseManager.getConnection();
             Statement statement = connection.createStatement()) {
            if (isEmpty(statement, "users")) {
                statement.executeUpdate("INSERT INTO users(name, username, password, phone, role, security_question, security_answer, status) "
                        + "VALUES ('Default Manager', '" + AppConstants.DEFAULT_ADMIN_USERNAME + "', '"
                        + AppConstants.DEFAULT_ADMIN_PASSWORD + "', '000', 'Manager', 'Default question?', 'admin', 'ACTIVE')");
            }
            if (isEmpty(statement, "categories")) {
                statement.executeUpdate("INSERT INTO categories(name, description) VALUES "
                        + "('Coffee','Hot and cold coffee drinks'),"
                        + "('Tea','Tea drinks'),"
                        + "('Dessert','Cakes and sweets'),"
                        + "('Juice','Fresh juices')");
            }
            if (isEmpty(statement, "products")) {
                statement.executeUpdate("""
                        INSERT INTO products(name, category_id, price, size, status)
                        SELECT 'Espresso', id, 2.50, 'Small', 'AVAILABLE' FROM categories WHERE name='Coffee'
                        """);
                statement.executeUpdate("""
                        INSERT INTO products(name, category_id, price, size, status)
                        SELECT 'Latte', id, 4.00, 'Medium', 'AVAILABLE' FROM categories WHERE name='Coffee'
                        """);
                statement.executeUpdate("""
                        INSERT INTO products(name, category_id, price, size, status)
                        SELECT 'Green Tea', id, 2.00, 'Cup', 'AVAILABLE' FROM categories WHERE name='Tea'
                        """);
                statement.executeUpdate("""
                        INSERT INTO products(name, category_id, price, size, status)
                        SELECT 'Cheesecake', id, 4.50, 'Slice', 'AVAILABLE' FROM categories WHERE name='Dessert'
                        """);
            }
        } catch (SQLException ex) {
            throw new DatabaseConnectionException("Could not insert seed data.", ex);
        }
    }

    private static boolean isEmpty(Statement statement, String table) throws SQLException {
        try (ResultSet resultSet = statement.executeQuery("SELECT COUNT(*) FROM " + table)) {
            resultSet.next();
            return resultSet.getInt(1) == 0;
        }
    }
}

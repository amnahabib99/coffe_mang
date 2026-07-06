package com.coffeeshop.repositories;

import com.coffeeshop.database.DatabaseManager;
import com.coffeeshop.models.Order;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

/**
 * Database repository for orders and order items.
 */
public class OrderRepository {
    private final UserRepository userRepository = new UserRepository();

    /**
     * Saves a completed order and all order items.
     *
     * @param order order
     * @return saved order
     * @throws Exception when saving fails
     */
    public Order save(Order order) throws Exception {
        String sql = "INSERT INTO orders(customer_id, cashier_id, subtotal, tax, discount, total, status) VALUES(?,?,?,?,?,?,?)";
        try (Connection connection = DatabaseManager.getConnection()) {
            connection.setAutoCommit(false);
            try (PreparedStatement statement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
                if (order.getCustomer() == null || order.getCustomer().getId() == 0) {
                    statement.setNull(1, java.sql.Types.INTEGER);
                } else {
                    statement.setInt(1, order.getCustomer().getId());
                }
                statement.setInt(2, order.getCashier().getId());
                statement.setDouble(3, order.getSubtotal());
                statement.setDouble(4, order.getTax());
                statement.setDouble(5, order.getDiscount());
                statement.setDouble(6, order.getTotal());
                statement.setString(7, order.getStatus());
                statement.executeUpdate();
                try (ResultSet keys = statement.getGeneratedKeys()) {
                    if (keys.next()) {
                        order.setId(keys.getInt(1));
                    }
                }
            }
            saveItems(connection, order);
            connection.commit();
            return order;
        }
    }

    /**
     * Loads all orders with their items.
     *
     * @return completed orders
     * @throws Exception when loading fails
     */
    public List<Order> findAll() throws Exception {
        List<Order> orders = new ArrayList<>();
        String sql = "SELECT * FROM orders ORDER BY created_at DESC";
        try (Connection connection = DatabaseManager.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql);
             ResultSet resultSet = statement.executeQuery()) {
            while (resultSet.next()) {
                Order order = new Order(resultSet.getInt("id"), null,
                        userRepository.findUser(resultSet.getInt("cashier_id")),
                        resultSet.getTimestamp("created_at").toLocalDateTime());
                order.setSubtotal(resultSet.getDouble("subtotal"));
                order.setTax(resultSet.getDouble("tax"));
                order.setDiscount(resultSet.getDouble("discount"));
                order.setTotal(resultSet.getDouble("total"));
                loadItems(connection, order);
                orders.add(order);
            }
        }
        return orders;
    }

    private void saveItems(Connection connection, Order order) throws Exception {
        String sql = "INSERT INTO order_items(order_id, product_id, product_name, quantity, unit_price, line_total) VALUES(?,?,?,?,?,?)";
        try (PreparedStatement statement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            for (Order.OrderItem item : order.getItems()) {
                statement.setInt(1, order.getId());
                statement.setInt(2, item.getProductId());
                statement.setString(3, item.getProductName());
                statement.setInt(4, item.getQuantity());
                statement.setDouble(5, item.getUnitPrice());
                statement.setDouble(6, item.getLineTotal());
                statement.executeUpdate();
                try (ResultSet keys = statement.getGeneratedKeys()) {
                    if (keys.next()) {
                        item.setId(keys.getInt(1));
                    }
                }
            }
        }
    }

    private void loadItems(Connection connection, Order order) throws Exception {
        String sql = "SELECT * FROM order_items WHERE order_id=?";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, order.getId());
            try (ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    Order.OrderItem item = order.new OrderItem(resultSet.getInt("product_id"),
                            resultSet.getString("product_name"), resultSet.getInt("quantity"),
                            resultSet.getDouble("unit_price"));
                    item.setId(resultSet.getInt("id"));
                    order.addLoadedItem(item);
                }
            }
        }
    }
}

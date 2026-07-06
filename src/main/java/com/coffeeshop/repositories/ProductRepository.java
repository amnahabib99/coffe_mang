package com.coffeeshop.repositories;

import com.coffeeshop.database.DatabaseManager;
import com.coffeeshop.enums.ProductStatus;
import com.coffeeshop.interfaces.Repository;
import com.coffeeshop.models.Category;
import com.coffeeshop.models.Product;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * Database repository for products.
 */
public class ProductRepository implements Repository<Product> {
    @Override
    public Product save(Product product) throws Exception {
        String sql = "INSERT INTO products(name, category_id, price, size, status) VALUES(?,?,?,?,?)";
        try (Connection connection = DatabaseManager.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            fill(statement, product);
            statement.executeUpdate();
            try (ResultSet keys = statement.getGeneratedKeys()) {
                if (keys.next()) {
                    product.setId(keys.getInt(1));
                }
            }
            return product;
        }
    }

    @Override
    public void update(Product product) throws Exception {
        String sql = "UPDATE products SET name=?, category_id=?, price=?, size=?, status=? WHERE id=?";
        try (Connection connection = DatabaseManager.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {
            fill(statement, product);
            statement.setInt(6, product.getId());
            statement.executeUpdate();
        }
    }

    @Override
    public void delete(int id) throws Exception {
        try (Connection connection = DatabaseManager.getConnection();
             PreparedStatement statement = connection.prepareStatement("DELETE FROM products WHERE id=?")) {
            statement.setInt(1, id);
            statement.executeUpdate();
        }
    }

    @Override
    public Optional<Product> findById(int id) throws Exception {
        try (Connection connection = DatabaseManager.getConnection();
             PreparedStatement statement = connection.prepareStatement(productQuery() + " WHERE p.id=?")) {
            statement.setInt(1, id);
            try (ResultSet resultSet = statement.executeQuery()) {
                return resultSet.next() ? Optional.of(map(resultSet)) : Optional.empty();
            }
        }
    }

    @Override
    public List<Product> findAll() throws Exception {
        List<Product> products = new ArrayList<>();
        try (Connection connection = DatabaseManager.getConnection();
             PreparedStatement statement = connection.prepareStatement(productQuery() + " ORDER BY p.name");
             ResultSet resultSet = statement.executeQuery()) {
            while (resultSet.next()) {
                products.add(map(resultSet));
            }
        }
        return products;
    }

    /**
     * Finds products by category.
     *
     * @param categoryId category id
     * @return products
     * @throws Exception when query fails
     */
    public List<Product> findByCategory(int categoryId) throws Exception {
        List<Product> products = new ArrayList<>();
        try (Connection connection = DatabaseManager.getConnection();
             PreparedStatement statement = connection.prepareStatement(productQuery() + " WHERE c.id=? ORDER BY p.name")) {
            statement.setInt(1, categoryId);
            try (ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    products.add(map(resultSet));
                }
            }
        }
        return products;
    }

    private void fill(PreparedStatement statement, Product product) throws Exception {
        statement.setString(1, product.getName());
        statement.setInt(2, product.getCategory().getId());
        statement.setDouble(3, product.getPrice());
        statement.setString(4, product.getSize());
        statement.setString(5, product.getStatus().name());
    }

    private String productQuery() {
        return "SELECT p.*, c.name category_name, c.description category_description FROM products p "
                + "JOIN categories c ON p.category_id=c.id";
    }

    private Product map(ResultSet resultSet) throws Exception {
        Category category = new Category(resultSet.getInt("category_id"), resultSet.getString("category_name"),
                resultSet.getString("category_description"));
        return new Product(resultSet.getInt("id"), resultSet.getString("name"), category,
                resultSet.getDouble("price"), resultSet.getString("size"), ProductStatus.valueOf(resultSet.getString("status")));
    }
}

package com.coffeeshop.repositories;

import com.coffeeshop.database.DatabaseManager;
import com.coffeeshop.enums.UserRole;
import com.coffeeshop.enums.UserStatus;
import com.coffeeshop.interfaces.Repository;
import com.coffeeshop.models.Employee;
import com.coffeeshop.models.Manager;
import com.coffeeshop.models.User;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * Database repository for users.
 */
public class UserRepository implements Repository<User> {
    @Override
    public User save(User user) throws Exception {
        String sql = "INSERT INTO users(name, username, password, phone, role, security_question, security_answer, status) VALUES(?,?,?,?,?,?,?,?)";
        try (Connection connection = DatabaseManager.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            statement.setString(1, user.getName());
            statement.setString(2, user.getUsername());
            statement.setString(3, user.getPassword());
            statement.setString(4, user.getPhone());
            statement.setString(5, user.getRole().getLabel());
            statement.setString(6, user.getSecurityQuestion());
            statement.setString(7, user.getSecurityAnswer());
            statement.setString(8, user.getStatus().name());
            statement.executeUpdate();
            try (ResultSet keys = statement.getGeneratedKeys()) {
                if (keys.next()) {
                    user.setId(keys.getInt(1));
                }
            }
            return user;
        }
    }

    @Override
    public void update(User user) throws Exception {
        String sql = "UPDATE users SET name=?, username=?, password=?, phone=?, role=?, security_question=?, security_answer=?, status=? WHERE id=?";
        try (Connection connection = DatabaseManager.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, user.getName());
            statement.setString(2, user.getUsername());
            statement.setString(3, user.getPassword());
            statement.setString(4, user.getPhone());
            statement.setString(5, user.getRole().getLabel());
            statement.setString(6, user.getSecurityQuestion());
            statement.setString(7, user.getSecurityAnswer());
            statement.setString(8, user.getStatus().name());
            statement.setInt(9, user.getId());
            statement.executeUpdate();
        }
    }

    @Override
    public void delete(int id) throws Exception {
        try (Connection connection = DatabaseManager.getConnection();
             PreparedStatement statement = connection.prepareStatement("DELETE FROM users WHERE id=?")) {
            statement.setInt(1, id);
            statement.executeUpdate();
        }
    }

    @Override
    public Optional<User> findById(int id) throws Exception {
        try (Connection connection = DatabaseManager.getConnection();
             PreparedStatement statement = connection.prepareStatement("SELECT * FROM users WHERE id=?")) {
            statement.setInt(1, id);
            try (ResultSet resultSet = statement.executeQuery()) {
                return resultSet.next() ? Optional.of(map(resultSet)) : Optional.empty();
            }
        }
    }

    /**
     * Overloaded finder by id.
     *
     * @param id user id
     * @return user
     * @throws Exception when user is missing
     */
    public User findUser(int id) throws Exception {
        return findById(id).orElseThrow(() -> new IllegalArgumentException("User not found."));
    }

    /**
     * Overloaded finder by username.
     *
     * @param username username
     * @return optional user
     * @throws Exception when query fails
     */
    public Optional<User> findUser(String username) throws Exception {
        return findByUsername(username);
    }

    /**
     * Finds a user by username.
     *
     * @param username username
     * @return optional user
     * @throws Exception when query fails
     */
    public Optional<User> findByUsername(String username) throws Exception {
        try (Connection connection = DatabaseManager.getConnection();
             PreparedStatement statement = connection.prepareStatement("SELECT * FROM users WHERE username=?")) {
            statement.setString(1, username);
            try (ResultSet resultSet = statement.executeQuery()) {
                return resultSet.next() ? Optional.of(map(resultSet)) : Optional.empty();
            }
        }
    }

    @Override
    public List<User> findAll() throws Exception {
        List<User> users = new ArrayList<>();
        try (Connection connection = DatabaseManager.getConnection();
             PreparedStatement statement = connection.prepareStatement("SELECT * FROM users ORDER BY id");
             ResultSet resultSet = statement.executeQuery()) {
            while (resultSet.next()) {
                users.add(map(resultSet));
            }
        }
        return users;
    }

    private User map(ResultSet resultSet) throws Exception {
        UserRole role = UserRole.from(resultSet.getString("role"));
        UserStatus status = UserStatus.valueOf(resultSet.getString("status"));
        if (role == UserRole.MANAGER) {
            return new Manager(resultSet.getInt("id"), resultSet.getString("name"), resultSet.getString("username"),
                    resultSet.getString("password"), resultSet.getString("phone"), resultSet.getString("security_question"),
                    resultSet.getString("security_answer"), status);
        }
        return new Employee(resultSet.getInt("id"), resultSet.getString("name"), resultSet.getString("username"),
                resultSet.getString("password"), resultSet.getString("phone"), resultSet.getString("security_question"),
                resultSet.getString("security_answer"), status);
    }
}

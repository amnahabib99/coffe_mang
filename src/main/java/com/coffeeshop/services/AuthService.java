package com.coffeeshop.services;

import com.coffeeshop.enums.UserRole;
import com.coffeeshop.enums.UserStatus;
import com.coffeeshop.exceptions.AuthenticationException;
import com.coffeeshop.exceptions.InvalidInputException;
import com.coffeeshop.interfaces.Authenticatable;
import com.coffeeshop.models.User;
import com.coffeeshop.repositories.UserRepository;
import com.coffeeshop.utils.PasswordUtils;
import com.coffeeshop.utils.SessionManager;
import com.coffeeshop.utils.ValidationUtils;

/**
 * Handles registration, login, and account recovery workflows.
 */
public class AuthService implements Authenticatable {
    private final UserRepository userRepository = new UserRepository();

    @Override
    public User login(String username, String password) throws AuthenticationException {
        try {
            ValidationUtils.requireText(username, "Username");
            ValidationUtils.requireText(password, "Password");
            User user = userRepository.findByUsername(username)
                    .orElseThrow(() -> new AuthenticationException("Invalid username or password."));
            if (!PasswordUtils.matches(password, user.getPassword())) {
                throw new AuthenticationException("Invalid username or password.");
            }
            if (user.getStatus() != UserStatus.ACTIVE) {
                throw new AuthenticationException("Your account is not active. Ask a manager to verify it.");
            }
            SessionManager.setCurrentUser(user);
            return user;
        } catch (AuthenticationException ex) {
            throw ex;
        } catch (Exception ex) {
            throw new AuthenticationException(ex.getMessage());
        }
    }

    /**
     * Registers a new user.
     *
     * @return saved user
     * @throws Exception when validation or database save fails
     */
    public User register(String name, String username, String password, String phone, UserRole role,
                         String question, String answer, UserStatus status) throws Exception {
        ValidationUtils.requireText(username, "Username");
        ValidationUtils.requireText(password, "Password");
        if (userRepository.findByUsername(username).isPresent()) {
            throw new InvalidInputException("Username already exists.");
        }
        User user = new User(name, username, password, phone, role, question, answer);
        user.setStatus(status == null ? UserStatus.PENDING : status);
        return userRepository.save(user);
    }

    /**
     * Returns the security question for a username.
     *
     * @param username username
     * @return question
     * @throws Exception when user is not found
     */
    public String getSecurityQuestion(String username) throws Exception {
        return userRepository.findByUsername(username)
                .orElseThrow(() -> new AuthenticationException("Username was not found."))
                .getSecurityQuestion();
    }

    /**
     * Resets password using the security answer.
     *
     * @throws Exception when answer is wrong
     */
    public void resetPassword(String username, String answer, String newPassword) throws Exception {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new AuthenticationException("Username was not found."));
        ValidationUtils.requireText(newPassword, "New password");
        if (!user.getSecurityAnswer().equalsIgnoreCase(answer == null ? "" : answer.trim())) {
            throw new AuthenticationException("Security answer is incorrect.");
        }
        user.setPassword(newPassword);
        userRepository.update(user);
    }
}

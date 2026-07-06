package com.coffeeshop.services;

import com.coffeeshop.enums.UserRole;
import com.coffeeshop.enums.UserStatus;
import com.coffeeshop.exceptions.AuthenticationException;
import com.coffeeshop.exceptions.EntityNotFoundException;
import com.coffeeshop.exceptions.UnauthorizedActionException;
import com.coffeeshop.models.User;
import com.coffeeshop.repositories.UserRepository;
import com.coffeeshop.utils.PasswordUtils;
import com.coffeeshop.utils.SessionManager;
import com.coffeeshop.utils.ValidationUtils;

import java.util.List;

/**
 * Business service for account settings and manager user management.
 */
public class UserService {
    private final UserRepository userRepository = new UserRepository();

    /**
     * @return all users, manager-only
     * @throws Exception when unauthorized or loading fails
     */
    public List<User> getAllUsers() throws Exception {
        requireManager();
        return userRepository.findAll();
    }

    /**
     * Updates account status.
     *
     * @param id user id
     * @param status new status
     * @throws Exception when unauthorized or missing
     */
    public void updateStatus(int id, UserStatus status) throws Exception {
        requireManager();
        User user = userRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("User not found."));
        user.setStatus(status);
        userRepository.update(user);
    }

    /**
     * Updates user role.
     *
     * @param id user id
     * @param role new role
     * @throws Exception when unauthorized or missing
     */
    public void updateRole(int id, UserRole role) throws Exception {
        requireManager();
        User user = userRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("User not found."));
        user.setRole(role);
        userRepository.update(user);
    }

    /**
     * Changes the current user's password.
     *
     * @throws Exception when old password is wrong
     */
    public void changePassword(User user, String oldPassword, String newPassword, String confirmPassword) throws Exception {
        if (!PasswordUtils.matches(oldPassword, user.getPassword())) {
            throw new AuthenticationException("Old password is incorrect.");
        }
        if (!newPassword.equals(confirmPassword)) {
            throw new AuthenticationException("New password and confirmation do not match.");
        }
        ValidationUtils.requireText(newPassword, "New password");
        user.setPassword(newPassword);
        userRepository.update(user);
    }

    /**
     * Changes the current user's security question and answer.
     *
     * @throws Exception when password is wrong
     */
    public void changeSecurityQuestion(User user, String currentPassword, String question, String answer) throws Exception {
        if (!PasswordUtils.matches(currentPassword, user.getPassword())) {
            throw new AuthenticationException("Current password is incorrect.");
        }
        ValidationUtils.requireText(question, "Security question");
        ValidationUtils.requireText(answer, "Security answer");
        user.setSecurityQuestion(question);
        user.setSecurityAnswer(answer);
        userRepository.update(user);
    }

    private void requireManager() throws UnauthorizedActionException {
        User current = SessionManager.getCurrentUser();
        if (current == null || current.getRole() != UserRole.MANAGER) {
            throw new UnauthorizedActionException("Only managers can access this feature.");
        }
    }
}

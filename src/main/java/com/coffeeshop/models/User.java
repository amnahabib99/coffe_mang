package com.coffeeshop.models;

import com.coffeeshop.abstracts.Person;
import com.coffeeshop.enums.UserRole;
import com.coffeeshop.enums.UserStatus;
import com.coffeeshop.exceptions.InvalidInputException;
import com.coffeeshop.utils.ValidationUtils;

/**
 * Represents a system user who can authenticate.
 */
public class User extends Person {
    private String username;
    private String password;
    private UserRole role;
    private String securityQuestion;
    private String securityAnswer;
    private UserStatus status;

    /**
     * Creates a user without id.
     *
     * @param name name
     * @param username username
     * @param password password
     * @param phone phone
     * @param role user role
     * @param securityQuestion security question
     * @param securityAnswer security answer
     * @throws InvalidInputException when required data is invalid
     */
    public User(String name, String username, String password, String phone, UserRole role,
                String securityQuestion, String securityAnswer) throws InvalidInputException {
        this(0, name, username, password, phone, role, securityQuestion, securityAnswer, UserStatus.ACTIVE);
    }

    /**
     * Creates a user with full database data.
     *
     * @param id id
     * @param name name
     * @param username username
     * @param password password
     * @param phone phone
     * @param role role
     * @param securityQuestion security question
     * @param securityAnswer security answer
     * @param status account status
     * @throws InvalidInputException when required data is invalid
     */
    public User(int id, String name, String username, String password, String phone, UserRole role,
                String securityQuestion, String securityAnswer, UserStatus status) throws InvalidInputException {
        super(id, name, phone);
        setUsername(username);
        setPassword(password);
        this.role = role;
        this.securityQuestion = securityQuestion == null ? "" : securityQuestion.trim();
        this.securityAnswer = securityAnswer == null ? "" : securityAnswer.trim();
        this.status = status == null ? UserStatus.PENDING : status;
    }

    @Override
    public String getRoleDescription() {
        return role == UserRole.MANAGER ? "مدير: صلاحية كاملة لإدارة المقهى" : "موظف: مسؤول عن المبيعات والفواتير";
    }

    @Override
    public String toString() {
        return getName() + " (" + role + ")";
    }

    public String getUsername() {
        return username;
    }

    public final void setUsername(String username) throws InvalidInputException {
        ValidationUtils.requireText(username, "اسم المستخدم");
        this.username = username.trim();
    }

    public String getPassword() {
        return password;
    }

    public final void setPassword(String password) throws InvalidInputException {
        ValidationUtils.requireText(password, "كلمة المرور");
        this.password = password;
    }

    public UserRole getRole() {
        return role;
    }

    public void setRole(UserRole role) {
        this.role = role;
    }

    public String getSecurityQuestion() {
        return securityQuestion;
    }

    public void setSecurityQuestion(String securityQuestion) {
        this.securityQuestion = securityQuestion == null ? "" : securityQuestion.trim();
    }

    public String getSecurityAnswer() {
        return securityAnswer;
    }

    public void setSecurityAnswer(String securityAnswer) {
        this.securityAnswer = securityAnswer == null ? "" : securityAnswer.trim();
    }

    public UserStatus getStatus() {
        return status;
    }

    public void setStatus(UserStatus status) {
        this.status = status;
    }
}

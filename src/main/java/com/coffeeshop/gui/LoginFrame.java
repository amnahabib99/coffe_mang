package com.coffeeshop.gui;

import com.coffeeshop.models.User;
import com.coffeeshop.services.AuthService;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import java.awt.BorderLayout;
import java.awt.GridLayout;

/**
 * First application screen where users log in.
 */
public class LoginFrame extends JFrame {
    private final JTextField usernameField = new JTextField();
    private final JPasswordField passwordField = new JPasswordField();
    private final AuthService authService = new AuthService();

    /**
     * Creates the login window.
     */
    public LoginFrame() {
        setTitle("Coffee Shop Login");
        setSize(420, 230);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        buildUi();
    }

    /**
     * Shows a startup database error while still allowing the login screen to open.
     *
     * @param message error message
     */
    public static void showStartupError(String message) {
        JOptionPane.showMessageDialog(null, message, "Database Startup Warning", JOptionPane.WARNING_MESSAGE);
    }

    private void buildUi() {
        JPanel form = new JPanel(new GridLayout(3, 2, 8, 8));
        form.add(new JLabel("Username"));
        form.add(usernameField);
        form.add(new JLabel("Password"));
        form.add(passwordField);

        JButton loginButton = new JButton("Login");
        JButton registerButton = new JButton("Register");
        JButton forgotButton = new JButton("Forgot Password");
        form.add(loginButton);
        form.add(registerButton);

        add(form, BorderLayout.CENTER);
        add(forgotButton, BorderLayout.SOUTH);

        loginButton.addActionListener(event -> login());
        registerButton.addActionListener(event -> new RegisterDialog(this).setVisible(true));
        forgotButton.addActionListener(event -> new ForgotPasswordDialog(this).setVisible(true));
    }

    private void login() {
        try {
            User user = authService.login(usernameField.getText(), new String(passwordField.getPassword()));
            JOptionPane.showMessageDialog(this, "Welcome " + user.getName());
            new MainFrame().setVisible(true);
            dispose();
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, ex.getMessage(), "Login Error", JOptionPane.ERROR_MESSAGE);
        }
    }
}

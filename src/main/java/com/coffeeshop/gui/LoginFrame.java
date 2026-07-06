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
import java.awt.FlowLayout;
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
        setTitle("تسجيل الدخول - نظام إدارة المقهى");
        setSize(420, 230);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        getContentPane().setBackground(UiTheme.BACKGROUND);
        buildUi();
    }

    /**
     * Shows a startup database error while still allowing the login screen to open.
     *
     * @param message error message
     */
    public static void showStartupError(String message) {
        JOptionPane.showMessageDialog(null, message, "تنبيه قاعدة البيانات", JOptionPane.WARNING_MESSAGE);
    }

    private void buildUi() {
        JPanel content = new JPanel(new BorderLayout(10, 10));
        UiTheme.page(content);
        content.add(UiTheme.title("تسجيل الدخول إلى نظام إدارة المقهى"), BorderLayout.NORTH);

        JPanel form = new JPanel(new GridLayout(2, 2, 10, 10));
        form.setBackground(UiTheme.SURFACE);
        JLabel usernameLabel = new JLabel("اسم المستخدم");
        JLabel passwordLabel = new JLabel("كلمة المرور");
        UiTheme.label(usernameLabel);
        UiTheme.label(passwordLabel);
        UiTheme.field(usernameField);
        UiTheme.field(passwordField);
        form.add(usernameLabel);
        form.add(usernameField);
        form.add(passwordLabel);
        form.add(passwordField);

        JButton loginButton = new JButton("دخول");
        JButton registerButton = new JButton("إنشاء حساب");
        JButton forgotButton = new JButton("نسيت كلمة المرور");
        UiTheme.button(loginButton, true);
        UiTheme.button(registerButton, false);
        UiTheme.button(forgotButton, false);

        JPanel actions = new JPanel(new FlowLayout(FlowLayout.RIGHT, 8, 0));
        actions.setBackground(UiTheme.BACKGROUND);
        actions.add(forgotButton);
        actions.add(registerButton);
        actions.add(loginButton);

        content.add(UiTheme.card(form), BorderLayout.CENTER);
        content.add(actions, BorderLayout.SOUTH);
        add(content);

        loginButton.addActionListener(event -> login());
        registerButton.addActionListener(event -> new RegisterDialog(this).setVisible(true));
        forgotButton.addActionListener(event -> new ForgotPasswordDialog(this).setVisible(true));
    }

    private void login() {
        try {
            User user = authService.login(usernameField.getText(), new String(passwordField.getPassword()));
            JOptionPane.showMessageDialog(this, "مرحبًا " + user.getName());
            new MainFrame().setVisible(true);
            dispose();
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, ex.getMessage(), "خطأ في تسجيل الدخول", JOptionPane.ERROR_MESSAGE);
        }
    }
}

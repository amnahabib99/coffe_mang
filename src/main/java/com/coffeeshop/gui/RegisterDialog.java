package com.coffeeshop.gui;

import com.coffeeshop.enums.UserRole;
import com.coffeeshop.enums.UserStatus;
import com.coffeeshop.services.AuthService;
import com.coffeeshop.utils.SessionManager;

import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import java.awt.BorderLayout;
import java.awt.GridLayout;

/**
 * Registration dialog for managers and new employees.
 */
public class RegisterDialog extends JDialog {
    private final JTextField nameField = new JTextField();
    private final JTextField usernameField = new JTextField();
    private final JPasswordField passwordField = new JPasswordField();
    private final JTextField phoneField = new JTextField();
    private final JComboBox<UserRole> roleBox = new JComboBox<>(UserRole.values());
    private final JTextField questionField = new JTextField("Default question?");
    private final JTextField answerField = new JTextField();
    private final AuthService authService = new AuthService();

    /**
     * Creates the registration dialog.
     *
     * @param owner parent frame
     */
    public RegisterDialog(JFrame owner) {
        super(owner, "Register User", true);
        setSize(460, 360);
        setLocationRelativeTo(owner);
        getContentPane().setBackground(UiTheme.BACKGROUND);
        buildUi();
    }

    private void buildUi() {
        JPanel content = new JPanel(new BorderLayout(10, 10));
        UiTheme.page(content);
        content.add(UiTheme.title("Register User"), BorderLayout.NORTH);
        JPanel panel = new JPanel(new GridLayout(8, 2, 8, 8));
        panel.setBackground(UiTheme.SURFACE);
        styleFields();
        addRow(panel, "Name", nameField);
        addRow(panel, "Username", usernameField);
        addRow(panel, "Password", passwordField);
        addRow(panel, "Phone", phoneField);
        JLabel roleLabel = new JLabel("Role");
        UiTheme.label(roleLabel);
        UiTheme.combo(roleBox);
        panel.add(roleLabel);
        panel.add(roleBox);
        addRow(panel, "Security Question", questionField);
        addRow(panel, "Security Answer", answerField);
        javax.swing.JButton saveButton = new javax.swing.JButton("Save User");
        UiTheme.button(saveButton, true);
        panel.add(new JLabel());
        panel.add(saveButton);
        content.add(UiTheme.card(panel), BorderLayout.CENTER);
        add(content);
        saveButton.addActionListener(event -> save());
    }

    private void styleFields() {
        UiTheme.field(nameField);
        UiTheme.field(usernameField);
        UiTheme.field(passwordField);
        UiTheme.field(phoneField);
        UiTheme.field(questionField);
        UiTheme.field(answerField);
    }

    private void addRow(JPanel panel, String labelText, JTextField field) {
        JLabel label = new JLabel(labelText);
        UiTheme.label(label);
        panel.add(label);
        panel.add(field);
    }

    private void save() {
        try {
            UserStatus status = SessionManager.getCurrentUser() == null ? UserStatus.PENDING : UserStatus.ACTIVE;
            authService.register(nameField.getText(), usernameField.getText(), new String(passwordField.getPassword()),
                    phoneField.getText(), (UserRole) roleBox.getSelectedItem(), questionField.getText(), answerField.getText(), status);
            JOptionPane.showMessageDialog(this, "User registered successfully.");
            dispose();
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, ex.getMessage(), "Registration Error", JOptionPane.ERROR_MESSAGE);
        }
    }
}

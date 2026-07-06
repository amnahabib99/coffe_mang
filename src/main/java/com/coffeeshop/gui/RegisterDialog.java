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
        buildUi();
    }

    private void buildUi() {
        JPanel panel = new JPanel(new GridLayout(8, 2, 8, 8));
        panel.add(new JLabel("Name"));
        panel.add(nameField);
        panel.add(new JLabel("Username"));
        panel.add(usernameField);
        panel.add(new JLabel("Password"));
        panel.add(passwordField);
        panel.add(new JLabel("Phone"));
        panel.add(phoneField);
        panel.add(new JLabel("Role"));
        panel.add(roleBox);
        panel.add(new JLabel("Security Question"));
        panel.add(questionField);
        panel.add(new JLabel("Security Answer"));
        panel.add(answerField);

        javax.swing.JButton saveButton = new javax.swing.JButton("Save");
        panel.add(new JLabel());
        panel.add(saveButton);
        add(panel);
        saveButton.addActionListener(event -> save());
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

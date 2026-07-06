package com.coffeeshop.gui;

import com.coffeeshop.services.AuthService;

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
 * Allows users to reset passwords using a security answer.
 */
public class ForgotPasswordDialog extends JDialog {
    private final JTextField usernameField = new JTextField();
    private final JTextField questionField = new JTextField();
    private final JTextField answerField = new JTextField();
    private final JPasswordField newPasswordField = new JPasswordField();
    private final AuthService authService = new AuthService();

    /**
     * Creates the forgot-password dialog.
     *
     * @param owner parent frame
     */
    public ForgotPasswordDialog(JFrame owner) {
        super(owner, "Forgot Password", true);
        setSize(500, 260);
        setLocationRelativeTo(owner);
        getContentPane().setBackground(UiTheme.BACKGROUND);
        questionField.setEditable(false);
        buildUi();
    }

    private void buildUi() {
        JPanel content = new JPanel(new BorderLayout(10, 10));
        UiTheme.page(content);
        content.add(UiTheme.title("Password Recovery"), BorderLayout.NORTH);
        JPanel panel = new JPanel(new GridLayout(5, 2, 8, 8));
        panel.setBackground(UiTheme.SURFACE);
        UiTheme.field(usernameField);
        UiTheme.field(questionField);
        UiTheme.field(answerField);
        UiTheme.field(newPasswordField);
        addRow(panel, "Username", usernameField);
        addRow(panel, "Security Question", questionField);
        addRow(panel, "Security Answer", answerField);
        addRow(panel, "New Password", newPasswordField);

        javax.swing.JButton loadButton = new javax.swing.JButton("Load Question");
        javax.swing.JButton resetButton = new javax.swing.JButton("Reset Password");
        UiTheme.button(loadButton, false);
        UiTheme.button(resetButton, true);
        panel.add(loadButton);
        panel.add(resetButton);
        content.add(UiTheme.card(panel), BorderLayout.CENTER);
        add(content);

        loadButton.addActionListener(event -> loadQuestion());
        resetButton.addActionListener(event -> reset());
    }

    private void addRow(JPanel panel, String labelText, JTextField field) {
        JLabel label = new JLabel(labelText);
        UiTheme.label(label);
        panel.add(label);
        panel.add(field);
    }

    private void loadQuestion() {
        try {
            questionField.setText(authService.getSecurityQuestion(usernameField.getText()));
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, ex.getMessage(), "Recovery Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void reset() {
        try {
            authService.resetPassword(usernameField.getText(), answerField.getText(), new String(newPasswordField.getPassword()));
            JOptionPane.showMessageDialog(this, "Password updated successfully.");
            dispose();
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, ex.getMessage(), "Recovery Error", JOptionPane.ERROR_MESSAGE);
        }
    }
}

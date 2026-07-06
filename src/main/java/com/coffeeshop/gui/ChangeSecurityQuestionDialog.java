package com.coffeeshop.gui;

import com.coffeeshop.services.UserService;
import com.coffeeshop.utils.SessionManager;

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
 * Dialog for changing the logged-in user's security question.
 */
public class ChangeSecurityQuestionDialog extends JDialog {
    private final JPasswordField passwordField = new JPasswordField();
    private final JTextField questionField = new JTextField();
    private final JTextField answerField = new JTextField();
    private final UserService service = new UserService();

    /**
     * Creates the dialog.
     *
     * @param owner parent frame
     */
    public ChangeSecurityQuestionDialog(JFrame owner) {
        super(owner, "Change Security Question", true);
        setSize(500, 230);
        setLocationRelativeTo(owner);
        getContentPane().setBackground(UiTheme.BACKGROUND);
        JPanel content = new JPanel(new BorderLayout(10, 10));
        UiTheme.page(content);
        content.add(UiTheme.title("Security Question"), BorderLayout.NORTH);
        JPanel panel = new JPanel(new GridLayout(4, 2, 8, 8));
        panel.setBackground(UiTheme.SURFACE);
        UiTheme.field(passwordField);
        UiTheme.field(questionField);
        UiTheme.field(answerField);
        addRow(panel, "Current Password", passwordField);
        addRow(panel, "New Security Question", questionField);
        addRow(panel, "New Security Answer", answerField);
        javax.swing.JButton saveButton = new javax.swing.JButton("Save Security");
        UiTheme.button(saveButton, true);
        panel.add(new JLabel());
        panel.add(saveButton);
        content.add(UiTheme.card(panel), BorderLayout.CENTER);
        add(content);
        saveButton.addActionListener(event -> save());
    }

    private void addRow(JPanel panel, String text, JTextField field) {
        JLabel label = new JLabel(text);
        UiTheme.label(label);
        panel.add(label);
        panel.add(field);
    }

    private void save() {
        try {
            service.changeSecurityQuestion(SessionManager.getCurrentUser(), new String(passwordField.getPassword()),
                    questionField.getText(), answerField.getText());
            JOptionPane.showMessageDialog(this, "Security question changed successfully.");
            dispose();
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, ex.getMessage(), "Security Question Error", JOptionPane.ERROR_MESSAGE);
        }
    }
}

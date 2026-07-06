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
        JPanel panel = new JPanel(new GridLayout(4, 2, 8, 8));
        panel.add(new JLabel("Current Password"));
        panel.add(passwordField);
        panel.add(new JLabel("New Security Question"));
        panel.add(questionField);
        panel.add(new JLabel("New Security Answer"));
        panel.add(answerField);
        javax.swing.JButton saveButton = new javax.swing.JButton("Save");
        panel.add(new JLabel());
        panel.add(saveButton);
        add(panel);
        saveButton.addActionListener(event -> save());
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

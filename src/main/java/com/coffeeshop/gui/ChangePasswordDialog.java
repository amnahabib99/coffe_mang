package com.coffeeshop.gui;

import com.coffeeshop.services.UserService;
import com.coffeeshop.utils.SessionManager;

import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import java.awt.GridLayout;

/**
 * Dialog for changing the logged-in user's password.
 */
public class ChangePasswordDialog extends JDialog {
    private final JPasswordField oldField = new JPasswordField();
    private final JPasswordField newField = new JPasswordField();
    private final JPasswordField confirmField = new JPasswordField();
    private final UserService service = new UserService();

    /**
     * Creates the dialog.
     *
     * @param owner parent frame
     */
    public ChangePasswordDialog(JFrame owner) {
        super(owner, "Change Password", true);
        setSize(420, 220);
        setLocationRelativeTo(owner);
        JPanel panel = new JPanel(new GridLayout(4, 2, 8, 8));
        panel.add(new JLabel("Old Password"));
        panel.add(oldField);
        panel.add(new JLabel("New Password"));
        panel.add(newField);
        panel.add(new JLabel("Confirm New Password"));
        panel.add(confirmField);
        javax.swing.JButton saveButton = new javax.swing.JButton("Save");
        panel.add(new JLabel());
        panel.add(saveButton);
        add(panel);
        saveButton.addActionListener(event -> save());
    }

    private void save() {
        try {
            service.changePassword(SessionManager.getCurrentUser(), new String(oldField.getPassword()),
                    new String(newField.getPassword()), new String(confirmField.getPassword()));
            JOptionPane.showMessageDialog(this, "Password changed successfully.");
            dispose();
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, ex.getMessage(), "Password Error", JOptionPane.ERROR_MESSAGE);
        }
    }
}

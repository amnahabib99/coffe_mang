package com.coffeeshop.gui;

import com.coffeeshop.services.UserService;
import com.coffeeshop.utils.SessionManager;

import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import java.awt.BorderLayout;
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
        getContentPane().setBackground(UiTheme.BACKGROUND);
        JPanel content = new JPanel(new BorderLayout(10, 10));
        UiTheme.page(content);
        content.add(UiTheme.title("Change Password"), BorderLayout.NORTH);
        JPanel panel = new JPanel(new GridLayout(4, 2, 8, 8));
        panel.setBackground(UiTheme.SURFACE);
        UiTheme.field(oldField);
        UiTheme.field(newField);
        UiTheme.field(confirmField);
        addRow(panel, "Old Password", oldField);
        addRow(panel, "New Password", newField);
        addRow(panel, "Confirm New Password", confirmField);
        javax.swing.JButton saveButton = new javax.swing.JButton("Save Password");
        UiTheme.button(saveButton, true);
        panel.add(new JLabel());
        panel.add(saveButton);
        content.add(UiTheme.card(panel), BorderLayout.CENTER);
        add(content);
        saveButton.addActionListener(event -> save());
    }

    private void addRow(JPanel panel, String text, JPasswordField field) {
        JLabel label = new JLabel(text);
        UiTheme.label(label);
        panel.add(label);
        panel.add(field);
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

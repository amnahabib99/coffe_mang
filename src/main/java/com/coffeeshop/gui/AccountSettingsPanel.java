package com.coffeeshop.gui;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;

/**
 * Account settings panel for changing passwords and security questions.
 */
public class AccountSettingsPanel extends JPanel {
    /**
     * Creates account settings controls.
     *
     * @param owner parent frame
     */
    public AccountSettingsPanel(JFrame owner) {
        JButton passwordButton = new JButton("Change Password");
        JButton securityButton = new JButton("Change Security Question");
        add(passwordButton);
        add(securityButton);
        passwordButton.addActionListener(event -> new ChangePasswordDialog(owner).setVisible(true));
        securityButton.addActionListener(event -> new ChangeSecurityQuestionDialog(owner).setVisible(true));
    }
}

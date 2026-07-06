package com.coffeeshop.gui;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import java.awt.BorderLayout;
import java.awt.FlowLayout;

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
        setLayout(new BorderLayout(12, 12));
        UiTheme.page(this);
        JButton passwordButton = new JButton("تغيير كلمة المرور");
        JButton securityButton = new JButton("تغيير سؤال الأمان");
        UiTheme.button(passwordButton, true);
        UiTheme.button(securityButton, false);
        add(UiTheme.title("إعدادات الحساب"), BorderLayout.NORTH);
        JPanel actions = new JPanel(new FlowLayout(FlowLayout.RIGHT, 12, 0));
        actions.setBackground(UiTheme.BACKGROUND);
        actions.add(passwordButton);
        actions.add(securityButton);
        add(actions, BorderLayout.SOUTH);
        passwordButton.addActionListener(event -> new ChangePasswordDialog(owner).setVisible(true));
        securityButton.addActionListener(event -> new ChangeSecurityQuestionDialog(owner).setVisible(true));
        UiTheme.rtl(this);
    }
}

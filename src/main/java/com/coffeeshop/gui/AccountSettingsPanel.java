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
        UiTheme.page(this);
        JButton passwordButton = new JButton("تغيير كلمة المرور");
        JButton securityButton = new JButton("تغيير سؤال الأمان");
        UiTheme.button(passwordButton, true);
        UiTheme.button(securityButton, false);
        add(UiTheme.title("إعدادات الحساب"));
        add(passwordButton);
        add(securityButton);
        passwordButton.addActionListener(event -> new ChangePasswordDialog(owner).setVisible(true));
        securityButton.addActionListener(event -> new ChangeSecurityQuestionDialog(owner).setVisible(true));
        UiTheme.rtl(this);
    }
}

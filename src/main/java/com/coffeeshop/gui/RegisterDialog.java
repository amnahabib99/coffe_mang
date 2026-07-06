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
    private final JTextField questionField = new JTextField("ما هو سؤال الأمان؟");
    private final JTextField answerField = new JTextField();
    private final AuthService authService = new AuthService();

    /**
     * Creates the registration dialog.
     *
     * @param owner parent frame
     */
    public RegisterDialog(JFrame owner) {
        super(owner, "إنشاء حساب مستخدم", true);
        setSize(620, 500);
        setMinimumSize(new java.awt.Dimension(560, 440));
        setLocationRelativeTo(owner);
        getContentPane().setBackground(UiTheme.BACKGROUND);
        buildUi();
    }

    private void buildUi() {
        JPanel content = new JPanel(new BorderLayout(10, 10));
        UiTheme.page(content);
        content.add(UiTheme.title("إنشاء حساب مستخدم"), BorderLayout.NORTH);
        JPanel panel = new JPanel(new GridLayout(8, 2, 8, 8));
        panel.setBackground(UiTheme.SURFACE);
        styleFields();
        addRow(panel, "الاسم", nameField);
        addRow(panel, "اسم المستخدم", usernameField);
        addRow(panel, "كلمة المرور", passwordField);
        addRow(panel, "رقم الهاتف", phoneField);
        JLabel roleLabel = new JLabel("الدور");
        UiTheme.label(roleLabel);
        UiTheme.combo(roleBox);
        panel.add(roleLabel);
        panel.add(roleBox);
        addRow(panel, "سؤال الأمان", questionField);
        addRow(panel, "إجابة سؤال الأمان", answerField);
        javax.swing.JButton saveButton = new javax.swing.JButton("حفظ المستخدم");
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
            JOptionPane.showMessageDialog(this, "تم إنشاء الحساب بنجاح.");
            dispose();
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, ex.getMessage(), "خطأ في إنشاء الحساب", JOptionPane.ERROR_MESSAGE);
        }
    }
}

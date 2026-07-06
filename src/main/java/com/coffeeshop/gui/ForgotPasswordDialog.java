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
        super(owner, "استعادة كلمة المرور", true);
        setSize(620, 360);
        setMinimumSize(new java.awt.Dimension(560, 320));
        setLocationRelativeTo(owner);
        getContentPane().setBackground(UiTheme.BACKGROUND);
        questionField.setEditable(false);
        buildUi();
    }

    private void buildUi() {
        JPanel content = new JPanel(new BorderLayout(10, 10));
        UiTheme.page(content);
        content.add(UiTheme.title("استعادة كلمة المرور"), BorderLayout.NORTH);
        JPanel panel = new JPanel(new GridLayout(5, 2, 8, 8));
        panel.setBackground(UiTheme.SURFACE);
        UiTheme.field(usernameField);
        UiTheme.field(questionField);
        UiTheme.field(answerField);
        UiTheme.field(newPasswordField);
        addRow(panel, "اسم المستخدم", usernameField);
        addRow(panel, "سؤال الأمان", questionField);
        addRow(panel, "إجابة سؤال الأمان", answerField);
        addRow(panel, "كلمة المرور الجديدة", newPasswordField);

        javax.swing.JButton loadButton = new javax.swing.JButton("عرض السؤال");
        javax.swing.JButton resetButton = new javax.swing.JButton("تحديث كلمة المرور");
        UiTheme.button(loadButton, false);
        UiTheme.button(resetButton, true);
        panel.add(loadButton);
        panel.add(resetButton);
        content.add(UiTheme.card(panel), BorderLayout.CENTER);
        add(content);
        UiTheme.rtl(getContentPane());

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
            JOptionPane.showMessageDialog(this, ex.getMessage(), "خطأ في الاستعادة", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void reset() {
        try {
            authService.resetPassword(usernameField.getText(), answerField.getText(), new String(newPasswordField.getPassword()));
            JOptionPane.showMessageDialog(this, "تم تحديث كلمة المرور بنجاح.");
            dispose();
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, ex.getMessage(), "خطأ في الاستعادة", JOptionPane.ERROR_MESSAGE);
        }
    }
}

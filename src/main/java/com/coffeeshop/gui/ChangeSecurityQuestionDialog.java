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
        super(owner, "تغيير سؤال الأمان", true);
        setSize(620, 340);
        setMinimumSize(new java.awt.Dimension(560, 300));
        setLocationRelativeTo(owner);
        getContentPane().setBackground(UiTheme.BACKGROUND);
        JPanel content = new JPanel(new BorderLayout(10, 10));
        UiTheme.page(content);
        content.add(UiTheme.title("تغيير سؤال الأمان"), BorderLayout.NORTH);
        JPanel panel = new JPanel(new GridLayout(4, 2, 8, 8));
        panel.setBackground(UiTheme.SURFACE);
        UiTheme.field(passwordField);
        UiTheme.field(questionField);
        UiTheme.field(answerField);
        addRow(panel, "كلمة المرور الحالية", passwordField);
        addRow(panel, "سؤال الأمان الجديد", questionField);
        addRow(panel, "إجابة سؤال الأمان الجديدة", answerField);
        javax.swing.JButton saveButton = new javax.swing.JButton("حفظ سؤال الأمان");
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
            JOptionPane.showMessageDialog(this, "تم تغيير سؤال الأمان بنجاح.");
            dispose();
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, ex.getMessage(), "خطأ في سؤال الأمان", JOptionPane.ERROR_MESSAGE);
        }
    }
}

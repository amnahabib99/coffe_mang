package com.coffeeshop.gui;

import com.coffeeshop.enums.UserRole;
import com.coffeeshop.enums.UserStatus;
import com.coffeeshop.models.User;
import com.coffeeshop.services.UserService;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import java.awt.BorderLayout;
import java.util.ArrayList;
import java.util.List;

/**
 * Manager-only panel for user verification and account management.
 */
public class UserManagementPanel extends JPanel {
    private final UserService service = new UserService();
    private final DefaultTableModel model = new DefaultTableModel(new String[]{"الرقم", "الاسم", "اسم المستخدم", "الدور", "الحالة"}, 0);
    private final JTable table = new JTable(model);
    private final JComboBox<UserRole> roleBox = new JComboBox<>(UserRole.values());
    private final List<User> users = new ArrayList<>();
    private final JFrame owner;

    /**
     * Creates the user management panel.
     */
    public UserManagementPanel(JFrame owner) {
        this.owner = owner;
        setLayout(new BorderLayout(8, 8));
        UiTheme.page(this);
        add(UiTheme.title("إدارة المستخدمين والتحقق"), BorderLayout.NORTH);
        UiTheme.table(table);
        JScrollPane scrollPane = new JScrollPane(table);
        UiTheme.scroll(scrollPane);
        add(scrollPane, BorderLayout.CENTER);
        JPanel buttons = new JPanel();
        buttons.setBackground(UiTheme.BACKGROUND);
        JButton register = new JButton("إنشاء حساب");
        JButton activate = new JButton("تفعيل");
        JButton deactivate = new JButton("إيقاف");
        JButton changeRole = new JButton("تغيير الدور");
        UiTheme.button(register, true);
        UiTheme.button(activate, true);
        UiTheme.dangerButton(deactivate);
        UiTheme.button(changeRole, false);
        UiTheme.combo(roleBox);
        buttons.add(register);
        buttons.add(activate);
        buttons.add(deactivate);
        buttons.add(roleBox);
        buttons.add(changeRole);
        add(buttons, BorderLayout.SOUTH);
        register.addActionListener(event -> openRegisterDialog());
        activate.addActionListener(event -> updateStatus(UserStatus.ACTIVE));
        deactivate.addActionListener(event -> updateStatus(UserStatus.INACTIVE));
        changeRole.addActionListener(event -> changeRole());
        UiTheme.rtl(this);
        refresh();
    }

    private void openRegisterDialog() {
        new RegisterDialog(owner).setVisible(true);
        refresh();
    }

    private void refresh() {
        try {
            users.clear();
            users.addAll(service.getAllUsers());
            model.setRowCount(0);
            for (User user : users) {
                model.addRow(new Object[]{user.getId(), user.getName(), user.getUsername(), user.getRole(), user.getStatus()});
            }
        } catch (Exception ex) {
            showError(ex);
        }
    }

    private void updateStatus(UserStatus status) {
        try {
            service.updateStatus(selectedUserId(), status);
            refresh();
            JOptionPane.showMessageDialog(this, "تم تحديث حالة المستخدم.");
        } catch (Exception ex) {
            showError(ex);
        }
    }

    private void changeRole() {
        try {
            service.updateRole(selectedUserId(), (UserRole) roleBox.getSelectedItem());
            refresh();
            JOptionPane.showMessageDialog(this, "تم تحديث دور المستخدم.");
        } catch (Exception ex) {
            showError(ex);
        }
    }

    private int selectedUserId() {
        int row = table.getSelectedRow();
        if (row < 0) {
            throw new IllegalArgumentException("الرجاء اختيار مستخدم أولًا.");
        }
        return (int) model.getValueAt(row, 0);
    }

    private void showError(Exception ex) {
        JOptionPane.showMessageDialog(this, ex.getMessage(), "خطأ في إدارة المستخدمين", JOptionPane.ERROR_MESSAGE);
    }
}

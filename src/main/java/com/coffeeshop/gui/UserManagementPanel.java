package com.coffeeshop.gui;

import com.coffeeshop.enums.UserRole;
import com.coffeeshop.enums.UserStatus;
import com.coffeeshop.models.User;
import com.coffeeshop.services.UserService;

import javax.swing.JButton;
import javax.swing.JComboBox;
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
    private final DefaultTableModel model = new DefaultTableModel(new String[]{"ID", "Name", "Username", "Role", "Status"}, 0);
    private final JTable table = new JTable(model);
    private final JComboBox<UserRole> roleBox = new JComboBox<>(UserRole.values());
    private final List<User> users = new ArrayList<>();

    /**
     * Creates the user management panel.
     */
    public UserManagementPanel() {
        setLayout(new BorderLayout(8, 8));
        add(new JScrollPane(table), BorderLayout.CENTER);
        JPanel buttons = new JPanel();
        JButton activate = new JButton("Activate");
        JButton deactivate = new JButton("Deactivate");
        JButton changeRole = new JButton("Change Role");
        buttons.add(activate);
        buttons.add(deactivate);
        buttons.add(roleBox);
        buttons.add(changeRole);
        add(buttons, BorderLayout.SOUTH);
        activate.addActionListener(event -> updateStatus(UserStatus.ACTIVE));
        deactivate.addActionListener(event -> updateStatus(UserStatus.INACTIVE));
        changeRole.addActionListener(event -> changeRole());
        refresh();
    }

    private void refresh() {
        try {
            users.clear();
            users.addAll(service.getAllUsers());
            model.setRowCount(0);
            for (User user : users) {
                model.addRow(new Object[]{user.getId(), user.getName(), user.getUsername(), user.getRole().getLabel(), user.getStatus()});
            }
        } catch (Exception ex) {
            showError(ex);
        }
    }

    private void updateStatus(UserStatus status) {
        try {
            service.updateStatus(selectedUserId(), status);
            refresh();
            JOptionPane.showMessageDialog(this, "User status updated.");
        } catch (Exception ex) {
            showError(ex);
        }
    }

    private void changeRole() {
        try {
            service.updateRole(selectedUserId(), (UserRole) roleBox.getSelectedItem());
            refresh();
            JOptionPane.showMessageDialog(this, "User role updated.");
        } catch (Exception ex) {
            showError(ex);
        }
    }

    private int selectedUserId() {
        int row = table.getSelectedRow();
        if (row < 0) {
            throw new IllegalArgumentException("Select a user first.");
        }
        return (int) model.getValueAt(row, 0);
    }

    private void showError(Exception ex) {
        JOptionPane.showMessageDialog(this, ex.getMessage(), "User Management Error", JOptionPane.ERROR_MESSAGE);
    }
}

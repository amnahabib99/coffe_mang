package com.coffeeshop.gui;

import com.coffeeshop.enums.UserRole;
import com.coffeeshop.models.User;
import com.coffeeshop.utils.SessionManager;

import javax.swing.JFrame;
import javax.swing.JTabbedPane;

/**
 * Main role-based application window.
 */
public class MainFrame extends JFrame {
    /**
     * Creates the main frame after login.
     */
    public MainFrame() {
        User user = SessionManager.getCurrentUser();
        setTitle("Coffee Shop Management System - " + user);
        setSize(1100, 720);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        buildTabs(user);
    }

    private void buildTabs(User user) {
        JTabbedPane tabs = new JTabbedPane();
        tabs.addTab("Home", new HomePanel());
        if (user.getRole() == UserRole.MANAGER) {
            tabs.addTab("Categories", new CategoryPanel());
            tabs.addTab("Products", new ProductPanel());
        }
        tabs.addTab("Orders", new OrderPanel());
        tabs.addTab("Invoices", new InvoicePanel());
        tabs.addTab("Reports", new ReportsPanel());
        if (user.getRole() == UserRole.MANAGER) {
            tabs.addTab("User Management", new UserManagementPanel());
        }
        tabs.addTab("Account Settings", new AccountSettingsPanel(this));
        add(tabs);
    }
}

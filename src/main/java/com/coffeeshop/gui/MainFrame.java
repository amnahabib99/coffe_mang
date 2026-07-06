package com.coffeeshop.gui;

import com.coffeeshop.enums.UserRole;
import com.coffeeshop.models.User;
import com.coffeeshop.utils.SessionManager;

import javax.swing.JFrame;
import javax.swing.JTabbedPane;
import java.awt.Color;

/**
 * Main role-based application window.
 */
public class MainFrame extends JFrame {
    /**
     * Creates the main frame after login.
     */
    public MainFrame() {
        User user = SessionManager.getCurrentUser();
        setTitle("نظام إدارة المقهى - " + user);
        setSize(1100, 720);
        setMinimumSize(new java.awt.Dimension(1000, 660));
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        getContentPane().setBackground(UiTheme.BACKGROUND);
        buildTabs(user);
    }

    private void buildTabs(User user) {
        JTabbedPane tabs = new JTabbedPane();
        tabs.setFont(new java.awt.Font(UiTheme.FONT_NAME, java.awt.Font.BOLD, 14));
        tabs.setBackground(UiTheme.BACKGROUND);
        tabs.setForeground(UiTheme.TEXT);
        tabs.setOpaque(true);
        tabs.addTab("الرئيسية", new HomePanel());
        if (user.getRole() == UserRole.MANAGER) {
            tabs.addTab("التصنيفات", new CategoryPanel());
            tabs.addTab("المنتجات", new ProductPanel());
        }
        tabs.addTab("الطلبات", new OrderPanel());
        tabs.addTab("الفواتير", new InvoicePanel());
        tabs.addTab("التقارير", new ReportsPanel());
        if (user.getRole() == UserRole.MANAGER) {
            tabs.addTab("إدارة المستخدمين", new UserManagementPanel());
        }
        tabs.addTab("إعدادات الحساب", new AccountSettingsPanel(this));
        add(tabs);
        UiTheme.rtl(getContentPane());
    }
}

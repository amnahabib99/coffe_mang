package com.coffeeshop.gui;

import com.coffeeshop.models.User;
import com.coffeeshop.utils.SessionManager;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import java.awt.BorderLayout;

/**
 * Home panel showing available features for the current role.
 */
public class HomePanel extends JPanel {
    /**
     * Creates the role-aware home panel.
     */
    public HomePanel() {
        setLayout(new BorderLayout());
        User user = SessionManager.getCurrentUser();
        add(new JLabel("Welcome, " + user.getName() + " - " + user.getRole().getLabel()), BorderLayout.NORTH);
        JTextArea area = new JTextArea();
        area.setEditable(false);
        area.setText(user.getRoleDescription() + "\n\nManager features: categories, products, users, reports, invoices, orders.\n"
                + "Employee features: orders, invoices, account settings.");
        add(area, BorderLayout.CENTER);
    }
}

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
        UiTheme.page(this);
        User user = SessionManager.getCurrentUser();
        add(UiTheme.title("Welcome, " + user.getName()), BorderLayout.NORTH);
        JTextArea area = new JTextArea();
        UiTheme.textArea(area);
        area.setEditable(false);
        area.setText(user.getRoleDescription() + "\n\nRole: " + user.getRole().getLabel()
                + "\n\nManager features:\n- Categories\n- Products\n- User Management\n- Reports\n- Invoices\n- Orders\n\n"
                + "Employee features:\n- Orders\n- Invoices\n- Account Settings");
        add(UiTheme.card(area), BorderLayout.CENTER);
    }
}

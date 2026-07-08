package com.coffeeshop.gui;

import com.coffeeshop.enums.UserRole;
import com.coffeeshop.models.User;
import com.coffeeshop.utils.SessionManager;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.SwingConstants;
import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Dimension;
import java.awt.GridLayout;

/**
 * Main role-based application window.
 */
public class MainFrame extends JFrame {
    private final CardLayout cardLayout = new CardLayout();
    private final JPanel contentPanel = new JPanel(cardLayout);

    /**
     * Creates the main frame after login.
     */
    public MainFrame() {
        User user = SessionManager.getCurrentUser();
        setTitle("نظام إدارة المقهى - " + user);
        setSize(1100, 720);
        setMinimumSize(new java.awt.Dimension(1000, 660));
        setExtendedState(JFrame.MAXIMIZED_BOTH);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        getContentPane().setBackground(UiTheme.BACKGROUND);
        buildLayout(user);
    }

    private void buildLayout(User user) {
        JPanel root = new JPanel(new BorderLayout(12, 12));
        UiTheme.page(root);
        contentPanel.setBackground(UiTheme.BACKGROUND);

        JPanel sidebar = new JPanel(new BorderLayout(8, 8));
        sidebar.setBackground(UiTheme.PRIMARY);
        sidebar.setPreferredSize(new Dimension(300, 0));
        sidebar.setBorder(BorderFactory.createEmptyBorder(16, 12, 16, 12));

        JLabel title = new JLabel("نظام إدارة المقهى");
        title.setForeground(java.awt.Color.WHITE);
        title.setFont(new java.awt.Font(UiTheme.FONT_NAME, java.awt.Font.BOLD, 18));
        title.setHorizontalAlignment(JLabel.RIGHT);
        sidebar.add(title, BorderLayout.NORTH);

        JPanel menu = new JPanel(new GridLayout(0, 1, 0, 14));
        menu.setBackground(UiTheme.PRIMARY);
        menu.setBorder(BorderFactory.createEmptyBorder(12, 0, 12, 0));

        addScreen(menu, "الرئيسية", new HomePanel());
        if (user.getRole() == UserRole.MANAGER) {
            addScreen(menu, "التصنيفات", new CategoryPanel());
            addScreen(menu, "المنتجات", new ProductPanel());
        }
        addScreen(menu, "الطلبات", new OrderPanel());
        addScreen(menu, "الفواتير", new InvoicePanel());
        addScreen(menu, "التقارير", new ReportsPanel());
        if (user.getRole() == UserRole.MANAGER) {
            addScreen(menu, "إدارة المستخدمين", new UserManagementPanel(this));
        }
        addScreen(menu, "إعدادات الحساب", new AccountSettingsPanel(this));

        JScrollPane sidebarScroll = new JScrollPane(menu);
        sidebarScroll.setBorder(BorderFactory.createEmptyBorder());
        sidebarScroll.getViewport().setBackground(UiTheme.PRIMARY);
        sidebar.add(sidebarScroll, BorderLayout.CENTER);
        sidebar.add(createLogoutButton(), BorderLayout.SOUTH);

        root.add(sidebar, BorderLayout.LINE_START);
        root.add(contentPanel, BorderLayout.CENTER);
        add(root);
        UiTheme.rtl(getContentPane());
    }

    private void addScreen(JPanel menu, String name, JPanel panel) {
        contentPanel.add(panel, name);
        JButton button = new JButton(name);
        UiTheme.button(button, false);
        button.setPreferredSize(new Dimension(250, 46));
        button.setBackground(UiTheme.ACCENT);
        button.setHorizontalAlignment(SwingConstants.CENTER);
        button.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(java.awt.Color.WHITE, 1),
                BorderFactory.createEmptyBorder(10, 16, 10, 16)));
        button.addActionListener(event -> cardLayout.show(contentPanel, name));
        menu.add(button);
    }

    private JButton createLogoutButton() {
        JButton logoutButton = new JButton("تسجيل الخروج");
        UiTheme.dangerButton(logoutButton);
        logoutButton.setPreferredSize(new Dimension(250, 46));
        logoutButton.setHorizontalAlignment(SwingConstants.CENTER);
        logoutButton.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(java.awt.Color.WHITE, 1),
                BorderFactory.createEmptyBorder(10, 16, 10, 16)));
        logoutButton.addActionListener(event -> confirmLogout());
        return logoutButton;
    }

    private void confirmLogout() {
        int choice = JOptionPane.showConfirmDialog(
                this,
                "هل أنت متأكد من تسجيل الخروج؟",
                "تأكيد تسجيل الخروج",
                JOptionPane.YES_NO_OPTION,
                JOptionPane.QUESTION_MESSAGE);
        if (choice == JOptionPane.YES_OPTION) {
            SessionManager.clear();
            dispose();
            new LoginFrame().setVisible(true);
        }
    }
}

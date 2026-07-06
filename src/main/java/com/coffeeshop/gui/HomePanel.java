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
        add(UiTheme.title("مرحبًا، " + user.getName()), BorderLayout.NORTH);
        JTextArea area = new JTextArea();
        UiTheme.textArea(area);
        area.setEditable(false);
        area.setText(user.getRoleDescription() + "\n\nالدور: " + user.getRole()
                + "\n\nصلاحيات المدير:\n- إدارة التصنيفات\n- إدارة المنتجات\n- إدارة المستخدمين\n- التقارير\n- الفواتير\n- الطلبات\n\n"
                + "صلاحيات الموظف:\n- إنشاء الطلبات\n- عرض الفواتير\n- إعدادات الحساب");
        add(UiTheme.card(area), BorderLayout.CENTER);
        UiTheme.rtl(this);
    }
}

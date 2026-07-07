package com.coffeeshop.gui;

import com.coffeeshop.services.OrderService;
import com.coffeeshop.services.ReportService;

import javax.swing.JButton;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import java.awt.BorderLayout;

/**
 * Reports panel that displays real sales totals from saved orders.
 */
public class ReportsPanel extends JPanel {
    private final ReportService reportService = new ReportService();
    private final OrderService orderService = new OrderService();
    private final JTextArea area = new JTextArea();

    /**
     * Creates the reports panel.
     */
    public ReportsPanel() {
        setLayout(new BorderLayout());
        UiTheme.page(this);

        JButton refreshButton = new JButton("إنشاء التقرير");
        JButton printButton = new JButton("طباعة");
        UiTheme.refreshButton(refreshButton);
        UiTheme.button(printButton, false);

        area.setEditable(false);
        UiTheme.textArea(area);

        JPanel top = new JPanel(new BorderLayout());
        top.setBackground(UiTheme.BACKGROUND);
        top.add(UiTheme.title("التقارير"), BorderLayout.LINE_START);

        JPanel actions = new JPanel();
        actions.setBackground(UiTheme.BACKGROUND);
        actions.add(printButton);
        actions.add(refreshButton);
        top.add(actions, BorderLayout.LINE_END);

        add(top, BorderLayout.NORTH);
        JScrollPane scrollPane = new JScrollPane(area);
        UiTheme.scroll(scrollPane);
        add(scrollPane, BorderLayout.CENTER);

        refreshButton.addActionListener(event -> buildReport());
        printButton.addActionListener(event -> printReport());
        UiTheme.rtl(this);
        buildReport();
    }

    private void buildReport() {
        try {
            area.setText(reportService.buildSalesReport(orderService.getAllOrders()));
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, ex.getMessage(), "خطأ في التقارير", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void printReport() {
        try {
            if (area.getText().trim().isEmpty()) {
                JOptionPane.showMessageDialog(this, "لا يوجد تقرير للطباعة.");
                return;
            }
            area.print();
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "تعذرت طباعة التقرير: " + ex.getMessage(),
                    "خطأ في الطباعة", JOptionPane.ERROR_MESSAGE);
        }
    }
}

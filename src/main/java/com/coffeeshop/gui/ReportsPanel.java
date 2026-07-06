package com.coffeeshop.gui;

import com.coffeeshop.models.Order;
import com.coffeeshop.services.OrderService;
import com.coffeeshop.services.ReportService;

import javax.swing.JButton;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import java.awt.BorderLayout;

/**
 * Reports panel including visible OOP concept demonstration.
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
        JButton refreshButton = new JButton("Build Reports");
        UiTheme.button(refreshButton, true);
        area.setEditable(false);
        UiTheme.textArea(area);
        JPanel top = new JPanel(new BorderLayout());
        top.setBackground(UiTheme.BACKGROUND);
        top.add(UiTheme.title("Reports"), BorderLayout.WEST);
        top.add(refreshButton, BorderLayout.EAST);
        add(top, BorderLayout.NORTH);
        JScrollPane scrollPane = new JScrollPane(area);
        UiTheme.scroll(scrollPane);
        add(scrollPane, BorderLayout.CENTER);
        refreshButton.addActionListener(event -> buildReport());
        buildReport();
    }

    private void buildReport() {
        try {
            StringBuilder builder = new StringBuilder(reportService.buildOopDemoReport()).append('\n');
            double sales = 0;
            int count = 0;
            for (Order order : orderService.getAllOrders()) {
                count++;
                sales += order.getTotal();
            }
            builder.append("Orders completed: ").append(count).append('\n');
            builder.append("Total sales: ").append(String.format("%.2f", sales));
            area.setText(builder.toString());
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, ex.getMessage(), "Report Error", JOptionPane.ERROR_MESSAGE);
        }
    }
}

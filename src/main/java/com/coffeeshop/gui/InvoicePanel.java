package com.coffeeshop.gui;

import com.coffeeshop.models.Order;
import com.coffeeshop.services.InvoiceService;
import com.coffeeshop.services.OrderService;

import javax.swing.JButton;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.table.DefaultTableModel;
import java.awt.BorderLayout;
import java.util.ArrayList;
import java.util.List;

/**
 * Panel for viewing orders, invoice details, and sales totals.
 */
public class InvoicePanel extends JPanel {
    private final OrderService orderService = new OrderService();
    private final InvoiceService invoiceService = new InvoiceService();
    private final DefaultTableModel model = new DefaultTableModel(new String[]{"رقم الفاتورة", "الموظف", "التاريخ", "المجموع الفرعي", "الضريبة", "الخصم", "الإجمالي"}, 0);
    private final JTable table = new JTable(model);
    private final JTextArea detailsArea = new JTextArea();
    private final List<Order> orders = new ArrayList<>();

    /**
     * Creates the invoice panel.
     */
    public InvoicePanel() {
        setLayout(new BorderLayout(8, 8));
        UiTheme.page(this);
        JButton refreshButton = new JButton("تحديث");
        UiTheme.refreshButton(refreshButton);
        JPanel top = new JPanel(new BorderLayout());
        top.setBackground(UiTheme.BACKGROUND);
        top.add(UiTheme.title("الفواتير والمبيعات"), BorderLayout.LINE_START);
        top.add(refreshButton, BorderLayout.LINE_END);
        add(top, BorderLayout.NORTH);
        UiTheme.table(table);
        JScrollPane tableScroll = new JScrollPane(table);
        UiTheme.scroll(tableScroll);
        add(tableScroll, BorderLayout.CENTER);
        detailsArea.setRows(10);
        detailsArea.setEditable(false);
        UiTheme.textArea(detailsArea);
        JScrollPane detailsScroll = new JScrollPane(detailsArea);
        UiTheme.scroll(detailsScroll);
        add(detailsScroll, BorderLayout.SOUTH);
        refreshButton.addActionListener(event -> refresh());
        table.getSelectionModel().addListSelectionListener(event -> showSelected());
        UiTheme.rtl(this);
        refresh();
    }

    private void refresh() {
        try {
            orders.clear();
            orders.addAll(orderService.getAllOrders());
            model.setRowCount(0);
            double sales = 0;
            for (Order order : orders) {
                sales += order.getTotal();
                model.addRow(new Object[]{order.getId(), order.getCashier().getName(), order.getCreatedAt(),
                        order.getSubtotal(), order.getTax(), order.getDiscount(), order.getTotal()});
            }
            detailsArea.setText("إجمالي المبيعات: " + String.format("%.2f", sales));
        } catch (Exception ex) {
            showError(ex);
        }
    }

    private void showSelected() {
        int row = table.getSelectedRow();
        if (row >= 0 && row < orders.size()) {
            detailsArea.setText(invoiceService.createInvoiceText(orders.get(row)));
        }
    }

    private void showError(Exception ex) {
        JOptionPane.showMessageDialog(this, ex.getMessage(), "خطأ في الفواتير", JOptionPane.ERROR_MESSAGE);
    }
}

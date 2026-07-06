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
    private final DefaultTableModel model = new DefaultTableModel(new String[]{"ID", "Cashier", "Date", "Subtotal", "Tax", "Discount", "Total"}, 0);
    private final JTable table = new JTable(model);
    private final JTextArea detailsArea = new JTextArea();
    private final List<Order> orders = new ArrayList<>();

    /**
     * Creates the invoice panel.
     */
    public InvoicePanel() {
        setLayout(new BorderLayout(8, 8));
        JButton refreshButton = new JButton("Refresh");
        add(refreshButton, BorderLayout.NORTH);
        add(new JScrollPane(table), BorderLayout.CENTER);
        detailsArea.setRows(10);
        detailsArea.setEditable(false);
        add(new JScrollPane(detailsArea), BorderLayout.SOUTH);
        refreshButton.addActionListener(event -> refresh());
        table.getSelectionModel().addListSelectionListener(event -> showSelected());
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
            detailsArea.setText("Total sales: " + String.format("%.2f", sales));
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
        JOptionPane.showMessageDialog(this, ex.getMessage(), "Invoice Error", JOptionPane.ERROR_MESSAGE);
    }
}

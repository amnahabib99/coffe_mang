package com.coffeeshop.gui;

import com.coffeeshop.enums.CustomerType;
import com.coffeeshop.models.Customer;
import com.coffeeshop.models.Order;
import com.coffeeshop.models.Product;
import com.coffeeshop.models.VIPCustomer;
import com.coffeeshop.services.CustomerService;
import com.coffeeshop.services.InvoiceService;
import com.coffeeshop.services.OrderService;
import com.coffeeshop.services.ProductService;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;
import java.awt.BorderLayout;
import java.awt.GridLayout;

/**
 * Sales panel where employees and managers create and complete orders.
 */
public class OrderPanel extends JPanel {
    private final ProductService productService = new ProductService();
    private final CustomerService customerService = new CustomerService();
    private final OrderService orderService = new OrderService();
    private final InvoiceService invoiceService = new InvoiceService();
    private final JComboBox<Product> productBox = new JComboBox<>();
    private final JTextField quantityField = new JTextField("1");
    private final JTextField customerNameField = new JTextField();
    private final JTextField customerPhoneField = new JTextField();
    private final JComboBox<CustomerType> customerTypeBox = new JComboBox<>(CustomerType.values());
    private final DefaultTableModel model = new DefaultTableModel(new String[]{"المنتج", "الكمية", "سعر الوحدة", "الإجمالي"}, 0);
    private final JTable table = new JTable(model);
    private final JLabel subtotalLabel = new JLabel("المجموع الفرعي: 0.00");
    private final JLabel taxLabel = new JLabel("الضريبة: 0.00");
    private final JLabel discountLabel = new JLabel("الخصم: 0.00");
    private final JLabel totalLabel = new JLabel("الإجمالي النهائي: 0.00");
    private final JTextArea invoiceArea = new JTextArea();
    private Order currentOrder;

    /**
     * Creates the order panel.
     */
    public OrderPanel() {
        setLayout(new BorderLayout(8, 8));
        UiTheme.page(this);
        buildTop();
        UiTheme.table(table);
        JScrollPane tableScroll = new JScrollPane(table);
        UiTheme.scroll(tableScroll);
        add(tableScroll, BorderLayout.CENTER);
        buildBottom();
        UiTheme.rtl(this);
        startOrder();
        loadProducts();
    }

    private void buildTop() {
        JPanel wrapper = new JPanel(new BorderLayout(8, 8));
        wrapper.setBackground(UiTheme.BACKGROUND);
        wrapper.add(UiTheme.title("إنشاء طلب جديد"), BorderLayout.NORTH);
        JPanel panel = new JPanel(new GridLayout(2, 6, 6, 6));
        panel.setBackground(UiTheme.SURFACE);
        UiTheme.combo(productBox);
        UiTheme.combo(customerTypeBox);
        UiTheme.field(quantityField);
        UiTheme.field(customerNameField);
        UiTheme.field(customerPhoneField);
        addHeader(panel, "المنتج");
        addHeader(panel, "الكمية");
        addHeader(panel, "اسم العميل");
        addHeader(panel, "رقم الهاتف");
        addHeader(panel, "نوع العميل");
        panel.add(new JLabel());
        panel.add(productBox);
        panel.add(quantityField);
        panel.add(customerNameField);
        panel.add(customerPhoneField);
        panel.add(customerTypeBox);
        JButton addButton = new JButton("إضافة للطلب");
        UiTheme.saveButton(addButton);
        panel.add(addButton);
        wrapper.add(UiTheme.card(panel), BorderLayout.CENTER);
        add(wrapper, BorderLayout.NORTH);
        addButton.addActionListener(event -> addItem());
    }

    private void buildBottom() {
        JPanel bottom = new JPanel(new BorderLayout());
        bottom.setBackground(UiTheme.BACKGROUND);
        JPanel totals = new JPanel();
        totals.setBackground(UiTheme.BACKGROUND);
        JButton removeButton = new JButton("حذف من الطلب");
        JButton completeButton = new JButton("إتمام الطلب");
        JButton newButton = new JButton("طلب جديد");
        UiTheme.dangerButton(removeButton);
        UiTheme.saveButton(completeButton);
        UiTheme.button(newButton, false);
        totals.add(subtotalLabel);
        totals.add(taxLabel);
        totals.add(discountLabel);
        totals.add(totalLabel);
        totals.add(removeButton);
        totals.add(completeButton);
        totals.add(newButton);
        invoiceArea.setRows(8);
        invoiceArea.setEditable(false);
        UiTheme.textArea(invoiceArea);
        bottom.add(totals, BorderLayout.NORTH);
        JScrollPane invoiceScroll = new JScrollPane(invoiceArea);
        UiTheme.scroll(invoiceScroll);
        bottom.add(invoiceScroll, BorderLayout.CENTER);
        add(bottom, BorderLayout.SOUTH);
        removeButton.addActionListener(event -> removeItem());
        completeButton.addActionListener(event -> completeOrder());
        newButton.addActionListener(event -> startOrder());
    }

    private void addHeader(JPanel panel, String text) {
        JLabel label = new JLabel(text);
        UiTheme.label(label);
        panel.add(label);
    }

    private void loadProducts() {
        try {
            productBox.removeAllItems();
            for (Product product : productService.getAll()) {
                productBox.addItem(product);
            }
        } catch (Exception ex) {
            showError(ex);
        }
    }

    private void startOrder() {
        try {
            currentOrder = orderService.startOrder();
            model.setRowCount(0);
            invoiceArea.setText("");
            updateTotals();
        } catch (Exception ex) {
            showError(ex);
        }
    }

    private void addItem() {
        try {
            orderService.addProduct(currentOrder, (Product) productBox.getSelectedItem(), Integer.parseInt(quantityField.getText()));
            refreshOrderTable();
        } catch (Exception ex) {
            showError(ex);
        }
    }

    private void removeItem() {
        int row = table.getSelectedRow();
        if (row >= 0) {
            currentOrder.removeItem(row);
            refreshOrderTable();
        }
    }

    private void completeOrder() {
        try {
            Customer customer = buildCustomer();
            if (customer != null) {
                customer = customerService.save(customer);
            }
            Order saved = orderService.completeOrder(currentOrder, customer);
            String invoiceText = invoiceService.createInvoiceText(saved);
            JOptionPane.showMessageDialog(this, "تم إتمام الطلب وإنشاء الفاتورة.");
            startOrder();
            invoiceArea.setText(invoiceText);
        } catch (Exception ex) {
            showError(ex);
        }
    }

    private Customer buildCustomer() throws Exception {
        String name = customerNameField.getText().trim();
        if (name.isEmpty()) {
            return null;
        }
        if (customerTypeBox.getSelectedItem() == CustomerType.VIP) {
            return new VIPCustomer(0, name, customerPhoneField.getText());
        }
        return new Customer(name, customerPhoneField.getText());
    }

    private void refreshOrderTable() {
        model.setRowCount(0);
        for (Order.OrderItem item : currentOrder.getItems()) {
            model.addRow(new Object[]{item.getProductName(), item.getQuantity(), item.getUnitPrice(), item.getLineTotal()});
        }
        updateTotals();
    }

    private void updateTotals() {
        subtotalLabel.setText(String.format("المجموع الفرعي: %.2f", currentOrder.getSubtotal()));
        taxLabel.setText(String.format("الضريبة: %.2f", currentOrder.getTax()));
        discountLabel.setText(String.format("الخصم: %.2f", currentOrder.getDiscount()));
        totalLabel.setText(String.format("الإجمالي النهائي: %.2f", currentOrder.getTotal()));
    }

    private void showError(Exception ex) {
        JOptionPane.showMessageDialog(this, ex.getMessage(), "خطأ في الطلب", JOptionPane.ERROR_MESSAGE);
    }
}

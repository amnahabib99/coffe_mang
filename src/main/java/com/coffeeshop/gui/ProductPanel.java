package com.coffeeshop.gui;

import com.coffeeshop.enums.ProductStatus;
import com.coffeeshop.models.Category;
import com.coffeeshop.models.Product;
import com.coffeeshop.services.CategoryService;
import com.coffeeshop.services.ProductService;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;
import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.util.ArrayList;
import java.util.List;

/**
 * Manager panel for product management and adding new products.
 */
public class ProductPanel extends JPanel {
    private final ProductService productService = new ProductService();
    private final CategoryService categoryService = new CategoryService();
    private final JTextField idField = new JTextField();
    private final JTextField nameField = new JTextField();
    private final JComboBox<Category> categoryBox = new JComboBox<>();
    private final JTextField priceField = new JTextField();
    private final JTextField sizeField = new JTextField();
    private final JComboBox<ProductStatus> statusBox = new JComboBox<>(ProductStatus.values());
    private final DefaultTableModel model = new DefaultTableModel(new String[]{"الرقم", "اسم المنتج", "التصنيف", "السعر", "الحجم", "الحالة"}, 0);
    private final JTable table = new JTable(model);
    private final List<Product> products = new ArrayList<>();

    /**
     * Creates the product panel.
     */
    public ProductPanel() {
        setLayout(new BorderLayout(8, 8));
        UiTheme.page(this);
        idField.setEditable(false);
        buildForm();
        UiTheme.table(table);
        JScrollPane scrollPane = new JScrollPane(table);
        UiTheme.scroll(scrollPane);
        add(scrollPane, BorderLayout.CENTER);
        table.getSelectionModel().addListSelectionListener(event -> loadSelected());
        refresh();
    }

    private void buildForm() {
        add(UiTheme.title("إدارة المنتجات"), BorderLayout.NORTH);
        JPanel form = new JPanel(new GridLayout(2, 8, 6, 6));
        form.setBackground(UiTheme.SURFACE);
        UiTheme.field(idField);
        UiTheme.field(nameField);
        UiTheme.field(priceField);
        UiTheme.field(sizeField);
        UiTheme.combo(categoryBox);
        UiTheme.combo(statusBox);
        addHeader(form, "الرقم");
        addHeader(form, "اسم المنتج");
        addHeader(form, "التصنيف");
        addHeader(form, "السعر");
        addHeader(form, "الحجم");
        addHeader(form, "الحالة");
        form.add(new JLabel());
        form.add(new JLabel());
        form.add(idField);
        form.add(nameField);
        form.add(categoryBox);
        form.add(priceField);
        form.add(sizeField);
        form.add(statusBox);
        JButton addButton = new JButton("إضافة");
        JButton updateButton = new JButton("تحديث");
        JButton deleteButton = new JButton("حذف");
        UiTheme.button(addButton, true);
        UiTheme.button(updateButton, false);
        UiTheme.dangerButton(deleteButton);
        form.add(addButton);
        form.add(updateButton);
        form.add(deleteButton);
        add(UiTheme.card(form), BorderLayout.SOUTH);
        addButton.addActionListener(event -> addProduct());
        updateButton.addActionListener(event -> updateProduct());
        deleteButton.addActionListener(event -> deleteProduct());
    }

    private void addHeader(JPanel form, String text) {
        JLabel label = new JLabel(text);
        UiTheme.label(label);
        form.add(label);
    }

    private void addProduct() {
        try {
            Product product = readProduct(0);
            productService.addProduct(product);
            clear();
            refresh();
            JOptionPane.showMessageDialog(this, "تمت إضافة المنتج بنجاح.");
        } catch (Exception ex) {
            showError(ex);
        }
    }

    private void updateProduct() {
        try {
            productService.update(readProduct(Integer.parseInt(idField.getText())));
            clear();
            refresh();
            JOptionPane.showMessageDialog(this, "تم تحديث المنتج بنجاح.");
        } catch (Exception ex) {
            showError(ex);
        }
    }

    private void deleteProduct() {
        try {
            productService.delete(Integer.parseInt(idField.getText()));
            clear();
            refresh();
            JOptionPane.showMessageDialog(this, "تم حذف المنتج بنجاح.");
        } catch (Exception ex) {
            showError(ex);
        }
    }

    private Product readProduct(int id) throws Exception {
        return new Product(id, nameField.getText(), (Category) categoryBox.getSelectedItem(),
                Double.parseDouble(priceField.getText()), sizeField.getText(), (ProductStatus) statusBox.getSelectedItem());
    }

    private void refresh() {
        try {
            categoryBox.removeAllItems();
            for (Category category : categoryService.getAll()) {
                categoryBox.addItem(category);
            }
            products.clear();
            products.addAll(productService.getAll());
            model.setRowCount(0);
            for (Product product : products) {
                model.addRow(new Object[]{product.getId(), product.getName(), product.getCategory().getName(),
                        product.getPrice(), product.getSize(), product.getStatus()});
            }
        } catch (Exception ex) {
            showError(ex);
        }
    }

    private void loadSelected() {
        int row = table.getSelectedRow();
        if (row >= 0 && row < products.size()) {
            Product product = products.get(row);
            idField.setText(String.valueOf(product.getId()));
            nameField.setText(product.getName());
            priceField.setText(String.valueOf(product.getPrice()));
            sizeField.setText(product.getSize());
            statusBox.setSelectedItem(product.getStatus());
            for (int i = 0; i < categoryBox.getItemCount(); i++) {
                if (categoryBox.getItemAt(i).getId() == product.getCategory().getId()) {
                    categoryBox.setSelectedIndex(i);
                    break;
                }
            }
        }
    }

    private void clear() {
        idField.setText("");
        nameField.setText("");
        priceField.setText("");
        sizeField.setText("");
    }

    private void showError(Exception ex) {
        JOptionPane.showMessageDialog(this, ex.getMessage(), "خطأ في المنتجات", JOptionPane.ERROR_MESSAGE);
    }
}

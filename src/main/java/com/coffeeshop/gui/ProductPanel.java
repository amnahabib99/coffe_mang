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
import java.awt.ComponentOrientation;
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
    private final DefaultTableModel model = new DefaultTableModel(
            new String[]{"الرقم", "اسم المنتج", "التصنيف", "السعر", "الحجم", "الحالة"}, 0);
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
        UiTheme.rtl(this);
        refresh();
    }

    private void buildForm() {
        add(UiTheme.title("إدارة المنتجات"), BorderLayout.NORTH);

        JPanel form = new JPanel(new BorderLayout(12, 8));
        form.setBackground(UiTheme.SURFACE);
        form.setComponentOrientation(ComponentOrientation.RIGHT_TO_LEFT);

        UiTheme.field(idField);
        UiTheme.field(nameField);
        UiTheme.field(priceField);
        UiTheme.field(sizeField);
        UiTheme.combo(categoryBox);
        UiTheme.combo(statusBox);

        JPanel fields = new JPanel(new GridLayout(1, 6, 8, 8));
        fields.setBackground(UiTheme.SURFACE);
        fields.setComponentOrientation(ComponentOrientation.RIGHT_TO_LEFT);
        fields.add(fieldGroup("الرقم", idField));
        fields.add(fieldGroup("اسم المنتج", nameField));
        fields.add(fieldGroup("التصنيف", categoryBox));
        fields.add(fieldGroup("السعر", priceField));
        fields.add(fieldGroup("الحجم", sizeField));
        fields.add(fieldGroup("الحالة", statusBox));

        JButton addButton = new JButton("إضافة");
        JButton updateButton = new JButton("تحديث");
        JButton deleteButton = new JButton("حذف");
        UiTheme.saveButton(addButton);
        UiTheme.refreshButton(updateButton);
        UiTheme.dangerButton(deleteButton);

        JPanel actions = new JPanel(new GridLayout(1, 3, 8, 8));
        actions.setBackground(UiTheme.SURFACE);
        actions.add(deleteButton);
        actions.add(updateButton);
        actions.add(addButton);

        form.add(fields, BorderLayout.CENTER);
        form.add(actions, BorderLayout.WEST);
        add(UiTheme.card(form), BorderLayout.SOUTH);

        addButton.addActionListener(event -> addProduct());
        updateButton.addActionListener(event -> updateProduct());
        deleteButton.addActionListener(event -> deleteProduct());
    }

    private JPanel fieldGroup(String text, java.awt.Component field) {
        JPanel group = new JPanel(new BorderLayout(4, 4));
        group.setBackground(UiTheme.SURFACE);
        group.setComponentOrientation(ComponentOrientation.RIGHT_TO_LEFT);
        JLabel label = new JLabel(text);
        UiTheme.label(label);
        group.add(label, BorderLayout.NORTH);
        group.add(field, BorderLayout.CENTER);
        return group;
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

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
    private final DefaultTableModel model = new DefaultTableModel(new String[]{"ID", "Name", "Category", "Price", "Size", "Status"}, 0);
    private final JTable table = new JTable(model);
    private final List<Product> products = new ArrayList<>();

    /**
     * Creates the product panel.
     */
    public ProductPanel() {
        setLayout(new BorderLayout(8, 8));
        idField.setEditable(false);
        buildForm();
        add(new JScrollPane(table), BorderLayout.CENTER);
        table.getSelectionModel().addListSelectionListener(event -> loadSelected());
        refresh();
    }

    private void buildForm() {
        JPanel form = new JPanel(new GridLayout(2, 8, 6, 6));
        form.add(new JLabel("ID"));
        form.add(new JLabel("Name"));
        form.add(new JLabel("Category"));
        form.add(new JLabel("Price"));
        form.add(new JLabel("Size"));
        form.add(new JLabel("Status"));
        form.add(new JLabel());
        form.add(new JLabel());
        form.add(idField);
        form.add(nameField);
        form.add(categoryBox);
        form.add(priceField);
        form.add(sizeField);
        form.add(statusBox);
        JButton addButton = new JButton("Add");
        JButton updateButton = new JButton("Update");
        JButton deleteButton = new JButton("Delete");
        form.add(addButton);
        form.add(updateButton);
        form.add(deleteButton);
        add(form, BorderLayout.NORTH);
        addButton.addActionListener(event -> addProduct());
        updateButton.addActionListener(event -> updateProduct());
        deleteButton.addActionListener(event -> deleteProduct());
    }

    private void addProduct() {
        try {
            Product product = readProduct(0);
            productService.addProduct(product);
            clear();
            refresh();
            JOptionPane.showMessageDialog(this, "Product added.");
        } catch (Exception ex) {
            showError(ex);
        }
    }

    private void updateProduct() {
        try {
            productService.update(readProduct(Integer.parseInt(idField.getText())));
            clear();
            refresh();
            JOptionPane.showMessageDialog(this, "Product updated.");
        } catch (Exception ex) {
            showError(ex);
        }
    }

    private void deleteProduct() {
        try {
            productService.delete(Integer.parseInt(idField.getText()));
            clear();
            refresh();
            JOptionPane.showMessageDialog(this, "Product deleted.");
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
        JOptionPane.showMessageDialog(this, ex.getMessage(), "Product Error", JOptionPane.ERROR_MESSAGE);
    }
}

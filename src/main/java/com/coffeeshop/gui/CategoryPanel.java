package com.coffeeshop.gui;

import com.coffeeshop.models.Category;
import com.coffeeshop.services.CategoryService;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;
import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.util.List;

/**
 * Manager panel for category CRUD operations.
 */
public class CategoryPanel extends JPanel {
    private final CategoryService service = new CategoryService();
    private final JTextField idField = new JTextField();
    private final JTextField nameField = new JTextField();
    private final JTextField descriptionField = new JTextField();
    private final DefaultTableModel model = new DefaultTableModel(new String[]{"ID", "Name", "Description"}, 0);
    private final JTable table = new JTable(model);

    /**
     * Creates the category panel.
     */
    public CategoryPanel() {
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
        add(UiTheme.title("Category Management"), BorderLayout.NORTH);
        JPanel form = new JPanel(new GridLayout(2, 5, 6, 6));
        form.setBackground(UiTheme.SURFACE);
        UiTheme.field(idField);
        UiTheme.field(nameField);
        UiTheme.field(descriptionField);
        addHeader(form, "ID");
        addHeader(form, "Name");
        addHeader(form, "Description");
        form.add(new JLabel());
        form.add(new JLabel());
        form.add(idField);
        form.add(nameField);
        form.add(descriptionField);
        JButton addButton = new JButton("Add");
        JButton updateButton = new JButton("Update");
        JButton deleteButton = new JButton("Delete");
        UiTheme.button(addButton, true);
        UiTheme.button(updateButton, false);
        UiTheme.dangerButton(deleteButton);
        form.add(addButton);
        form.add(updateButton);
        form.add(deleteButton);
        add(UiTheme.card(form), BorderLayout.SOUTH);
        addButton.addActionListener(event -> addCategory());
        updateButton.addActionListener(event -> updateCategory());
        deleteButton.addActionListener(event -> deleteCategory());
    }

    private void addHeader(JPanel form, String text) {
        JLabel label = new JLabel(text);
        UiTheme.label(label);
        form.add(label);
    }

    private void addCategory() {
        try {
            service.add(new Category(nameField.getText(), descriptionField.getText()));
            clear();
            refresh();
            JOptionPane.showMessageDialog(this, "Category added.");
        } catch (Exception ex) {
            showError(ex);
        }
    }

    private void updateCategory() {
        try {
            service.update(new Category(Integer.parseInt(idField.getText()), nameField.getText(), descriptionField.getText()));
            clear();
            refresh();
            JOptionPane.showMessageDialog(this, "Category updated.");
        } catch (Exception ex) {
            showError(ex);
        }
    }

    private void deleteCategory() {
        try {
            service.delete(Integer.parseInt(idField.getText()));
            clear();
            refresh();
            JOptionPane.showMessageDialog(this, "Category deleted.");
        } catch (Exception ex) {
            showError(ex);
        }
    }

    private void refresh() {
        try {
            model.setRowCount(0);
            List<Category> categories = service.getAll();
            for (Category category : categories) {
                model.addRow(new Object[]{category.getId(), category.getName(), category.getDescription()});
            }
        } catch (Exception ex) {
            showError(ex);
        }
    }

    private void loadSelected() {
        int row = table.getSelectedRow();
        if (row >= 0) {
            idField.setText(model.getValueAt(row, 0).toString());
            nameField.setText(model.getValueAt(row, 1).toString());
            descriptionField.setText(model.getValueAt(row, 2).toString());
        }
    }

    private void clear() {
        idField.setText("");
        nameField.setText("");
        descriptionField.setText("");
    }

    private void showError(Exception ex) {
        JOptionPane.showMessageDialog(this, ex.getMessage(), "Category Error", JOptionPane.ERROR_MESSAGE);
    }
}

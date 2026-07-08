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
import java.awt.ComponentOrientation;
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
    private final DefaultTableModel model = new DefaultTableModel(new String[]{"الرقم", "اسم التصنيف", "الوصف"}, 0);
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
        UiTheme.rtl(this);
        refresh();
    }

    private void buildForm() {
        add(UiTheme.title("إدارة التصنيفات"), BorderLayout.NORTH);

        JPanel form = new JPanel(new BorderLayout(12, 8));
        form.setBackground(UiTheme.SURFACE);
        form.setComponentOrientation(ComponentOrientation.RIGHT_TO_LEFT);

        UiTheme.field(idField);
        UiTheme.field(nameField);
        UiTheme.field(descriptionField);

        JPanel fields = new JPanel(new GridLayout(1, 3, 8, 8));
        fields.setBackground(UiTheme.SURFACE);
        fields.setComponentOrientation(ComponentOrientation.RIGHT_TO_LEFT);
        fields.add(fieldGroup("الرقم", idField));
        fields.add(fieldGroup("اسم التصنيف", nameField));
        fields.add(fieldGroup("الوصف", descriptionField));

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

        addButton.addActionListener(event -> addCategory());
        updateButton.addActionListener(event -> updateCategory());
        deleteButton.addActionListener(event -> deleteCategory());
    }

    private JPanel fieldGroup(String text, JTextField field) {
        JPanel group = new JPanel(new BorderLayout(4, 4));
        group.setBackground(UiTheme.SURFACE);
        group.setComponentOrientation(ComponentOrientation.RIGHT_TO_LEFT);
        JLabel label = new JLabel(text);
        UiTheme.label(label);
        group.add(label, BorderLayout.NORTH);
        group.add(field, BorderLayout.CENTER);
        return group;
    }

    private void addCategory() {
        try {
            service.add(new Category(nameField.getText(), descriptionField.getText()));
            clear();
            refresh();
            JOptionPane.showMessageDialog(this, "تمت إضافة التصنيف بنجاح.");
        } catch (Exception ex) {
            showError(ex);
        }
    }

    private void updateCategory() {
        try {
            service.update(new Category(Integer.parseInt(idField.getText()), nameField.getText(), descriptionField.getText()));
            clear();
            refresh();
            JOptionPane.showMessageDialog(this, "تم تحديث التصنيف بنجاح.");
        } catch (Exception ex) {
            showError(ex);
        }
    }

    private void deleteCategory() {
        try {
            service.delete(Integer.parseInt(idField.getText()));
            clear();
            refresh();
            JOptionPane.showMessageDialog(this, "تم حذف التصنيف بنجاح.");
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
        JOptionPane.showMessageDialog(this, ex.getMessage(), "خطأ في التصنيفات", JOptionPane.ERROR_MESSAGE);
    }
}

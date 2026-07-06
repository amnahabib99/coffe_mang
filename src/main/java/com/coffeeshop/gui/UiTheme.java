package com.coffeeshop.gui;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.UIManager;
import javax.swing.plaf.basic.BasicButtonUI;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.JTableHeader;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.ComponentOrientation;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Insets;
import java.awt.Container;

/**
 * Shared Swing styling helpers for a cleaner, consistent cafe interface.
 */
public final class UiTheme {
    /** Warm page background. */
    public static final Color BACKGROUND = Color.WHITE;
    /** Panel surface color. */
    public static final Color SURFACE = Color.WHITE;
    /** Primary coffee accent. */
    public static final Color PRIMARY = new Color(178, 34, 34);
    /** Secondary teal accent. */
    public static final Color ACCENT = new Color(178, 34, 34);
    /** Text color. */
    public static final Color TEXT = new Color(178, 34, 34);
    /** Muted border color. */
    public static final Color BORDER = new Color(178, 34, 34);
    /** Soft danger color. */
    public static final Color DANGER = new Color(178, 34, 34);
    /** Green used for save and confirmation buttons only. */
    public static final Color SAVE = new Color(34, 139, 34);
    /** Cyan used only for refresh/update actions. */
    public static final Color REFRESH = new Color(0, 150, 180);
    /** Arabic-friendly application font family. */
    public static final String FONT_NAME = "Tahoma";

    private UiTheme() {
    }

    /**
     * Applies application-wide UI defaults before building windows.
     */
    public static void configureDefaults() {
        Font base = new Font(FONT_NAME, Font.PLAIN, 14);
        UIManager.put("Label.font", base);
        UIManager.put("Button.font", new Font(FONT_NAME, Font.BOLD, 14));
        UIManager.put("TextField.font", base);
        UIManager.put("PasswordField.font", base);
        UIManager.put("ComboBox.font", base);
        UIManager.put("Table.font", base);
        UIManager.put("TableHeader.font", new Font(FONT_NAME, Font.BOLD, 14));
        UIManager.put("OptionPane.messageFont", base);
        UIManager.put("OptionPane.buttonFont", new Font(FONT_NAME, Font.BOLD, 13));
    }

    /**
     * Styles a page panel with consistent spacing.
     *
     * @param panel panel
     */
    public static void page(JPanel panel) {
        panel.setBackground(BACKGROUND);
        panel.setBorder(BorderFactory.createEmptyBorder(18, 18, 18, 18));
        panel.setComponentOrientation(ComponentOrientation.RIGHT_TO_LEFT);
    }

    /**
     * Creates a section title.
     *
     * @param text title text
     * @return styled label
     */
    public static JLabel title(String text) {
        JLabel label = new JLabel(text);
        label.setFont(new Font(FONT_NAME, Font.BOLD, 20));
        label.setForeground(PRIMARY);
        label.setBorder(BorderFactory.createEmptyBorder(0, 0, 12, 0));
        label.setHorizontalAlignment(SwingConstants.RIGHT);
        label.setComponentOrientation(ComponentOrientation.RIGHT_TO_LEFT);
        return label;
    }

    /**
     * Creates a small descriptive text label.
     *
     * @param text text
     * @return label
     */
    public static JLabel subtitle(String text) {
        JLabel label = new JLabel(text);
        label.setFont(new Font(FONT_NAME, Font.PLAIN, 14));
        label.setForeground(PRIMARY);
        label.setHorizontalAlignment(SwingConstants.RIGHT);
        label.setComponentOrientation(ComponentOrientation.RIGHT_TO_LEFT);
        return label;
    }

    /**
     * Wraps content in a white card-like surface.
     *
     * @param content component
     * @return wrapper panel
     */
    public static JPanel card(Component content) {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBackground(SURFACE);
        panel.setComponentOrientation(ComponentOrientation.RIGHT_TO_LEFT);
        panel.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(BORDER),
                BorderFactory.createEmptyBorder(14, 14, 14, 14)));
        panel.add(content, BorderLayout.CENTER);
        rtl(panel);
        return panel;
    }

    /**
     * Styles a standard text field.
     *
     * @param field text field
     */
    public static void field(JTextField field) {
        field.setFont(new Font(FONT_NAME, Font.PLAIN, 14));
        field.setComponentOrientation(ComponentOrientation.RIGHT_TO_LEFT);
        field.setHorizontalAlignment(JTextField.RIGHT);
        field.setMargin(new Insets(6, 8, 6, 8));
        field.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(BORDER),
                BorderFactory.createEmptyBorder(4, 6, 4, 6)));
    }

    /**
     * Styles a combo box.
     *
     * @param combo combo box
     */
    public static void combo(JComboBox<?> combo) {
        combo.setFont(new Font(FONT_NAME, Font.PLAIN, 15));
        combo.setBackground(SURFACE);
        combo.setComponentOrientation(ComponentOrientation.RIGHT_TO_LEFT);
    }

    /**
     * Styles labels in forms.
     *
     * @param label label
     */
    public static void label(JLabel label) {
        label.setFont(new Font(FONT_NAME, Font.BOLD, 14));
        label.setForeground(TEXT);
        label.setHorizontalAlignment(SwingConstants.RIGHT);
        label.setComponentOrientation(ComponentOrientation.RIGHT_TO_LEFT);
    }

    /**
     * Styles a primary or secondary button.
     *
     * @param button button
     * @param primary true for primary action
     */
    public static void button(JButton button, boolean primary) {
        button.setFocusPainted(false);
        button.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        button.setUI(new BasicButtonUI());
        button.setFont(new Font(FONT_NAME, Font.BOLD, 14));
        button.setBorder(BorderFactory.createEmptyBorder(9, 16, 9, 16));
        button.setBackground(primary ? PRIMARY : ACCENT);
        button.setForeground(Color.WHITE);
        button.setComponentOrientation(ComponentOrientation.RIGHT_TO_LEFT);
        button.setOpaque(true);
        button.setContentAreaFilled(true);
        button.setPreferredSize(new Dimension(180, 42));
        button.setMinimumSize(new Dimension(150, 40));
    }

    /**
     * Styles a destructive button.
     *
     * @param button button
     */
    public static void dangerButton(JButton button) {
        button(button, false);
        button.setBackground(DANGER);
    }

    /**
     * Styles save and confirmation buttons in green.
     *
     * @param button button
     */
    public static void saveButton(JButton button) {
        button(button, true);
        button.setBackground(SAVE);
    }

    /**
     * Styles refresh/update buttons in cyan.
     *
     * @param button button
     */
    public static void refreshButton(JButton button) {
        button(button, false);
        button.setBackground(REFRESH);
    }

    /**
     * Styles a table and its scroll pane.
     *
     * @param table table
     */
    public static void table(JTable table) {
        table.setFont(new Font(FONT_NAME, Font.PLAIN, 14));
        table.setRowHeight(32);
        table.setGridColor(PRIMARY);
        table.setSelectionBackground(PRIMARY);
        table.setSelectionForeground(Color.WHITE);
        table.setShowVerticalLines(false);
        table.setComponentOrientation(ComponentOrientation.RIGHT_TO_LEFT);
        DefaultTableCellRenderer renderer = new DefaultTableCellRenderer();
        renderer.setHorizontalAlignment(SwingConstants.RIGHT);
        table.setDefaultRenderer(Object.class, renderer);
        JTableHeader header = table.getTableHeader();
        header.setFont(new Font(FONT_NAME, Font.BOLD, 14));
        header.setBackground(PRIMARY);
        header.setForeground(Color.WHITE);
        header.setComponentOrientation(ComponentOrientation.RIGHT_TO_LEFT);
    }

    /**
     * Styles a scroll pane.
     *
     * @param scrollPane scroll pane
     */
    public static void scroll(JScrollPane scrollPane) {
        scrollPane.setBorder(BorderFactory.createLineBorder(BORDER));
        scrollPane.getViewport().setBackground(SURFACE);
        scrollPane.setComponentOrientation(ComponentOrientation.RIGHT_TO_LEFT);
    }

    /**
     * Styles a text area used for reports and invoices.
     *
     * @param area text area
     */
    public static void textArea(JTextArea area) {
        area.setFont(new Font(FONT_NAME, Font.PLAIN, 15));
        area.setBackground(Color.WHITE);
        area.setForeground(TEXT);
        area.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        area.setComponentOrientation(ComponentOrientation.RIGHT_TO_LEFT);
    }

    /**
     * Applies right-to-left orientation recursively after a UI tree is built.
     *
     * @param component root component
     */
    public static void rtl(Component component) {
        component.setComponentOrientation(ComponentOrientation.RIGHT_TO_LEFT);
        if (component instanceof JLabel label) {
            label.setHorizontalAlignment(SwingConstants.RIGHT);
        }
        if (component instanceof JTextField field) {
            field.setHorizontalAlignment(JTextField.RIGHT);
        }
        if (component instanceof JTextArea area) {
            area.setComponentOrientation(ComponentOrientation.RIGHT_TO_LEFT);
        }
        if (component instanceof JTable table) {
            table(table);
        }
        if (component instanceof Container container) {
            container.applyComponentOrientation(ComponentOrientation.RIGHT_TO_LEFT);
            for (Component child : container.getComponents()) {
                rtl(child);
            }
        }
    }

    /**
     * Applies common font to a component tree.
     *
     * @param component root component
     */
    public static void applyFont(Component component) {
        component.setFont(new Font(FONT_NAME, Font.PLAIN, 14));
        component.setComponentOrientation(ComponentOrientation.RIGHT_TO_LEFT);
        if (component instanceof JComponent jComponent) {
            jComponent.setOpaque(component instanceof JPanel || component instanceof JLabel ? jComponent.isOpaque() : jComponent.isOpaque());
        }
        if (component instanceof java.awt.Container container) {
            for (Component child : container.getComponents()) {
                applyFont(child);
            }
        }
    }
}

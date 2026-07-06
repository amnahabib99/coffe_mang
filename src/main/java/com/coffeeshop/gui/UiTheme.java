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
import javax.swing.table.JTableHeader;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Cursor;
import java.awt.Font;
import java.awt.Insets;

/**
 * Shared Swing styling helpers for a cleaner, consistent cafe interface.
 */
public final class UiTheme {
    /** Warm page background. */
    public static final Color BACKGROUND = new Color(246, 243, 238);
    /** Panel surface color. */
    public static final Color SURFACE = Color.WHITE;
    /** Primary coffee accent. */
    public static final Color PRIMARY = new Color(78, 52, 46);
    /** Secondary teal accent. */
    public static final Color ACCENT = new Color(0, 121, 107);
    /** Text color. */
    public static final Color TEXT = new Color(38, 38, 38);
    /** Muted border color. */
    public static final Color BORDER = new Color(222, 216, 207);
    /** Soft danger color. */
    public static final Color DANGER = new Color(174, 53, 53);

    private UiTheme() {
    }

    /**
     * Styles a page panel with consistent spacing.
     *
     * @param panel panel
     */
    public static void page(JPanel panel) {
        panel.setBackground(BACKGROUND);
        panel.setBorder(BorderFactory.createEmptyBorder(18, 18, 18, 18));
    }

    /**
     * Creates a section title.
     *
     * @param text title text
     * @return styled label
     */
    public static JLabel title(String text) {
        JLabel label = new JLabel(text);
        label.setFont(new Font("Segoe UI", Font.BOLD, 22));
        label.setForeground(PRIMARY);
        label.setBorder(BorderFactory.createEmptyBorder(0, 0, 12, 0));
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
        label.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        label.setForeground(new Color(96, 86, 78));
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
        panel.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(BORDER),
                BorderFactory.createEmptyBorder(14, 14, 14, 14)));
        panel.add(content, BorderLayout.CENTER);
        return panel;
    }

    /**
     * Styles a standard text field.
     *
     * @param field text field
     */
    public static void field(JTextField field) {
        field.setFont(new Font("Segoe UI", Font.PLAIN, 14));
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
        combo.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        combo.setBackground(SURFACE);
    }

    /**
     * Styles labels in forms.
     *
     * @param label label
     */
    public static void label(JLabel label) {
        label.setFont(new Font("Segoe UI", Font.BOLD, 13));
        label.setForeground(TEXT);
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
        button.setFont(new Font("Segoe UI", Font.BOLD, 13));
        button.setBorder(BorderFactory.createEmptyBorder(8, 14, 8, 14));
        button.setBackground(primary ? PRIMARY : ACCENT);
        button.setForeground(Color.WHITE);
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
     * Styles a table and its scroll pane.
     *
     * @param table table
     */
    public static void table(JTable table) {
        table.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        table.setRowHeight(30);
        table.setGridColor(new Color(236, 232, 226));
        table.setSelectionBackground(new Color(226, 242, 239));
        table.setSelectionForeground(TEXT);
        table.setShowVerticalLines(false);
        JTableHeader header = table.getTableHeader();
        header.setFont(new Font("Segoe UI", Font.BOLD, 13));
        header.setBackground(PRIMARY);
        header.setForeground(Color.WHITE);
    }

    /**
     * Styles a scroll pane.
     *
     * @param scrollPane scroll pane
     */
    public static void scroll(JScrollPane scrollPane) {
        scrollPane.setBorder(BorderFactory.createLineBorder(BORDER));
        scrollPane.getViewport().setBackground(SURFACE);
    }

    /**
     * Styles a text area used for reports and invoices.
     *
     * @param area text area
     */
    public static void textArea(JTextArea area) {
        area.setFont(new Font("Consolas", Font.PLAIN, 13));
        area.setBackground(new Color(255, 253, 249));
        area.setForeground(TEXT);
        area.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
    }

    /**
     * Applies common font to a component tree.
     *
     * @param component root component
     */
    public static void applyFont(Component component) {
        component.setFont(new Font("Segoe UI", Font.PLAIN, 13));
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

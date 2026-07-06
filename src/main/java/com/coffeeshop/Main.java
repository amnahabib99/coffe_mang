package com.coffeeshop;

import com.coffeeshop.database.SchemaInitializer;
import com.coffeeshop.gui.LoginFrame;

import javax.swing.SwingUtilities;
import javax.swing.UIManager;

/**
 * Application entry point for the Coffee Shop Management System.
 */
public class Main {
    /**
     * Starts database initialization and opens the login window.
     *
     * @param args command-line arguments, not used
     */
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            try {
                UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
                com.coffeeshop.gui.UiTheme.configureDefaults();
                SchemaInitializer.initialize();
            } catch (Exception ex) {
                LoginFrame.showStartupError(ex.getMessage());
            }
            new LoginFrame().setVisible(true);
        });
    }
}

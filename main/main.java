package main;

import userinterface.LoginUI;
import javax.swing.SwingUtilities;

/**
 * The main entry point for the application.
 */
public class Main {
    public static void main(String[] args) {
        // Ensure the UI is created on the Event Dispatch Thread (EDT)
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new LoginUI().setVisible(true);
            }
        });
    }
}
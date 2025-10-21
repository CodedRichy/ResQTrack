package userinterface;

import service.SosAlert;
import database.DatabaseService;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;
import java.text.SimpleDateFormat;

public class SosAlertPanel extends JPanel {

    private final DefaultTableModel tableModel;
    private final JTable alertTable;
    private final DatabaseService databaseService;

    public SosAlertPanel() {
        this.setLayout(new BorderLayout());
        this.databaseService = DatabaseService.getInstance();

        String[] columnNames = {"Alert ID", "User ID", "Location", "Timestamp", "Message", "Status"};
        tableModel = new DefaultTableModel(columnNames, 0);
        alertTable = new JTable(tableModel);

        JButton createAlertButton = new JButton("Create SOS Alert");
        createAlertButton.addActionListener(e -> createAlertDialog());

        JButton resolveAlertButton = new JButton("Resolve Alert");
        resolveAlertButton.addActionListener(e -> resolveAlertDialog());

        JPanel buttonPanel = new JPanel();
        buttonPanel.add(createAlertButton);
        buttonPanel.add(resolveAlertButton);

        add(new JScrollPane(alertTable), BorderLayout.CENTER);
        add(buttonPanel, BorderLayout.SOUTH);

        refreshTable();
    }

    private void refreshTable() {
        tableModel.setRowCount(0);
        try {
            List<SosAlert> alertList = databaseService.getAllSosAlerts();
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            for (SosAlert alert : alertList) {
                Object[] row = {
                        alert.getAlertId(),
                        alert.getUserId(),
                        alert.getLocation(),
                        alert.getTimestamp() != null ? dateFormat.format(alert.getTimestamp()) : "N/A",
                        alert.getMessage(),
                        alert.getStatus()
                };
                tableModel.addRow(row);
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Error loading SOS alerts: " + e.getMessage(), 
                "Database Error", JOptionPane.ERROR_MESSAGE);
            e.printStackTrace();
        }
    }

    private void createAlertDialog() {
        JTextField userIdField = new JTextField();
        JTextField locationField = new JTextField();
        JTextField messageField = new JTextField();

        Object[] form = {
                "User ID:", userIdField,
                "Location:", locationField,
                "Message:", messageField
        };

        int option = JOptionPane.showConfirmDialog(this, form, "Create New SOS Alert", JOptionPane.OK_CANCEL_OPTION);
        if (option == JOptionPane.OK_OPTION) {
            try {
                int userId = Integer.parseInt(userIdField.getText());
                String location = locationField.getText();
                String message = messageField.getText();

                if (location.trim().isEmpty() || message.trim().isEmpty()) {
                    JOptionPane.showMessageDialog(this, "Fields cannot be empty.", "Input Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                // Create a temporary SosAlert object for insertion
                // The database will auto-generate the ID
                SosAlert newAlert = new SosAlert(0, userId, location, message);
                boolean success = databaseService.createSosAlert(newAlert);
                
                if (success) {
                    JOptionPane.showMessageDialog(this, "SOS alert created successfully!", "Success", JOptionPane.INFORMATION_MESSAGE);
                    refreshTable();
                } else {
                    JOptionPane.showMessageDialog(this, "Failed to create SOS alert.", "Error", JOptionPane.ERROR_MESSAGE);
                }

            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(this, "Invalid User ID. Please enter a number.", "Format Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    private void resolveAlertDialog() {
        int selectedRow = alertTable.getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(this, "Please select an alert to resolve.", "Selection Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        try {
            // Get the alert ID from the selected row
            int alertId = (Integer) tableModel.getValueAt(selectedRow, 0);
            
            boolean success = databaseService.updateSosAlertStatus(alertId, "Resolved");
            if (success) {
                JOptionPane.showMessageDialog(this, "SOS alert resolved successfully!", "Success", JOptionPane.INFORMATION_MESSAGE);
                refreshTable();
            } else {
                JOptionPane.showMessageDialog(this, "Failed to resolve SOS alert.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Error resolving SOS alert: " + e.getMessage(), "Database Error", JOptionPane.ERROR_MESSAGE);
            e.printStackTrace();
        }
    }
}
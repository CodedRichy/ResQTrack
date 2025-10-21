package userinterface;

import service.SosAlert;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class SosAlertPanel extends JPanel {

    private final DefaultTableModel tableModel;
    private final JTable alertTable;
    private static final List<SosAlert> alertList = new ArrayList<>();

    public SosAlertPanel() {
        this.setLayout(new BorderLayout());

        if (alertList.isEmpty()) {
            alertList.add(new SosAlert(1, 101, "123 Maple St", "Building collapse, assistance needed."));
            alertList.add(new SosAlert(2, 105, "Oakwood Park", "Medical emergency."));
        }

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
        for (SosAlert alert : alertList) {
            Object[] row = {
                    alert.getAlertId(),
                    alert.getUserId(),
                    alert.getLocation(),
                    alert.getTimestamp(),
                    alert.getMessage(),
                    alert.getStatus()
            };
            tableModel.addRow(row);
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

                int alertId = alertList.isEmpty() ? 1 : alertList.get(alertList.size() - 1).getAlertId() + 1;
                SosAlert newAlert = new SosAlert(alertId, userId, location, message);
                alertList.add(newAlert);
                refreshTable();

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

        SosAlert selectedAlert = alertList.get(selectedRow);
        selectedAlert.setStatus("Resolved");
        refreshTable();
    }
}
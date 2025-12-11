package userinterface;

import service.Hazard;
import database.DatabaseService;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

public class HazardPanel extends JPanel implements AdminControllable {

    private final DefaultTableModel tableModel;
    private final JTable hazardTable;
    private final DatabaseService databaseService;

    public HazardPanel() {
        this.setLayout(new BorderLayout());
        this.databaseService = DatabaseService.getInstance();

        String[] columnNames = {"Hazard ID", "Description", "Location", "Status"};
        tableModel = new DefaultTableModel(columnNames, 0);
        hazardTable = new JTable(tableModel);

        JButton createHazardButton = new JButton("Create Hazard");
        createHazardButton.addActionListener(e -> createHazardDialog());

        JButton resolveHazardButton = new JButton("Resolve Hazard");
        resolveHazardButton.addActionListener(e -> resolveHazardDialog());

        JButton deleteHazardButton = new JButton("Delete Hazard");
        deleteHazardButton.addActionListener(e -> deleteHazardDialog());

        JPanel buttonPanel = new JPanel();
        buttonPanel.add(createHazardButton);
        buttonPanel.add(resolveHazardButton);
        buttonPanel.add(deleteHazardButton);

        add(new JScrollPane(hazardTable), BorderLayout.CENTER);
        add(buttonPanel, BorderLayout.SOUTH);

        refreshTable();
    }

    @Override
    public void setAdminMode(boolean isAdmin) {
        if (getComponentCount() >= 2 && getComponent(1) instanceof JPanel) {
            JPanel buttonPanel = (JPanel) getComponent(1);
            for (Component c : buttonPanel.getComponents()) {
                if (c instanceof JButton && ((JButton) c).getText().toLowerCase().contains("delete")) {
                    c.setVisible(isAdmin);
                }
            }
            buttonPanel.revalidate();
            buttonPanel.repaint();
        }
    }

    private void deleteHazardDialog() {
        int selectedRow = hazardTable.getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(this, "Please select a hazard to delete.", "Selection Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        try {
            int hazardId = (Integer) tableModel.getValueAt(selectedRow, 0);
            int confirm = JOptionPane.showConfirmDialog(this,
                    "Are you sure you want to delete Hazard #" + hazardId + "?\nThis action cannot be undone.",
                    "Confirm Delete", JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE);
            if (confirm == JOptionPane.YES_OPTION) {
                boolean success = databaseService.deleteHazard(hazardId);
                if (success) {
                    JOptionPane.showMessageDialog(this, "Hazard deleted successfully!", "Success", JOptionPane.INFORMATION_MESSAGE);
                    refreshTable();
                } else {
                    JOptionPane.showMessageDialog(this, "Failed to delete hazard.", "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Error deleting hazard: " + e.getMessage(), "Database Error", JOptionPane.ERROR_MESSAGE);
            e.printStackTrace();
        }
    }

    private void refreshTable() {
        tableModel.setRowCount(0);
        try {
            List<Hazard> hazardList = databaseService.getAllHazards();
            for (Hazard hazard : hazardList) {
                Object[] row = {
                    hazard.getHazardId(),
                    hazard.getDescription(),
                    hazard.getLocation(),
                    hazard.getStatus()
                };
                tableModel.addRow(row);
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Error loading hazards: " + e.getMessage(), 
                "Database Error", JOptionPane.ERROR_MESSAGE);
            e.printStackTrace();
        }
    }

    private void createHazardDialog() {
        JTextField descriptionField = new JTextField();
        JTextField locationField = new JTextField();

        Object[] form = {
                "Description:", descriptionField,
                "Location:", locationField
        };

        int option = JOptionPane.showConfirmDialog(this, form, "Create New Hazard", JOptionPane.OK_CANCEL_OPTION);
        if (option == JOptionPane.OK_OPTION) {
            String description = descriptionField.getText();
            String location = locationField.getText();

            if (description.trim().isEmpty() || location.trim().isEmpty()) {
                JOptionPane.showMessageDialog(this, "Fields cannot be empty.", "Input Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            try {
                Hazard newHazard = new Hazard(0, description, location);
                boolean success = databaseService.createHazard(newHazard);
                
                if (success) {
                    JOptionPane.showMessageDialog(this, "Hazard created successfully!", "Success", JOptionPane.INFORMATION_MESSAGE);
                    refreshTable();
                } else {
                    JOptionPane.showMessageDialog(this, "Failed to create hazard.", "Error", JOptionPane.ERROR_MESSAGE);
                }
            } catch (Exception e) {
                JOptionPane.showMessageDialog(this, "Error creating hazard: " + e.getMessage(), "Database Error", JOptionPane.ERROR_MESSAGE);
                e.printStackTrace();
            }
        }
    }

    private void resolveHazardDialog() {
        int selectedRow = hazardTable.getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(this, "Please select a hazard to resolve.", "Selection Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        try {
            // Get the hazard ID from the selected row
            int hazardId = (Integer) tableModel.getValueAt(selectedRow, 0);
            
            boolean success = databaseService.updateHazardStatus(hazardId, "Resolved");
            if (success) {
                JOptionPane.showMessageDialog(this, "Hazard resolved successfully!", "Success", JOptionPane.INFORMATION_MESSAGE);
                refreshTable();
            } else {
                JOptionPane.showMessageDialog(this, "Failed to resolve hazard.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Error resolving hazard: " + e.getMessage(), "Database Error", JOptionPane.ERROR_MESSAGE);
            e.printStackTrace();
        }
    }
}
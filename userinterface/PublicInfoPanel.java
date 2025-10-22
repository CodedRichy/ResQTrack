package userinterface;

import service.PublicInfo;
import database.DatabaseService;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

public class PublicInfoPanel extends JPanel implements AdminControllable {

    private final DefaultTableModel tableModel;
    private final JTable infoTable;
    private final DatabaseService databaseService;

    public PublicInfoPanel() {
        this.setLayout(new BorderLayout());
        this.databaseService = DatabaseService.getInstance();

        String[] columnNames = {"ID", "Type", "Description", "Status"};
        tableModel = new DefaultTableModel(columnNames, 0);
        infoTable = new JTable(tableModel);

        JButton createInfoButton = new JButton("Add Info");
        createInfoButton.addActionListener(e -> createInfoDialog());

        JButton archiveInfoButton = new JButton("Archive Info");
        archiveInfoButton.addActionListener(e -> archiveInfoDialog());

        JButton deleteInfoButton = new JButton("Delete Info");
        deleteInfoButton.addActionListener(e -> deleteInfoDialog());

        JPanel buttonPanel = new JPanel();
        buttonPanel.add(createInfoButton);
        buttonPanel.add(archiveInfoButton);
        buttonPanel.add(deleteInfoButton);

        add(new JScrollPane(infoTable), BorderLayout.CENTER);
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

    private void deleteInfoDialog() {
        int selectedRow = infoTable.getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(this, "Please select an item to delete.", "Selection Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        try {
            int infoId = (Integer) tableModel.getValueAt(selectedRow, 0);
            int confirm = JOptionPane.showConfirmDialog(this,
                    "Are you sure you want to delete Info #" + infoId + "?\nThis action cannot be undone.",
                    "Confirm Delete", JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE);
            if (confirm == JOptionPane.YES_OPTION) {
                boolean success = DatabaseService.getInstance().deletePublicInfo(infoId);
                if (success) {
                    JOptionPane.showMessageDialog(this, "Info deleted successfully!", "Success", JOptionPane.INFORMATION_MESSAGE);
                    refreshTable();
                } else {
                    JOptionPane.showMessageDialog(this, "Failed to delete info.", "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Error deleting info: " + e.getMessage(), "Database Error", JOptionPane.ERROR_MESSAGE);
            e.printStackTrace();
        }
    }

    private void refreshTable() {
        tableModel.setRowCount(0);
        try {
            List<PublicInfo> infoList = databaseService.getAllPublicInfo();
            for (PublicInfo item : infoList) {
                Object[] row = {
                    item.getInfoId(),
                    item.getType(),
                    item.getDescription(),
                    item.getStatus()
                };
                tableModel.addRow(row);
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Error loading data from database: " + e.getMessage(), 
                "Database Error", JOptionPane.ERROR_MESSAGE);
            e.printStackTrace();
        }
    }

    private void createInfoDialog() {
        String[] infoTypes = {"Alert", "Guideline", "Shelter", "Contact", "Update"};
        JComboBox<String> typeDropdown = new JComboBox<>(infoTypes);
        JTextField descriptionField = new JTextField();

        Object[] form = {
                "Type:", typeDropdown,
                "Description:", descriptionField
        };

        int option = JOptionPane.showConfirmDialog(this, form, "Add New Info", JOptionPane.OK_CANCEL_OPTION);
        if (option == JOptionPane.OK_OPTION) {
            String type = (String) typeDropdown.getSelectedItem();
            String description = descriptionField.getText();

            if (description.trim().isEmpty()) {
                JOptionPane.showMessageDialog(this, "Description cannot be empty.", "Input Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            try {
                // Create a temporary PublicInfo object for insertion
                // The database will auto-generate the ID
                PublicInfo newItem = new PublicInfo(0, type, description);
                boolean success = databaseService.createPublicInfo(newItem);
                
                if (success) {
                    JOptionPane.showMessageDialog(this, "Public info added successfully!", "Success", JOptionPane.INFORMATION_MESSAGE);
                    refreshTable();
                } else {
                    JOptionPane.showMessageDialog(this, "Failed to add public info.", "Error", JOptionPane.ERROR_MESSAGE);
                }
            } catch (Exception e) {
                JOptionPane.showMessageDialog(this, "Error adding public info: " + e.getMessage(), "Database Error", JOptionPane.ERROR_MESSAGE);
                e.printStackTrace();
            }
        }
    }

    private void archiveInfoDialog() {
        int selectedRow = infoTable.getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(this, "Please select an item to archive.", "Selection Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        try {
            // Get the ID from the selected row
            int infoId = (Integer) tableModel.getValueAt(selectedRow, 0);
            
            // Update the status in the database
            boolean success = databaseService.updatePublicInfoStatus(infoId, "Archived");
            
            if (success) {
                JOptionPane.showMessageDialog(this, "Public info archived successfully!", "Success", JOptionPane.INFORMATION_MESSAGE);
                refreshTable();
            } else {
                JOptionPane.showMessageDialog(this, "Failed to archive public info.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Error archiving public info: " + e.getMessage(), "Database Error", JOptionPane.ERROR_MESSAGE);
            e.printStackTrace();
        }
    }
}
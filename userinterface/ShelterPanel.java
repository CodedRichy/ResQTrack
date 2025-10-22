package userinterface;

import service.Shelter;
import database.DatabaseService;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

public class ShelterPanel extends JPanel implements AdminControllable { 
    
    private final DefaultTableModel tableModel;
    private final JTable shelterTable;
    private final DatabaseService databaseService;

    public ShelterPanel() { 
        
        this.setLayout(new BorderLayout());
        this.databaseService = DatabaseService.getInstance();

        String[] columnNames = {"ID", "Name", "Capacity", "Available", "Food", "Water", "Medicine"};
        tableModel = new DefaultTableModel(columnNames, 0);
        shelterTable = new JTable(tableModel);
        
        JButton addButton = new JButton("Add Shelter");
        addButton.addActionListener(e -> addShelterDialog());

        JButton updateButton = new JButton("Update Shelter");
        updateButton.addActionListener(e -> updateShelterDialog());
        
        JButton deleteButton = new JButton("Delete Shelter");
        deleteButton.addActionListener(e -> deleteShelterDialog());
        
        JPanel buttonPanel = new JPanel();
        buttonPanel.add(addButton);
        buttonPanel.add(updateButton);
        buttonPanel.add(deleteButton);

        add(new JScrollPane(shelterTable), BorderLayout.CENTER);
        add(buttonPanel, BorderLayout.SOUTH);
        
        refreshTable();
    }

    @Override
    public void setAdminMode(boolean isAdmin) {
        // Enable delete only for admins
        // Find the button panel and toggle the delete button
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

    private void refreshTable() {
        tableModel.setRowCount(0);
        try {
            List<Shelter> shelterList = databaseService.getAllShelters();
            for (Shelter s : shelterList) {
                Object[] row = {
                    s.getShelterId(), s.getName(), s.getCapacity(),
                    s.getAvailableCapacity(), s.getFood(), s.getWater(), s.getMedicine()
                };
                tableModel.addRow(row);
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Error loading shelters: " + e.getMessage(), 
                "Database Error", JOptionPane.ERROR_MESSAGE);
            e.printStackTrace();
        }
    }
    
    private void addShelterDialog() {
        String name = JOptionPane.showInputDialog(this, "Enter Shelter Name:");
        if (name != null && !name.trim().isEmpty()) {
            try {
                // Create a temporary Shelter object for insertion
                // The database will auto-generate the ID
                Shelter newShelter = new Shelter(0, name, 200, 500, 500, 500);
                boolean success = databaseService.createShelter(newShelter);
                
                if (success) {
                    JOptionPane.showMessageDialog(this, "Shelter added successfully!", "Success", JOptionPane.INFORMATION_MESSAGE);
                    refreshTable();
                } else {
                    JOptionPane.showMessageDialog(this, "Failed to add shelter.", "Error", JOptionPane.ERROR_MESSAGE);
                }
            } catch (Exception e) {
                JOptionPane.showMessageDialog(this, "Error adding shelter: " + e.getMessage(), "Database Error", JOptionPane.ERROR_MESSAGE);
                e.printStackTrace();
            }
        }
    }
    
    private void updateShelterDialog() {
        int selectedRow = shelterTable.getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(this, "Please select a shelter to update.", "Selection Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        try {
            // Get the shelter ID from the selected row
            int shelterId = (Integer) tableModel.getValueAt(selectedRow, 0);
            String shelterName = (String) tableModel.getValueAt(selectedRow, 1);
            int currentAvailable = (Integer) tableModel.getValueAt(selectedRow, 3);
            
            String newAvailableStr = JOptionPane.showInputDialog(this, "Enter new available capacity for " + shelterName + ":", currentAvailable);
            
            if (newAvailableStr == null) return;

            int newAvailableCap = Integer.parseInt(newAvailableStr);
            int currentFood = (Integer) tableModel.getValueAt(selectedRow, 4);
            int currentWater = (Integer) tableModel.getValueAt(selectedRow, 5);
            int currentMedicine = (Integer) tableModel.getValueAt(selectedRow, 6);
            
            boolean success = databaseService.updateShelterResources(shelterId, newAvailableCap, currentFood, currentWater, currentMedicine);
            if (success) {
                JOptionPane.showMessageDialog(this, "Shelter updated successfully!", "Success", JOptionPane.INFORMATION_MESSAGE);
                refreshTable();
            } else {
                JOptionPane.showMessageDialog(this, "Failed to update shelter.", "Error", JOptionPane.ERROR_MESSAGE);
            }
            
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Invalid number. Please enter a valid integer.", "Format Error", JOptionPane.ERROR_MESSAGE);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Error updating shelter: " + e.getMessage(), "Database Error", JOptionPane.ERROR_MESSAGE);
            e.printStackTrace();
        }
    }
    
    private void deleteShelterDialog() {
        int selectedRow = shelterTable.getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(this, "Please select a shelter to delete.", "Selection Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        try {
            int shelterId = (Integer) tableModel.getValueAt(selectedRow, 0);
            String shelterName = (String) tableModel.getValueAt(selectedRow, 1);
            
            int confirm = JOptionPane.showConfirmDialog(this, 
                "Are you sure you want to delete shelter '" + shelterName + "'?\nThis action cannot be undone.", 
                "Confirm Delete", JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE);
            
            if (confirm == JOptionPane.YES_OPTION) {
                boolean success = databaseService.deleteShelter(shelterId);
                if (success) {
                    JOptionPane.showMessageDialog(this, "Shelter deleted successfully!", "Success", JOptionPane.INFORMATION_MESSAGE);
                    refreshTable();
                } else {
                    JOptionPane.showMessageDialog(this, "Failed to delete shelter.", "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Error deleting shelter: " + e.getMessage(), "Database Error", JOptionPane.ERROR_MESSAGE);
            e.printStackTrace();
        }
    }
}
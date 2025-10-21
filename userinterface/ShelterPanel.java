package userinterface;

import service.Shelter;
import database.DatabaseService;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

public class ShelterPanel extends JPanel { 
    
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
        
        JPanel buttonPanel = new JPanel();
        buttonPanel.add(addButton);
        buttonPanel.add(updateButton);

        add(new JScrollPane(shelterTable), BorderLayout.CENTER);
        add(buttonPanel, BorderLayout.SOUTH);
        
        refreshTable();
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

        Shelter selectedShelter = shelterList.get(selectedRow);
        
        String newAvailableStr = JOptionPane.showInputDialog(this, "Enter new available capacity for " + selectedShelter.getName() + ":", selectedShelter.getAvailableCapacity());
        
        if (newAvailableStr == null) return;

        try {
            int newAvailableCap = Integer.parseInt(newAvailableStr);
            selectedShelter.updateShelter(newAvailableCap, selectedShelter.getFood(), selectedShelter.getWater(), selectedShelter.getMedicine());
            refreshTable();
            
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Invalid number. Please enter a valid integer.", "Format Error", JOptionPane.ERROR_MESSAGE);
        }
    }
}
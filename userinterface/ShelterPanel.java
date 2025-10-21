package userinterface;

import service.Shelter;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class ShelterPanel extends JPanel { 
    
    private final DefaultTableModel tableModel;
    private final JTable shelterTable;
    private static final List<Shelter> shelterList = new ArrayList<>();

    public ShelterPanel() { 
        
        this.setLayout(new BorderLayout());

        if (shelterList.isEmpty()) {
            shelterList.add(new Shelter(1, "Community Center", 150, 500, 300, 100));
            shelterList.add(new Shelter(2, "High School Gym", 300, 800, 500, 150));
        }

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
        for (Shelter s : shelterList) {
            Object[] row = {
                s.getShelterId(), s.getName(), s.getCapacity(),
                s.getAvailableCapacity(), s.getFood(), s.getWater(), s.getMedicine()
            };
            tableModel.addRow(row);
        }
    }
    
    private void addShelterDialog() {
        String name = JOptionPane.showInputDialog(this, "Enter Shelter Name:");
        if (name != null && !name.trim().isEmpty()) {
            int id = shelterList.isEmpty() ? 1 : shelterList.get(shelterList.size() - 1).getShelterId() + 1;
            shelterList.add(new Shelter(id, name, 200, 500, 500, 500));
            refreshTable();
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
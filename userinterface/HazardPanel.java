package userinterface;

import service.Hazard;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class HazardPanel extends JPanel {

    private final DefaultTableModel tableModel;
    private final JTable hazardTable;
    private static final List<Hazard> hazardList = new ArrayList<>();

    public HazardPanel() {
        this.setLayout(new BorderLayout());

        if (hazardList.isEmpty()) {
            hazardList.add(new Hazard(1, "Fallen power line", "Elm Street"));
            hazardList.add(new Hazard(2, "Flooding", "River Crossing"));
        }

        String[] columnNames = {"Hazard ID", "Description", "Location", "Status"};
        tableModel = new DefaultTableModel(columnNames, 0);
        hazardTable = new JTable(tableModel);

        JButton createHazardButton = new JButton("Create Hazard");
        createHazardButton.addActionListener(e -> createHazardDialog());

        JButton resolveHazardButton = new JButton("Resolve Hazard");
        resolveHazardButton.addActionListener(e -> resolveHazardDialog());

        JPanel buttonPanel = new JPanel();
        buttonPanel.add(createHazardButton);
        buttonPanel.add(resolveHazardButton);

        add(new JScrollPane(hazardTable), BorderLayout.CENTER);
        add(buttonPanel, BorderLayout.SOUTH);

        refreshTable();
    }

    private void refreshTable() {
        tableModel.setRowCount(0);
        for (Hazard hazard : hazardList) {
            Object[] row = {
                hazard.getHazardId(),
                hazard.getDescription(),
                hazard.getLocation(),
                hazard.getStatus()
            };
            tableModel.addRow(row);
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

            int hazardId = hazardList.isEmpty() ? 1 : hazardList.get(hazardList.size() - 1).getHazardId() + 1;
            Hazard newHazard = new Hazard(hazardId, description, location);
            hazardList.add(newHazard);
            refreshTable();
        }
    }

    private void resolveHazardDialog() {
        int selectedRow = hazardTable.getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(this, "Please select a hazard to resolve.", "Selection Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        Hazard selectedHazard = hazardList.get(selectedRow);
        selectedHazard.setStatus("Resolved");
        refreshTable();
    }
}
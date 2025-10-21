package userinterface;

import service.PublicInfo;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class PublicInfoPanel extends JPanel {

    private final DefaultTableModel tableModel;
    private final JTable infoTable;
    private static final List<PublicInfo> infoList = new ArrayList<>();

    public PublicInfoPanel() {
        this.setLayout(new BorderLayout());

        if (infoList.isEmpty()) {
            infoList.add(new PublicInfo(1, "Alert", "Flood warning in coastal area"));
            infoList.add(new PublicInfo(2, "Guideline", "Keep emergency kit ready."));
            infoList.add(new PublicInfo(3, "Shelter", "Govt School, Capacity: 500"));
            infoList.add(new PublicInfo(4, "Contact", "Disaster Helpline: 108"));
        }

        String[] columnNames = {"ID", "Type", "Description", "Status"};
        tableModel = new DefaultTableModel(columnNames, 0);
        infoTable = new JTable(tableModel);

        JButton createInfoButton = new JButton("Add Info");
        createInfoButton.addActionListener(e -> createInfoDialog());

        JButton archiveInfoButton = new JButton("Archive Info");
        archiveInfoButton.addActionListener(e -> archiveInfoDialog());

        JPanel buttonPanel = new JPanel();
        buttonPanel.add(createInfoButton);
        buttonPanel.add(archiveInfoButton);

        add(new JScrollPane(infoTable), BorderLayout.CENTER);
        add(buttonPanel, BorderLayout.SOUTH);

        refreshTable();
    }

    private void refreshTable() {
        tableModel.setRowCount(0);
        for (PublicInfo item : infoList) {
            Object[] row = {
                item.getInfoId(),
                item.getType(),
                item.getDescription(),
                item.getStatus()
            };
            tableModel.addRow(row);
        }
    }

    private void createInfoDialog() {
        String[] infoTypes = {"Alert", "Guideline", "Shelter", "Contact"};
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

            int infoId = infoList.isEmpty() ? 1 : infoList.get(infoList.size() - 1).getInfoId() + 1;
            PublicInfo newItem = new PublicInfo(infoId, type, description);
            infoList.add(newItem);
            refreshTable();
        }
    }

    private void archiveInfoDialog() {
        int selectedRow = infoTable.getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(this, "Please select an item to archive.", "Selection Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        PublicInfo selectedItem = infoList.get(selectedRow);
        selectedItem.setStatus("Archived");
        refreshTable();
    }
}
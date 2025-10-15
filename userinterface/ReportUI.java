package userinterface;

import service.Report;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class ReportUI extends JFrame {

    private final DefaultTableModel tableModel;
    private final JTable reportTable;
    private static final List<Report> reportList = new ArrayList<>();

    public ReportUI() {
        setTitle("Incident Reports");
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);

        if (reportList.isEmpty()) {
            reportList.add(new Report(1, 101, "Flood in city area", 5, "Houses damaged"));
            reportList.add(new Report(2, 102, "Earthquake in town", 10, "Buildings collapsed"));
        }

        String[] columnNames = {"Report ID", "User ID", "Description", "Casualties", "Damages"};
        tableModel = new DefaultTableModel(columnNames, 0);
        reportTable = new JTable(tableModel);

        JButton submitReportButton = new JButton("Submit New Report");
        submitReportButton.addActionListener(e -> submitReportDialog());

        JPanel buttonPanel = new JPanel();
        buttonPanel.add(submitReportButton);

        add(new JScrollPane(reportTable), BorderLayout.CENTER);
        add(buttonPanel, BorderLayout.SOUTH);

        refreshTable();
    }

    private void refreshTable() {
        tableModel.setRowCount(0);
        for (Report report : reportList) {
            Object[] row = {
                    report.getReportId(),
                    report.getUserId(),
                    report.getDescription(),
                    report.getCasualties(),
                    report.getDamages()
            };
            tableModel.addRow(row);
        }
    }

    private void submitReportDialog() {
        JTextField userIdField = new JTextField();
        JTextField descriptionField = new JTextField();
        JTextField casualtiesField = new JTextField();
        JTextField damagesField = new JTextField();

        Object[] form = {
                "User ID:", userIdField,
                "Description:", descriptionField,
                "Casualties:", casualtiesField,
                "Damages:", damagesField
        };

        int option = JOptionPane.showConfirmDialog(this, form, "Submit New Report", JOptionPane.OK_CANCEL_OPTION);
        if (option == JOptionPane.OK_OPTION) {
            try {
                int userId = Integer.parseInt(userIdField.getText());
                int casualties = Integer.parseInt(casualtiesField.getText());
                String description = descriptionField.getText();
                String damages = damagesField.getText();

                if (description.trim().isEmpty() || damages.trim().isEmpty()) {
                    JOptionPane.showMessageDialog(this, "Description and Damages fields cannot be empty.", "Input Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                int reportId = reportList.isEmpty() ? 1 : reportList.get(reportList.size() - 1).getReportId() + 1;
                Report newReport = new Report(reportId, userId, description, casualties, damages);
                reportList.add(newReport);
                refreshTable();

            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(this, "Invalid User ID or Casualties. Please enter numbers.", "Format Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }
}
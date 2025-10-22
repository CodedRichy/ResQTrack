package userinterface;

import service.Report;
import database.DatabaseService;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

public class ReportPanel extends JPanel implements AdminControllable {

    private final DefaultTableModel tableModel;
    private final JTable reportTable;
    private final DatabaseService databaseService;

    public ReportPanel() {
        this.setLayout(new BorderLayout());
        this.databaseService = DatabaseService.getInstance();

        String[] columnNames = {"Report ID", "User ID", "Description", "Casualties", "Damages"};
        tableModel = new DefaultTableModel(columnNames, 0);
        reportTable = new JTable(tableModel);

        JButton submitReportButton = new JButton("Submit New Report");
        submitReportButton.addActionListener(e -> submitReportDialog());

        JButton deleteReportButton = new JButton("Delete Report");
        deleteReportButton.addActionListener(e -> deleteReportDialog());

        JPanel buttonPanel = new JPanel();
        buttonPanel.add(submitReportButton);
        buttonPanel.add(deleteReportButton);

        add(new JScrollPane(reportTable), BorderLayout.CENTER);
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

    private void deleteReportDialog() {
        int selectedRow = reportTable.getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(this, "Please select a report to delete.", "Selection Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        try {
            int reportId = (Integer) tableModel.getValueAt(selectedRow, 0);
            int confirm = JOptionPane.showConfirmDialog(this,
                    "Are you sure you want to delete Report #" + reportId + "?\nThis action cannot be undone.",
                    "Confirm Delete", JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE);
            if (confirm == JOptionPane.YES_OPTION) {
                boolean success = DatabaseService.getInstance().deleteReport(reportId);
                if (success) {
                    JOptionPane.showMessageDialog(this, "Report deleted successfully!", "Success", JOptionPane.INFORMATION_MESSAGE);
                    refreshTable();
                } else {
                    JOptionPane.showMessageDialog(this, "Failed to delete report.", "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Error deleting report: " + e.getMessage(), "Database Error", JOptionPane.ERROR_MESSAGE);
            e.printStackTrace();
        }
    }

    private void refreshTable() {
        tableModel.setRowCount(0);
        try {
            List<Report> reportList = databaseService.getAllReports();
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
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Error loading reports: " + e.getMessage(), 
                "Database Error", JOptionPane.ERROR_MESSAGE);
            e.printStackTrace();
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

                // Create a temporary Report object for insertion
                // The database will auto-generate the ID
                Report newReport = new Report(0, userId, description, casualties, damages);
                boolean success = databaseService.createReport(newReport);
                
                if (success) {
                    JOptionPane.showMessageDialog(this, "Report submitted successfully!", "Success", JOptionPane.INFORMATION_MESSAGE);
                    refreshTable();
                } else {
                    JOptionPane.showMessageDialog(this, "Failed to submit report.", "Error", JOptionPane.ERROR_MESSAGE);
                }

            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(this, "Invalid User ID or Casualties. Please enter numbers.", "Format Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }
}
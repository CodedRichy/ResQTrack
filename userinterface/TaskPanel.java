package userinterface;

import service.Task;
import database.DatabaseService;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

public class TaskPanel extends JPanel implements AdminControllable {

    private final DefaultTableModel tableModel;
    private final JTable taskTable;
    private final DatabaseService databaseService;

    public TaskPanel() {
        this.setLayout(new BorderLayout());
        this.databaseService = DatabaseService.getInstance();

        String[] columnNames = {"Task ID", "Description", "Location", "Assigned User ID", "Status"};
        tableModel = new DefaultTableModel(columnNames, 0);
        taskTable = new JTable(tableModel);

        JButton createTaskButton = new JButton("Create Task");
        createTaskButton.addActionListener(e -> createTaskDialog());

        JButton updateStatusButton = new JButton("Update Status");
        updateStatusButton.addActionListener(e -> updateStatusDialog());

        JButton deleteTaskButton = new JButton("Delete Task");
        deleteTaskButton.addActionListener(e -> deleteTaskDialog());

        JPanel buttonPanel = new JPanel();
        buttonPanel.add(createTaskButton);
        buttonPanel.add(updateStatusButton);
        buttonPanel.add(deleteTaskButton);

        add(new JScrollPane(taskTable), BorderLayout.CENTER);
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

    private void deleteTaskDialog() {
        int selectedRow = taskTable.getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(this, "Please select a task to delete.", "Selection Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        try {
            int taskId = (Integer) tableModel.getValueAt(selectedRow, 0);
            int confirm = JOptionPane.showConfirmDialog(this,
                    "Are you sure you want to delete Task #" + taskId + "?\nThis action cannot be undone.",
                    "Confirm Delete", JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE);
            if (confirm == JOptionPane.YES_OPTION) {
                boolean success = databaseService.deleteTask(taskId);
                if (success) {
                    JOptionPane.showMessageDialog(this, "Task deleted successfully!", "Success", JOptionPane.INFORMATION_MESSAGE);
                    refreshTable();
                } else {
                    JOptionPane.showMessageDialog(this, "Failed to delete task.", "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Error deleting task: " + e.getMessage(), "Database Error", JOptionPane.ERROR_MESSAGE);
            e.printStackTrace();
        }
    }

    private void refreshTable() {
        tableModel.setRowCount(0);
        try {
            List<Task> taskList = databaseService.getAllTasks();
            for (Task task : taskList) {
                Object[] row = {
                        task.getTaskId(),
                        task.getDescription(),
                        task.getLocation(),
                        task.getAssignedUserId(),
                        task.getStatus()
                };
                tableModel.addRow(row);
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Error loading tasks: " + e.getMessage(), 
                "Database Error", JOptionPane.ERROR_MESSAGE);
            e.printStackTrace();
        }
    }

    private void createTaskDialog() {
        JTextField descriptionField = new JTextField();
        JTextField locationField = new JTextField();
        JTextField userIdField = new JTextField();

        Object[] message = {
                "Description:", descriptionField,
                "Location:", locationField,
                "Assign to User ID:", userIdField
        };

        int option = JOptionPane.showConfirmDialog(this, message, "Create New Task", JOptionPane.OK_CANCEL_OPTION);
        if (option == JOptionPane.OK_OPTION) {
            try {
                String description = descriptionField.getText();
                String location = locationField.getText();
                int userId = Integer.parseInt(userIdField.getText());

                if (description.trim().isEmpty() || location.trim().isEmpty()) {
                    JOptionPane.showMessageDialog(this, "Fields cannot be empty.", "Input Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                // Create a temporary Task object for insertion
                // The database will auto-generate the ID
                Task newTask = new Task(0, description, userId, location);
                boolean success = databaseService.createTask(newTask);
                
                if (success) {
                    JOptionPane.showMessageDialog(this, "Task created successfully!", "Success", JOptionPane.INFORMATION_MESSAGE);
                    refreshTable();
                } else {
                    JOptionPane.showMessageDialog(this, "Failed to create task.", "Error", JOptionPane.ERROR_MESSAGE);
                }

            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(this, "Invalid User ID. Please enter a number.", "Format Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    private void updateStatusDialog() {
        int selectedRow = taskTable.getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(this, "Please select a task to update.", "Selection Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        try {
            // Get the task ID from the selected row
            int taskId = (Integer) tableModel.getValueAt(selectedRow, 0);
            String currentStatus = (String) tableModel.getValueAt(selectedRow, 4);
            
            String[] statuses = {"Pending", "In Progress", "Completed", "Cancelled"};
            Object newStatus = JOptionPane.showInputDialog(this, "Select new status for Task " + taskId,
                    "Update Status", JOptionPane.PLAIN_MESSAGE, null, statuses, currentStatus);

            if (newStatus != null) {
                boolean success = databaseService.updateTaskStatus(taskId, (String) newStatus);
                if (success) {
                    JOptionPane.showMessageDialog(this, "Task status updated successfully!", "Success", JOptionPane.INFORMATION_MESSAGE);
                    refreshTable();
                } else {
                    JOptionPane.showMessageDialog(this, "Failed to update task status.", "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Error updating task status: " + e.getMessage(), "Database Error", JOptionPane.ERROR_MESSAGE);
            e.printStackTrace();
        }
    }
}
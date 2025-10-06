package userinterface;

import service.Task;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class TaskUI extends JFrame {

    private final DefaultTableModel tableModel;
    private final JTable taskTable;
    private static final List<Task> taskList = new ArrayList<>();

    public TaskUI() {
        setTitle("Task Management");
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);

        if (taskList.isEmpty()) {
            taskList.add(new Task(1, "Deliver supplies to Community Center", 2, "Sector A"));
            taskList.add(new Task(2, "Assess damage at High School Gym", 2, "Sector B"));
        }

        String[] columnNames = {"Task ID", "Description", "Location", "Assigned User ID", "Status"};
        tableModel = new DefaultTableModel(columnNames, 0);
        taskTable = new JTable(tableModel);

        JButton createTaskButton = new JButton("Create Task");
        createTaskButton.addActionListener(e -> createTaskDialog());

        JButton updateStatusButton = new JButton("Update Status");
        updateStatusButton.addActionListener(e -> updateStatusDialog());

        JPanel buttonPanel = new JPanel();
        buttonPanel.add(createTaskButton);
        buttonPanel.add(updateStatusButton);

        add(new JScrollPane(taskTable), BorderLayout.CENTER);
        add(buttonPanel, BorderLayout.SOUTH);

        refreshTable();
    }

    private void refreshTable() {
        tableModel.setRowCount(0);
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

                int taskId = taskList.isEmpty() ? 1 : taskList.get(taskList.size() - 1).getTaskId() + 1;
                Task newTask = new Task(taskId, description, userId, location);
                taskList.add(newTask);
                refreshTable();

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

        Task selectedTask = taskList.get(selectedRow);
        String[] statuses = {"Pending", "In Progress", "Completed"};

        Object newStatus = JOptionPane.showInputDialog(this, "Select new status for Task " + selectedTask.getTaskId(),
                "Update Status", JOptionPane.PLAIN_MESSAGE, null, statuses, selectedTask.getStatus());

        if (newStatus != null) {
            selectedTask.setStatus((String) newStatus);
            refreshTable();
        }
    }
}
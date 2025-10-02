package service;
public class Task {

    private int taskId;
    private String description;
    private int assignedUserId;
    private String status;
    private String location;

    public Task(int taskId, String description, int assignedUserId, String location) {
        this.taskId = taskId;
        this.description = description;
        this.assignedUserId = assignedUserId;
        this.location = location;
        this.status = "Pending";
    }
    public int getTaskId() {
        return taskId;
    }

    public void setTaskId(int taskId) {
        this.taskId = taskId;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getAssignedUserId() {
        return assignedUserId;
    }

    public void setAssignedUserId(int assignedUserId) {
        this.assignedUserId = assignedUserId;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public void displayTaskDetails() {
        System.out.println("--- Task Details ---");
        System.out.println("Task ID: " + taskId);
        System.out.println("Description: " + description);
        System.out.println("Location: " + location);
        System.out.println("Assigned User ID: " + assignedUserId);
        System.out.println("Status: " + status);
        System.out.println("--------------------");
    }

    public static void main(String[] args) {
        Task task1 = new Task(101, "Deliver medical supplies", 2, "Main Street Shelter");
        task1.displayTaskDetails();
        task1.setStatus("In Progress");
        System.out.println("\nTask status updated.");
        task1.displayTaskDetails();
    }
}

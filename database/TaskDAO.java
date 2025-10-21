package database;

import service.Task;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public class TaskDAO extends BaseDAO<Task> {
    
    @Override
    protected String getTableName() {
        return "tasks";
    }
    
    @Override
    protected Task mapResultSetToEntity(ResultSet rs) throws SQLException {
        Task task = new Task(
            rs.getInt("task_id"),
            rs.getString("description"),
            rs.getInt("assigned_user_id"),
            rs.getString("location")
        );
        task.setStatus(rs.getString("status"));
        return task;
    }
    
    @Override
    protected String getInsertQuery() {
        return "INSERT INTO tasks (description, assigned_user_id, location, status, priority) VALUES (?, ?, ?, ?, ?)";
    }
    
    @Override
    protected void setInsertParameters(PreparedStatement ps, Task task) throws SQLException {
        ps.setString(1, task.getDescription());
        ps.setInt(2, task.getAssignedUserId());
        ps.setString(3, task.getLocation());
        ps.setString(4, task.getStatus());
        ps.setString(5, "Medium"); // default priority
    }
    
    @Override
    protected String getUpdateQuery() {
        return "UPDATE tasks SET description = ?, assigned_user_id = ?, location = ?, status = ? WHERE task_id = ?";
    }
    
    @Override
    protected void setUpdateParameters(PreparedStatement ps, Task task) throws SQLException {
        ps.setString(1, task.getDescription());
        ps.setInt(2, task.getAssignedUserId());
        ps.setString(3, task.getLocation());
        ps.setString(4, task.getStatus());
        ps.setInt(5, task.getTaskId());
    }
    
    // Additional methods specific to Task
    public List<Task> findByAssignedUser(int userId) {
        List<Task> tasks = new java.util.ArrayList<>();
        String query = "SELECT * FROM tasks WHERE assigned_user_id = ?";
        
        try (PreparedStatement ps = connection.prepareStatement(query)) {
            ps.setInt(1, userId);
            
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    tasks.add(mapResultSetToEntity(rs));
                }
            }
        } catch (SQLException e) {
            System.err.println("Error fetching tasks by assigned user: " + e.getMessage());
            e.printStackTrace();
        }
        
        return tasks;
    }
    
    public List<Task> findByStatus(String status) {
        List<Task> tasks = new java.util.ArrayList<>();
        String query = "SELECT * FROM tasks WHERE status = ?";
        
        try (PreparedStatement ps = connection.prepareStatement(query)) {
            ps.setString(1, status);
            
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    tasks.add(mapResultSetToEntity(rs));
                }
            }
        } catch (SQLException e) {
            System.err.println("Error fetching tasks by status: " + e.getMessage());
            e.printStackTrace();
        }
        
        return tasks;
    }
    
    public List<Task> findPendingTasks() {
        return findByStatus("Pending");
    }
    
    public List<Task> findInProgressTasks() {
        return findByStatus("In Progress");
    }
    
    public List<Task> findCompletedTasks() {
        return findByStatus("Completed");
    }
    
    public boolean updateTaskStatus(int taskId, String status) {
        String query = "UPDATE tasks SET status = ? WHERE task_id = ?";
        
        try (PreparedStatement ps = connection.prepareStatement(query)) {
            ps.setString(1, status);
            ps.setInt(2, taskId);
            
            int rowsAffected = ps.executeUpdate();
            return rowsAffected > 0;
        } catch (SQLException e) {
            System.err.println("Error updating task status: " + e.getMessage());
            e.printStackTrace();
            return false;
        }
    }
    
    public List<Task> findByLocation(String location) {
        List<Task> tasks = new java.util.ArrayList<>();
        String query = "SELECT * FROM tasks WHERE location LIKE ?";
        
        try (PreparedStatement ps = connection.prepareStatement(query)) {
            ps.setString(1, "%" + location + "%");
            
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    tasks.add(mapResultSetToEntity(rs));
                }
            }
        } catch (SQLException e) {
            System.err.println("Error fetching tasks by location: " + e.getMessage());
            e.printStackTrace();
        }
        
        return tasks;
    }
}


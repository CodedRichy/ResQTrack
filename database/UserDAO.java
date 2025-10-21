package database;

import service.User;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public class UserDAO extends BaseDAO<User> {
    
    @Override
    protected String getTableName() {
        return "users";
    }
    
    @Override
    protected User mapResultSetToEntity(ResultSet rs) throws SQLException {
        return new User(
            rs.getInt("user_id"),
            rs.getString("username"),
            rs.getString("password"),
            rs.getString("role")
        );
    }
    
    @Override
    protected String getInsertQuery() {
        return "INSERT INTO users (username, password, role) VALUES (?, ?, ?)";
    }
    
    @Override
    protected void setInsertParameters(PreparedStatement ps, User user) throws SQLException {
        ps.setString(1, user.getUsername());
        ps.setString(2, user.getPassword());
        ps.setString(3, user.getRole());
    }
    
    @Override
    protected String getUpdateQuery() {
        return "UPDATE users SET username = ?, password = ?, role = ? WHERE user_id = ?";
    }
    
    @Override
    protected void setUpdateParameters(PreparedStatement ps, User user) throws SQLException {
        ps.setString(1, user.getUsername());
        ps.setString(2, user.getPassword());
        ps.setString(3, user.getRole());
        ps.setInt(4, user.getUserId());
    }
    
    // Additional methods specific to User
    public User findByUsername(String username) {
        String query = "SELECT * FROM users WHERE username = ?";
        
        try (PreparedStatement ps = connection.prepareStatement(query)) {
            ps.setString(1, username);
            
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return mapResultSetToEntity(rs);
                }
            }
        } catch (SQLException e) {
            System.err.println("Error fetching user by username: " + e.getMessage());
            e.printStackTrace();
        }
        
        return null;
    }
    
    public User authenticateUser(String username, String password) {
        String query = "SELECT * FROM users WHERE username = ? AND password = ?";
        
        try (PreparedStatement ps = connection.prepareStatement(query)) {
            ps.setString(1, username);
            ps.setString(2, password);
            
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return mapResultSetToEntity(rs);
                }
            }
        } catch (SQLException e) {
            System.err.println("Error authenticating user: " + e.getMessage());
            e.printStackTrace();
        }
        
        return null;
    }
    
    public List<User> findByRole(String role) {
        List<User> users = new java.util.ArrayList<>();
        String query = "SELECT * FROM users WHERE role = ?";
        
        try (PreparedStatement ps = connection.prepareStatement(query)) {
            ps.setString(1, role);
            
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    users.add(mapResultSetToEntity(rs));
                }
            }
        } catch (SQLException e) {
            System.err.println("Error fetching users by role: " + e.getMessage());
            e.printStackTrace();
        }
        
        return users;
    }
}


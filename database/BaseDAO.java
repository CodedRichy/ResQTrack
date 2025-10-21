package database;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public abstract class BaseDAO<T> {
    protected Connection connection;
    
    public BaseDAO() {
        this.connection = DatabaseConnector.getConnection();
    }
    
    // Abstract methods to be implemented by subclasses
    protected abstract String getTableName();
    protected abstract T mapResultSetToEntity(ResultSet rs) throws SQLException;
    protected abstract String getInsertQuery();
    protected abstract void setInsertParameters(PreparedStatement ps, T entity) throws SQLException;
    protected abstract String getUpdateQuery();
    protected abstract void setUpdateParameters(PreparedStatement ps, T entity) throws SQLException;
    
    // Common CRUD operations
    public List<T> findAll() {
        List<T> entities = new ArrayList<>();
        String query = "SELECT * FROM " + getTableName();
        
        try (PreparedStatement ps = connection.prepareStatement(query);
             ResultSet rs = ps.executeQuery()) {
            
            while (rs.next()) {
                entities.add(mapResultSetToEntity(rs));
            }
        } catch (SQLException e) {
            System.err.println("Error fetching all records from " + getTableName() + ": " + e.getMessage());
            e.printStackTrace();
        }
        
        return entities;
    }
    
    public T findById(int id) {
        String query = "SELECT * FROM " + getTableName() + " WHERE " + getTableName().substring(0, getTableName().length() - 1) + "_id = ?";
        
        try (PreparedStatement ps = connection.prepareStatement(query)) {
            ps.setInt(1, id);
            
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return mapResultSetToEntity(rs);
                }
            }
        } catch (SQLException e) {
            System.err.println("Error fetching record by ID from " + getTableName() + ": " + e.getMessage());
            e.printStackTrace();
        }
        
        return null;
    }
    
    public boolean insert(T entity) {
        String query = getInsertQuery();
        
        try (PreparedStatement ps = connection.prepareStatement(query)) {
            setInsertParameters(ps, entity);
            int rowsAffected = ps.executeUpdate();
            return rowsAffected > 0;
        } catch (SQLException e) {
            System.err.println("Error inserting record into " + getTableName() + ": " + e.getMessage());
            e.printStackTrace();
            return false;
        }
    }
    
    public boolean update(T entity) {
        String query = getUpdateQuery();
        
        try (PreparedStatement ps = connection.prepareStatement(query)) {
            setUpdateParameters(ps, entity);
            int rowsAffected = ps.executeUpdate();
            return rowsAffected > 0;
        } catch (SQLException e) {
            System.err.println("Error updating record in " + getTableName() + ": " + e.getMessage());
            e.printStackTrace();
            return false;
        }
    }
    
    public boolean delete(int id) {
        String query = "DELETE FROM " + getTableName() + " WHERE " + getTableName().substring(0, getTableName().length() - 1) + "_id = ?";
        
        try (PreparedStatement ps = connection.prepareStatement(query)) {
            ps.setInt(1, id);
            int rowsAffected = ps.executeUpdate();
            return rowsAffected > 0;
        } catch (SQLException e) {
            System.err.println("Error deleting record from " + getTableName() + ": " + e.getMessage());
            e.printStackTrace();
            return false;
        }
    }
    
    public void closeConnection() {
        try {
            if (connection != null && !connection.isClosed()) {
                connection.close();
            }
        } catch (SQLException e) {
            System.err.println("Error closing database connection: " + e.getMessage());
            e.printStackTrace();
        }
    }
}


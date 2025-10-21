package database;

import service.Hazard;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public class HazardDAO extends BaseDAO<Hazard> {
    
    @Override
    protected String getTableName() {
        return "hazards";
    }
    
    @Override
    protected Hazard mapResultSetToEntity(ResultSet rs) throws SQLException {
        return new Hazard(
            rs.getInt("hazard_id"),
            rs.getString("description"),
            rs.getString("location")
        );
    }
    
    @Override
    protected String getInsertQuery() {
        return "INSERT INTO hazards (description, location, status, severity) VALUES (?, ?, ?, ?)";
    }
    
    @Override
    protected void setInsertParameters(PreparedStatement ps, Hazard hazard) throws SQLException {
        ps.setString(1, hazard.getDescription());
        ps.setString(2, hazard.getLocation());
        ps.setString(3, hazard.getStatus());
        ps.setString(4, "Medium"); // default severity
    }
    
    @Override
    protected String getUpdateQuery() {
        return "UPDATE hazards SET description = ?, location = ?, status = ? WHERE hazard_id = ?";
    }
    
    @Override
    protected void setUpdateParameters(PreparedStatement ps, Hazard hazard) throws SQLException {
        ps.setString(1, hazard.getDescription());
        ps.setString(2, hazard.getLocation());
        ps.setString(3, hazard.getStatus());
        ps.setInt(4, hazard.getHazardId());
    }
    
    // Additional methods specific to Hazard
    public List<Hazard> findActiveHazards() {
        List<Hazard> hazards = new java.util.ArrayList<>();
        String query = "SELECT * FROM hazards WHERE status = 'Active' ORDER BY created_at DESC";
        
        try (PreparedStatement ps = connection.prepareStatement(query)) {
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    hazards.add(mapResultSetToEntity(rs));
                }
            }
        } catch (SQLException e) {
            System.err.println("Error fetching active hazards: " + e.getMessage());
            e.printStackTrace();
        }
        
        return hazards;
    }
    
    public List<Hazard> findByStatus(String status) {
        List<Hazard> hazards = new java.util.ArrayList<>();
        String query = "SELECT * FROM hazards WHERE status = ? ORDER BY created_at DESC";
        
        try (PreparedStatement ps = connection.prepareStatement(query)) {
            ps.setString(1, status);
            
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    hazards.add(mapResultSetToEntity(rs));
                }
            }
        } catch (SQLException e) {
            System.err.println("Error fetching hazards by status: " + e.getMessage());
            e.printStackTrace();
        }
        
        return hazards;
    }
    
    public List<Hazard> findByLocation(String location) {
        List<Hazard> hazards = new java.util.ArrayList<>();
        String query = "SELECT * FROM hazards WHERE location LIKE ? ORDER BY created_at DESC";
        
        try (PreparedStatement ps = connection.prepareStatement(query)) {
            ps.setString(1, "%" + location + "%");
            
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    hazards.add(mapResultSetToEntity(rs));
                }
            }
        } catch (SQLException e) {
            System.err.println("Error fetching hazards by location: " + e.getMessage());
            e.printStackTrace();
        }
        
        return hazards;
    }
    
    public List<Hazard> findBySeverity(String severity) {
        List<Hazard> hazards = new java.util.ArrayList<>();
        String query = "SELECT * FROM hazards WHERE severity = ? ORDER BY created_at DESC";
        
        try (PreparedStatement ps = connection.prepareStatement(query)) {
            ps.setString(1, severity);
            
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    hazards.add(mapResultSetToEntity(rs));
                }
            }
        } catch (SQLException e) {
            System.err.println("Error fetching hazards by severity: " + e.getMessage());
            e.printStackTrace();
        }
        
        return hazards;
    }
    
    public List<Hazard> findCriticalHazards() {
        return findBySeverity("Critical");
    }
    
    public List<Hazard> findHighSeverityHazards() {
        return findBySeverity("High");
    }
    
    public boolean updateHazardStatus(int hazardId, String status) {
        String query = "UPDATE hazards SET status = ? WHERE hazard_id = ?";
        
        try (PreparedStatement ps = connection.prepareStatement(query)) {
            ps.setString(1, status);
            ps.setInt(2, hazardId);
            
            int rowsAffected = ps.executeUpdate();
            return rowsAffected > 0;
        } catch (SQLException e) {
            System.err.println("Error updating hazard status: " + e.getMessage());
            e.printStackTrace();
            return false;
        }
    }
    
    public List<Hazard> findRecentHazards(int hours) {
        List<Hazard> hazards = new java.util.ArrayList<>();
        String query = "SELECT * FROM hazards WHERE created_at >= DATE_SUB(NOW(), INTERVAL ? HOUR) ORDER BY created_at DESC";
        
        try (PreparedStatement ps = connection.prepareStatement(query)) {
            ps.setInt(1, hours);
            
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    hazards.add(mapResultSetToEntity(rs));
                }
            }
        } catch (SQLException e) {
            System.err.println("Error fetching recent hazards: " + e.getMessage());
            e.printStackTrace();
        }
        
        return hazards;
    }
}


package database;

import service.SosAlert;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.Date;
import java.util.List;

public class SosAlertDAO extends BaseDAO<SosAlert> {
    
    @Override
    protected String getTableName() {
        return "sos_alerts";
    }
    
    @Override
    protected SosAlert mapResultSetToEntity(ResultSet rs) throws SQLException {
        SosAlert alert = new SosAlert(
            rs.getInt("alert_id"),
            rs.getInt("user_id"),
            rs.getString("location"),
            rs.getString("message")
        );
        alert.setStatus(rs.getString("status"));
        
        // Set timestamp from database
        Timestamp timestamp = rs.getTimestamp("created_at");
        if (timestamp != null) {
            alert.setTimestamp(new Date(timestamp.getTime()));
        }
        
        return alert;
    }
    
    @Override
    protected String getInsertQuery() {
        return "INSERT INTO sos_alerts (user_id, location, message, status) VALUES (?, ?, ?, ?)";
    }
    
    @Override
    protected void setInsertParameters(PreparedStatement ps, SosAlert alert) throws SQLException {
        ps.setInt(1, alert.getUserId());
        ps.setString(2, alert.getLocation());
        ps.setString(3, alert.getMessage());
        ps.setString(4, alert.getStatus());
    }
    
    @Override
    protected String getUpdateQuery() {
        return "UPDATE sos_alerts SET user_id = ?, location = ?, message = ?, status = ? WHERE alert_id = ?";
    }
    
    @Override
    protected void setUpdateParameters(PreparedStatement ps, SosAlert alert) throws SQLException {
        ps.setInt(1, alert.getUserId());
        ps.setString(2, alert.getLocation());
        ps.setString(3, alert.getMessage());
        ps.setString(4, alert.getStatus());
        ps.setInt(5, alert.getAlertId());
    }
    
    // Additional methods specific to SosAlert
    public List<SosAlert> findByUser(int userId) {
        List<SosAlert> alerts = new java.util.ArrayList<>();
        String query = "SELECT * FROM sos_alerts WHERE user_id = ?";
        
        try (PreparedStatement ps = connection.prepareStatement(query)) {
            ps.setInt(1, userId);
            
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    alerts.add(mapResultSetToEntity(rs));
                }
            }
        } catch (SQLException e) {
            System.err.println("Error fetching SOS alerts by user: " + e.getMessage());
            e.printStackTrace();
        }
        
        return alerts;
    }
    
    public List<SosAlert> findActiveAlerts() {
        List<SosAlert> alerts = new java.util.ArrayList<>();
        String query = "SELECT * FROM sos_alerts WHERE status = 'Active' ORDER BY created_at DESC";
        
        try (PreparedStatement ps = connection.prepareStatement(query)) {
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    alerts.add(mapResultSetToEntity(rs));
                }
            }
        } catch (SQLException e) {
            System.err.println("Error fetching active SOS alerts: " + e.getMessage());
            e.printStackTrace();
        }
        
        return alerts;
    }
    
    public List<SosAlert> findByStatus(String status) {
        List<SosAlert> alerts = new java.util.ArrayList<>();
        String query = "SELECT * FROM sos_alerts WHERE status = ? ORDER BY created_at DESC";
        
        try (PreparedStatement ps = connection.prepareStatement(query)) {
            ps.setString(1, status);
            
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    alerts.add(mapResultSetToEntity(rs));
                }
            }
        } catch (SQLException e) {
            System.err.println("Error fetching SOS alerts by status: " + e.getMessage());
            e.printStackTrace();
        }
        
        return alerts;
    }
    
    public List<SosAlert> findByLocation(String location) {
        List<SosAlert> alerts = new java.util.ArrayList<>();
        String query = "SELECT * FROM sos_alerts WHERE location LIKE ? ORDER BY created_at DESC";
        
        try (PreparedStatement ps = connection.prepareStatement(query)) {
            ps.setString(1, "%" + location + "%");
            
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    alerts.add(mapResultSetToEntity(rs));
                }
            }
        } catch (SQLException e) {
            System.err.println("Error fetching SOS alerts by location: " + e.getMessage());
            e.printStackTrace();
        }
        
        return alerts;
    }
    
    public boolean updateAlertStatus(int alertId, String status) {
        String query = "UPDATE sos_alerts SET status = ? WHERE alert_id = ?";
        
        try (PreparedStatement ps = connection.prepareStatement(query)) {
            ps.setString(1, status);
            ps.setInt(2, alertId);
            
            int rowsAffected = ps.executeUpdate();
            return rowsAffected > 0;
        } catch (SQLException e) {
            System.err.println("Error updating SOS alert status: " + e.getMessage());
            e.printStackTrace();
            return false;
        }
    }
    
    public List<SosAlert> findRecentAlerts(int hours) {
        List<SosAlert> alerts = new java.util.ArrayList<>();
        String query = "SELECT * FROM sos_alerts WHERE created_at >= DATE_SUB(NOW(), INTERVAL ? HOUR) ORDER BY created_at DESC";
        
        try (PreparedStatement ps = connection.prepareStatement(query)) {
            ps.setInt(1, hours);
            
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    alerts.add(mapResultSetToEntity(rs));
                }
            }
        } catch (SQLException e) {
            System.err.println("Error fetching recent SOS alerts: " + e.getMessage());
            e.printStackTrace();
        }
        
        return alerts;
    }
}


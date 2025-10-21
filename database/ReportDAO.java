package database;

import service.Report;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public class ReportDAO extends BaseDAO<Report> {
    
    @Override
    protected String getTableName() {
        return "reports";
    }
    
    @Override
    protected Report mapResultSetToEntity(ResultSet rs) throws SQLException {
        return new Report(
            rs.getInt("report_id"),
            rs.getInt("user_id"),
            rs.getString("description"),
            rs.getInt("casualties"),
            rs.getString("damages")
        );
    }
    
    @Override
    protected String getInsertQuery() {
        return "INSERT INTO reports (user_id, description, casualties, damages, location, status) VALUES (?, ?, ?, ?, ?, ?)";
    }
    
    @Override
    protected void setInsertParameters(PreparedStatement ps, Report report) throws SQLException {
        ps.setInt(1, report.getUserId());
        ps.setString(2, report.getDescription());
        ps.setInt(3, report.getCasualties());
        ps.setString(4, report.getDamages());
        ps.setString(5, ""); // location - you might want to add this to Report class
        ps.setString(6, "Pending"); // default status
    }
    
    @Override
    protected String getUpdateQuery() {
        return "UPDATE reports SET user_id = ?, description = ?, casualties = ?, damages = ? WHERE report_id = ?";
    }
    
    @Override
    protected void setUpdateParameters(PreparedStatement ps, Report report) throws SQLException {
        ps.setInt(1, report.getUserId());
        ps.setString(2, report.getDescription());
        ps.setInt(3, report.getCasualties());
        ps.setString(4, report.getDamages());
        ps.setInt(5, report.getReportId());
    }
    
    // Additional methods specific to Report
    public List<Report> findByUser(int userId) {
        List<Report> reports = new java.util.ArrayList<>();
        String query = "SELECT * FROM reports WHERE user_id = ? ORDER BY created_at DESC";
        
        try (PreparedStatement ps = connection.prepareStatement(query)) {
            ps.setInt(1, userId);
            
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    reports.add(mapResultSetToEntity(rs));
                }
            }
        } catch (SQLException e) {
            System.err.println("Error fetching reports by user: " + e.getMessage());
            e.printStackTrace();
        }
        
        return reports;
    }
    
    public List<Report> findByStatus(String status) {
        List<Report> reports = new java.util.ArrayList<>();
        String query = "SELECT * FROM reports WHERE status = ? ORDER BY created_at DESC";
        
        try (PreparedStatement ps = connection.prepareStatement(query)) {
            ps.setString(1, status);
            
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    reports.add(mapResultSetToEntity(rs));
                }
            }
        } catch (SQLException e) {
            System.err.println("Error fetching reports by status: " + e.getMessage());
            e.printStackTrace();
        }
        
        return reports;
    }
    
    public List<Report> findPendingReports() {
        return findByStatus("Pending");
    }
    
    public List<Report> findUnderReviewReports() {
        return findByStatus("Under Review");
    }
    
    public List<Report> findResolvedReports() {
        return findByStatus("Resolved");
    }
    
    public List<Report> findReportsWithCasualties() {
        List<Report> reports = new java.util.ArrayList<>();
        String query = "SELECT * FROM reports WHERE casualties > 0 ORDER BY casualties DESC, created_at DESC";
        
        try (PreparedStatement ps = connection.prepareStatement(query)) {
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    reports.add(mapResultSetToEntity(rs));
                }
            }
        } catch (SQLException e) {
            System.err.println("Error fetching reports with casualties: " + e.getMessage());
            e.printStackTrace();
        }
        
        return reports;
    }
    
    public boolean updateReportStatus(int reportId, String status) {
        String query = "UPDATE reports SET status = ? WHERE report_id = ?";
        
        try (PreparedStatement ps = connection.prepareStatement(query)) {
            ps.setString(1, status);
            ps.setInt(2, reportId);
            
            int rowsAffected = ps.executeUpdate();
            return rowsAffected > 0;
        } catch (SQLException e) {
            System.err.println("Error updating report status: " + e.getMessage());
            e.printStackTrace();
            return false;
        }
    }
    
    public List<Report> findRecentReports(int days) {
        List<Report> reports = new java.util.ArrayList<>();
        String query = "SELECT * FROM reports WHERE created_at >= DATE_SUB(NOW(), INTERVAL ? DAY) ORDER BY created_at DESC";
        
        try (PreparedStatement ps = connection.prepareStatement(query)) {
            ps.setInt(1, days);
            
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    reports.add(mapResultSetToEntity(rs));
                }
            }
        } catch (SQLException e) {
            System.err.println("Error fetching recent reports: " + e.getMessage());
            e.printStackTrace();
        }
        
        return reports;
    }
}


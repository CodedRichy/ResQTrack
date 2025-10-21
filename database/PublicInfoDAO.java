package database;

import service.PublicInfo;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public class PublicInfoDAO extends BaseDAO<PublicInfo> {
    
    @Override
    protected String getTableName() {
        return "public_info";
    }
    
    @Override
    protected PublicInfo mapResultSetToEntity(ResultSet rs) throws SQLException {
        PublicInfo info = new PublicInfo(
            rs.getInt("info_id"),
            rs.getString("type"),
            rs.getString("description")
        );
        info.setStatus(rs.getString("status"));
        return info;
    }
    
    @Override
    protected String getInsertQuery() {
        return "INSERT INTO public_info (type, description, status) VALUES (?, ?, ?)";
    }
    
    @Override
    protected void setInsertParameters(PreparedStatement ps, PublicInfo info) throws SQLException {
        ps.setString(1, info.getType());
        ps.setString(2, info.getDescription());
        ps.setString(3, info.getStatus());
    }
    
    @Override
    protected String getUpdateQuery() {
        return "UPDATE public_info SET type = ?, description = ?, status = ? WHERE info_id = ?";
    }
    
    @Override
    protected void setUpdateParameters(PreparedStatement ps, PublicInfo info) throws SQLException {
        ps.setString(1, info.getType());
        ps.setString(2, info.getDescription());
        ps.setString(3, info.getStatus());
        ps.setInt(4, info.getInfoId());
    }
    
    // Additional methods specific to PublicInfo
    public List<PublicInfo> findActiveInfo() {
        List<PublicInfo> infoList = new java.util.ArrayList<>();
        String query = "SELECT * FROM public_info WHERE status = 'Active' ORDER BY created_at DESC";
        
        try (PreparedStatement ps = connection.prepareStatement(query)) {
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    infoList.add(mapResultSetToEntity(rs));
                }
            }
        } catch (SQLException e) {
            System.err.println("Error fetching active public info: " + e.getMessage());
            e.printStackTrace();
        }
        
        return infoList;
    }
    
    public List<PublicInfo> findByType(String type) {
        List<PublicInfo> infoList = new java.util.ArrayList<>();
        String query = "SELECT * FROM public_info WHERE type = ? ORDER BY created_at DESC";
        
        try (PreparedStatement ps = connection.prepareStatement(query)) {
            ps.setString(1, type);
            
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    infoList.add(mapResultSetToEntity(rs));
                }
            }
        } catch (SQLException e) {
            System.err.println("Error fetching public info by type: " + e.getMessage());
            e.printStackTrace();
        }
        
        return infoList;
    }
    
    public List<PublicInfo> findByStatus(String status) {
        List<PublicInfo> infoList = new java.util.ArrayList<>();
        String query = "SELECT * FROM public_info WHERE status = ? ORDER BY created_at DESC";
        
        try (PreparedStatement ps = connection.prepareStatement(query)) {
            ps.setString(1, status);
            
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    infoList.add(mapResultSetToEntity(rs));
                }
            }
        } catch (SQLException e) {
            System.err.println("Error fetching public info by status: " + e.getMessage());
            e.printStackTrace();
        }
        
        return infoList;
    }
    
    public List<PublicInfo> findAlerts() {
        return findByType("Alert");
    }
    
    public List<PublicInfo> findGuidelines() {
        return findByType("Guideline");
    }
    
    public List<PublicInfo> findShelterInfo() {
        return findByType("Shelter");
    }
    
    public List<PublicInfo> findContactInfo() {
        return findByType("Contact");
    }
    
    public boolean updateInfoStatus(int infoId, String status) {
        String query = "UPDATE public_info SET status = ? WHERE info_id = ?";
        
        try (PreparedStatement ps = connection.prepareStatement(query)) {
            ps.setString(1, status);
            ps.setInt(2, infoId);
            
            int rowsAffected = ps.executeUpdate();
            return rowsAffected > 0;
        } catch (SQLException e) {
            System.err.println("Error updating public info status: " + e.getMessage());
            e.printStackTrace();
            return false;
        }
    }
    
    public List<PublicInfo> findRecentInfo(int days) {
        List<PublicInfo> infoList = new java.util.ArrayList<>();
        String query = "SELECT * FROM public_info WHERE created_at >= DATE_SUB(NOW(), INTERVAL ? DAY) ORDER BY created_at DESC";
        
        try (PreparedStatement ps = connection.prepareStatement(query)) {
            ps.setInt(1, days);
            
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    infoList.add(mapResultSetToEntity(rs));
                }
            }
        } catch (SQLException e) {
            System.err.println("Error fetching recent public info: " + e.getMessage());
            e.printStackTrace();
        }
        
        return infoList;
    }
}


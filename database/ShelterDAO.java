package database;

import service.Shelter;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public class ShelterDAO extends BaseDAO<Shelter> {
    
    @Override
    protected String getTableName() {
        return "shelters";
    }
    
    @Override
    protected Shelter mapResultSetToEntity(ResultSet rs) throws SQLException {
        return new Shelter(
            rs.getInt("shelter_id"),
            rs.getString("name"),
            rs.getInt("capacity"),
            rs.getInt("food"),
            rs.getInt("water"),
            rs.getInt("medicine")
        );
    }
    
    @Override
    protected String getInsertQuery() {
        return "INSERT INTO shelters (name, capacity, available_capacity, food, water, medicine, location) VALUES (?, ?, ?, ?, ?, ?, ?)";
    }
    
    @Override
    protected void setInsertParameters(PreparedStatement ps, Shelter shelter) throws SQLException {
        ps.setString(1, shelter.getName());
        ps.setInt(2, shelter.getCapacity());
        ps.setInt(3, shelter.getAvailableCapacity());
        ps.setInt(4, shelter.getFood());
        ps.setInt(5, shelter.getWater());
        ps.setInt(6, shelter.getMedicine());
        ps.setString(7, ""); // location - you might want to add this to Shelter class
    }
    
    @Override
    protected String getUpdateQuery() {
        return "UPDATE shelters SET name = ?, capacity = ?, available_capacity = ?, food = ?, water = ?, medicine = ? WHERE shelter_id = ?";
    }
    
    @Override
    protected void setUpdateParameters(PreparedStatement ps, Shelter shelter) throws SQLException {
        ps.setString(1, shelter.getName());
        ps.setInt(2, shelter.getCapacity());
        ps.setInt(3, shelter.getAvailableCapacity());
        ps.setInt(4, shelter.getFood());
        ps.setInt(5, shelter.getWater());
        ps.setInt(6, shelter.getMedicine());
        ps.setInt(7, shelter.getShelterId());
    }
    
    // Additional methods specific to Shelter
    public List<Shelter> findAvailableShelters() {
        List<Shelter> shelters = new java.util.ArrayList<>();
        String query = "SELECT * FROM shelters WHERE available_capacity > 0";
        
        try (PreparedStatement ps = connection.prepareStatement(query)) {
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    shelters.add(mapResultSetToEntity(rs));
                }
            }
        } catch (SQLException e) {
            System.err.println("Error fetching available shelters: " + e.getMessage());
            e.printStackTrace();
        }
        
        return shelters;
    }
    
    public boolean updateShelterResources(int shelterId, int newAvailableCapacity, int newFood, int newWater, int newMedicine) {
        String query = "UPDATE shelters SET available_capacity = ?, food = ?, water = ?, medicine = ? WHERE shelter_id = ?";
        
        try (PreparedStatement ps = connection.prepareStatement(query)) {
            ps.setInt(1, newAvailableCapacity);
            ps.setInt(2, newFood);
            ps.setInt(3, newWater);
            ps.setInt(4, newMedicine);
            ps.setInt(5, shelterId);
            
            int rowsAffected = ps.executeUpdate();
            return rowsAffected > 0;
        } catch (SQLException e) {
            System.err.println("Error updating shelter resources: " + e.getMessage());
            e.printStackTrace();
            return false;
        }
    }
    
    public List<Shelter> findSheltersByLocation(String location) {
        List<Shelter> shelters = new java.util.ArrayList<>();
        String query = "SELECT * FROM shelters WHERE location LIKE ?";
        
        try (PreparedStatement ps = connection.prepareStatement(query)) {
            ps.setString(1, "%" + location + "%");
            
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    shelters.add(mapResultSetToEntity(rs));
                }
            }
        } catch (SQLException e) {
            System.err.println("Error fetching shelters by location: " + e.getMessage());
            e.printStackTrace();
        }
        
        return shelters;
    }
}


package service;

public class Hazard {
    private int hazardId;
    private String description;
    private String location;
    private String status;

    public Hazard(int hazardId, String description, String location) {
        this.hazardId = hazardId;
        this.description = description;
        this.location = location;
        this.status = "Active"; 
    }


    public int getHazardId() { 
        return hazardId; 
    }
    
    public String getDescription() { 
        return description; 
    }
    
    public String getLocation() { 
        return location; 
    }
    
    public String getStatus() { 
        return status; 
    }

    // Setter for status
    public void setStatus(String status) {
        this.status = status;
    }
}
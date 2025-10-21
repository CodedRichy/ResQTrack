package service;

public class PublicInfo {
    private int infoId;
    private String type;
    private String description;
    private String status;

    public PublicInfo(int infoId, String type, String description) {
        this.infoId = infoId;
        this.type = type;
        this.description = description;
        this.status = "Active";
    }

    public int getInfoId() { 
        return infoId; 
    }

    public String getType() { 
        return type; 
    }

    public String getDescription() { 
        return description; 
    }

    public String getStatus() { 
        return status; 
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
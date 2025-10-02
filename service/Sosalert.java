package service;
import java.util.Date;
public class SosAlert {

    private int alertId;
    private int userId;
    private String location;
    private Date timestamp;
    private String message;
    private String status;

    public SosAlert(int alertId, int userId, String location, String message) {
        this.alertId = alertId;
        this.userId = userId;
        this.location = location;
        this.message = message;
        this.timestamp = new Date();
        this.status = "Active"; 
    }


    public int getAlertId() {
        return alertId;
    }

    public void setAlertId(int alertId) {
        this.alertId = alertId;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public Date getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Date timestamp) {
        this.timestamp = timestamp;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public void displayAlert() {
        System.out.println("--- SOS Alert ---");
        System.out.println("Alert ID: " + alertId);
        System.out.println("User ID: " + userId);
        System.out.println("Location: " + location);
        System.out.println("Timestamp: " + timestamp);
        System.out.println("Message: " + message);
        System.out.println("Status: " + status);
        System.out.println("-----------------");
    }

    public static void main(String[] args) {
        SosAlert alert1 = new SosAlert(901, 5, "123 Oak Avenue, Flood Zone B", "Trapped by floodwaters. Need immediate assistance.");
        alert1.displayAlert();
        alert1.setStatus("Responded");
        System.out.println("\nAlert status updated.");
        alert1.displayAlert();
    }
}

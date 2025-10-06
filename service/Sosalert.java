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
}
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
package service;

public class Sosalert {
    private String location;
    private String message;
    private LocalDateTime timestamp;
    private AlertType alertType;

    public enum AlertType {
        EARTHQUAKE,
        FLOOD,
        FIRE,
        TSUNAMI,
        MEDICAL_EMERGENCY,
        OTHER
    }
    public SOSAlert(String location, String message, AlertType alertType) {
        if (location == null || location.trim().isEmpty()) {
            throw new IllegalArgumentException("Location cannot be null or empty.");
        }
        this.location = location;
        this.message = (message != null) ? message : "No additional message provided.";
        this.alertType = alertType;
        this.timestamp = LocalDateTime.now(); // Set the timestamp to the current time upon creation.
    }
    public String getLocation() {
        return location;
    }
    public String getMessage() {
        return message;
    }
    public LocalDateTime getTimestamp() {
        return timestamp;
    }
    public AlertType getAlertType() {
        return alertType;
    }
    public String getFormattedAlertDetails() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        return String.format(
            "--- SOS ALERT ---\n" +
            "Type: %s\n" +
            "Location: %s\n" +
            "Time: %s\n" +
            "Message: \"%s\"\n" +
            "-----------------",
            alertType, location, timestamp.format(formatter), message
        );
    }
}

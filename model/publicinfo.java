import java.util.*;
package model;
public class publicinfo {
    private List<String> alerts;
    private List<String> safetyGuidelines;
    private List<String> shelters;
    private List<String> emergencyContacts;
    
     public PublicInfo() {
        this.alerts = new ArrayList<>();
        this.safetyGuidelines = new ArrayList<>();
        this.shelters = new ArrayList<>();
        this.emergencyContacts = new ArrayList<>();
        
         public void addAlert(String alert) {
        alerts.add(alert);
    }

    public void addSafetyGuideline(String guideline) {
        safetyGuidelines.add(guideline);
    }

    public void addShelterInfo(String shelter) {
        shelters.add(shelter);
    }

    public void addEmergencyContact(String contact) {
        emergencyContacts.add(contact);
    }

    public void addLiveUpdate(String update) {
        liveUpdates.add(update);
    }
}